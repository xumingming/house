(ns house.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.velocity.core :refer [render]]
            [clojure.string :refer [split]])
  (:require [loan.core :refer :all])
  (:require [house.buildings :refer :all])
  (:require [clojure.string :as str])
  (:require [house.util :as util]))

(defn cal-dibiao-distance [geo]
  (println "geo: " geo ", first: " (first geo) ", second: " (second geo))
  (let [ret (map (fn [loc]
                   (str "离" (:name loc)
                        (util/cal-distance (first (:geo loc))
                                           (second (:geo loc))
                                           (first geo)
                                           (second geo))
                        "公里"))
                 dibiao)]
    (str/join "," ret)))

(defn ->view [building]
  (into {} (for [[key value] building]
             (case key
               :geo ["geo" (cal-dibiao-distance (:geo building))]
               :lvhualv ["lvhualv" (str (* (:lvhualv building) 100) "%")]
               [(name key) value]))))

(defroutes app-routes
  (GET "/loan.htm" {params :params} []
       (let [total-principal (Long/parseLong (or (:tp params) "500000"))
             monthly-rate (Double/valueOf (or (:mr params) "0.003225"))
             loan-month (Long/parseLong (or (:lm params) "360"))
             ;; 等额本息
             debx (mk-debx total-principal
                           :monthly-rate monthly-rate
                           :loan-month loan-month)
             debx-pay-flow (get-pay-flow debx)
             debx-total (get-total debx)
             ;; 等额本金
             debj (mk-debj total-principal
                           :monthly-rate monthly-rate
                           :loan-month loan-month)
             debj-pay-flow (get-pay-flow debj)
             debj-total (get-total debj)]
         (render "loan.vm"
                 :total-principal total-principal
                 :monthly-rate monthly-rate
                 :loan-month loan-month
                 :debx-pay-flow debx-pay-flow
                 :debx-total debx-total
                 :debj-pay-flow debj-pay-flow
                 :debj-total debj-total
                 :monthes (range loan-month))))
  (GET "/" [] (render "index.vm"))
  (GET "/building/:building-id" [building-id]
       (let [building (buildings building-id)
             building (->view building)
             id->name (into {} (map (fn [pair] [(key pair) (:name (val pair))]) buildings))]
         (render "building.vm"
                 :id-to-name id->name
                 :building building)))
  
  (GET "/compare/:building-ids" [building-ids]
       (let [building-ids (str/split building-ids #"__")
             re-buildings (map #(buildings %) building-ids)
             re-buildings (map ->view re-buildings)
             id->name (into {} (map (fn [pair] [(key pair) (:name (val pair))]) buildings))]
         (render "compare.vm"
                 :id-to-name id->name
                 :buildings re-buildings)))
  
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))