(ns loan.core
  (:import [java.lang Math]))

(defprotocol Loan
  "计算贷款"
  (get-rest [this month-idx] "计算到这个月为止还有多少贷款本金需要还")
  (get-monthly-interest [this month-idx] "计算当前这个月需要还的利息")
  (get-monthly-principal [this month-idx] "计算当前这个月需要还的贷款本金")
  (get-monthly-total [this month-idx] "计算当前这个月需要还的总金额"))

;; 等额本息贷款计算公式
;; 每月还款金额 = 贷款本金
;;             *  (月利率 * (1 + 月利率) ^ 还款月数)
;;             / ((1 + 月利率) ^ 还款月数 - 1)
(defrecord DebxLoan [total-principal monthly-rate loan-month]
  Loan
  (get-rest
    [this month-idx]
    (let [monthly-total (get-monthly-total this month-idx)
          x (Math/pow (+ 1 monthly-rate) month-idx)
          y (* total-principal x)
          z (/ (* monthly-total (- x 1)) monthly-rate)]
      (- y z)))
  (get-monthly-principal
    [this month-idx]
    (- (get-monthly-total this month-idx) (get-monthly-interest this month-idx)))
  (get-monthly-interest
    [this month-idx]
    (let [
          rest-total (get-rest this month-idx)
          monthly-interest (* rest-total monthly-rate)]
      monthly-interest))
  (get-monthly-total
    [this month-idx]
    (let [tmp (Math/pow (+ 1 monthly-rate) loan-month)
          fenzi (* monthly-rate tmp)
          fenmu (- tmp 1)]
      (/ (* total-principal fenzi) fenmu))))

;; 等额本金贷款计算公式
;; 每月还款金额 = (贷款本金 / 还款月数) + (贷款本金 - 已还本金) x 每月利率
(defrecord DebjLoan [total-principal monthly-rate loan-month]
  Loan
  (get-monthly-principal
    [this month-idx]
    (/ total-principal loan-month))  
  (get-rest
    [this month-idx]
    (let [monthly-principal (get-monthly-principal this month-idx)]
      (- total-principal (* monthly-principal month-idx))))
  (get-monthly-interest
    [this month-idx]
    (let [rest-principal (get-rest this month-idx)]
      (* rest-principal monthly-rate)))
  (get-monthly-total
    [this month-idx]
    (+ (get-monthly-interest this month-idx) (get-monthly-principal this month-idx))))


(defn mk-debx
  [total-principal & {:keys [monthly-rate loan-month]
                      :or {monthly-rate 0.003225
                           loan-month 360}}]
  (DebxLoan. total-principal monthly-rate loan-month))

(defn mk-debj
  [total-principal & {:keys [monthly-rate loan-month]
                      :or {monthly-rate 0.003225
                           loan-month 360}}]
  (DebjLoan. total-principal monthly-rate loan-month))

(defn get-pay-flow
  [loan]
  (let [pay-flow (into {} (for [month-idx (range (:loan-month loan))
                                :let [monthly-total (get-monthly-total loan month-idx)
                                      monthly-principal (get-monthly-principal loan month-idx)
                                      monthly-interest (get-monthly-interest loan month-idx)
                                      rest-principal (get-rest loan month-idx)]]
                            [month-idx {"monthly-principal" (long monthly-principal)
                                        "monthly-interest" (long monthly-interest)
                                        "monthly-total" (long monthly-total)
                                        "rest-principal" (long rest-principal)}]))]
    pay-flow))

(defn get-total
  "计算这种贷款总共需要还多少钱的贷款"
  [loan]
  (let [monthly-totals (into [] (for [month-idx (range (:loan-month loan))
                                      :let [monthly-total (get-monthly-total loan month-idx)]]
                                  monthly-total))]
    (long (reduce + monthly-totals))))

(comment
  (cal-debx-month-money 500000 0.01 360)
  (cal-debj-month-money 500000 0.01 360 3)
  (resolve-formula 1 -2 1))

