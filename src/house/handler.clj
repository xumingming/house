(ns house.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.velocity.core :refer [render]]
            [clojure.string :refer [split]])
  (:require [house.loan :refer :all])
  (:require [house.buildings :refer :all])
  (:require [clojure.string :as str])
  (:require [house.util :as util]))

(defn cal-dibiao-distance [geo]
  (println "geo: " geo ", first: " (first geo) ", second: " (second geo))
  (let [ret (map (fn [loc]
                   (str "离" (:name loc) "<b>"
                        (util/cal-distance (first (:geo loc))
                                           (second (:geo loc))
                                           (first geo)
                                           (second geo))
                        "</b>公里"))
                 dibiao)]
    (str/join "<br/>" ret)))

(defn ->view [building]
  (let [ret (into {} (for [[key value] building]
                       (case key
                         :geo ["geo" (cal-dibiao-distance (:geo building))]
                         :lvhualv ["lvhualv" (str (* (:lvhualv building) 100) "%")]
                         :defanglv ["defanglv" (* (:defanglv building) 100)]
                         [(name key) value])))
        geo (str/join "," (:geo building))
        map-url (str "http://api.map.baidu.com/staticimage?center="
                     geo
                     "&zoom=15&width=430&height=200&markers="
                     geo
                     "&markerStyles=l,A")
        ret (assoc ret "map" map-url)]
    ret))

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