(ns house.util)

;; earth radius
(def ^:const EARTH-RADIUS 6378.137)

(defn rad [d]
  (/ (* d (Math/PI))
     180.0))

(defn cal-distance
  "Calculates the distance between two geo-location."
  [lat1 lng1 lat2 lng2]
  (let [rad-lat1 (rad lat1)
        rad-lat2 (rad lat2)
        a (- rad-lat1 rad-lat2)
        b (- (rad lng1) (rad lng2))
        s (* 2
             (Math/asin
              (Math/sqrt
               (+ (Math/pow (Math/sin (/ a 2)) 2)
                  (* (Math/cos rad-lat1)
                     (Math/cos rad-lat2)
                     (Math/pow (Math/sin (/ b 2)) 2))))))
        s (* s EARTH-RADIUS)
        s (/ (double (Math/round (* s 10000))) 10000)]
    s))

