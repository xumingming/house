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
    :zhuangxiu "毛坯"
    :hushu 900
    :cheweishu 871
    }
   
   "xinhu-guolin"
   {:name "新湖果岭"
    :loc ""
    :touzishan "浙江新兰得置业有限公司"
    :kaifashan "浙江新兰得置业有限公司"
    :wuye "浙江新湖物业管理有限公司"
    :wuyefei 2
    :price 10000
    :edu ["杭州英特外国语学校"
          "余杭镇第一中学教育集团镇中校区"
          "余杭镇第一幼儿园"
          "龙文教育"
          "余杭中学"
          "国泰·西园幼儿园"
          "杭州波达塑业有限公司研究开发中心"
          "阳光宝贝方汇幼儿园"
          "杭州绿城育华桃花源学校"
          "国泰·西园儿童之家"]
    :jiaotong "5号线工教路站"
    :kaipan-date "2011-10"
    :jiaofu-date "2014-06"
    :lvhualv 0.36
    :rongjilv 1.2
    :gongtanmianji 10
    :zhuangxiu "毛坯"
    :hushu 2330
    :cheweishu 2302
    }}
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
       (let [building (buildings name)
             id->name (into {} (map (fn [pair] [(key pair) (:name (val pair))]) buildings))]
         (println id->name)
         (render "building.vm"
                 :id name
                 :id-to-name id->name
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