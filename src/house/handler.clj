(ns house.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.velocity.core :refer [render]]
            [clojure.string :refer [split]])
  (:require [loan.core :refer :all])
  (:require [house.buildings :refer :all])
  (:require [clojure.string :as str]))

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
         (println debx-pay-flow)
         (render "loan.vm"
                 :total-principal total-principal
                 :monthly-rate monthly-rate
                 :loan-month loan-month
                 :debx-pay-flow debx-pay-flow
                 :debx-total debx-total
                 :debj-pay-flow debj-pay-flow
                 :debj-total debj-total
                 :monthes (range loan-month))))
  (GET "/" [] (str "test"))
  (GET "/building/:name" [name]
       (let [building (buildings name)
             building (into {} (map (fn [[key value]] [(clojure.core/name key) value]) building))
             id->name (into {} (map (fn [pair] [(key pair) (:name (val pair))]) buildings))]
         (println building)
         (render "building.vm"
                 :id-to-name id->name
                 :building building)))
  (GET "/compare/:building-ids" [building-ids]
       (println building-ids)
       (let [building-ids (str/split building-ids #"__")]
         (str building-ids)))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))