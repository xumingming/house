(ns house.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.velocity.core :refer [render]]
            [clojure.string :refer [split]])
  (:require [loan.core :refer :all]))

(def buildings
  {"xixi-haixiaohai"
   {:name "西溪海小海"
    :loc "天目山西路北侧，闲富北路与上和路交叉口西南角"
    :touzishan ""
    :kaifashan ""
    :wuye "杭州金成物业公司"
    :wuyefei 1.8
    :price 9800
    :edu "小学"
    :jiaotong ""
    :kaipan-date "2013-04"
    :jiaofu-date "2014-12"
    :lvhualv 0.3395
    :rongjilv 2.41
    :gongtanmianji 10
    }
   
   "xinhu-guolin"
   {:name "新湖果岭"
    :loc ""
    :touzishan "浙江新兰得置业有限公司"
    :kaifashan ""
    :wuye "浙江新湖物业管理有限公司"}}
  )
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
       (let [building (buildings name)]
         (render "building.vm"
                 :name (:name building)
                 :loc (:loc building)
                 :touzishan (:touzishan building)
                 :kaifashan (:kaifashan building)
                 :wuye (:wuye building)
                 :wuyefei (:wuyefei building)
                 :price (:price building)
                 :edu (:edu building)
                 :jiaotong (:jiaotong building)
                 :kaipan-date (:kaipan-date building)
                 :jiaofu-date (:jiaofu-date building)
                 :lvhualv (* 100 (:lvhualv building))
                 :rongjilv (:rongjilv building)
                 :gongtanmianji (:gongtanmianji building))))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))