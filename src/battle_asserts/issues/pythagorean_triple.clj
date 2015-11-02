(ns battle-asserts.issues.pythagorean-triple
  (:require [clojure.test.check.generators :as gen]
            [faker.generate :as faker]))

(def level :elementary)

(def description "Check 3 integers is a Pythagorian Triplet.
                 Pythagorian Triplet is a triplet of numbers,
                 such that x^2 + y^2 = z^2")

(defn square [i]
  (* i i))

(defn arguments-generator []
  (letfn [(triplet []
            (let [cathetus1 (rand-nth (range 1 100 2))
                  cathetus2 (quot (dec (square cathetus1)) 2)
                  hypotenuse (quot (inc (square cathetus1)) 2)]
              (shuffle [cathetus1 cathetus2 hypotenuse])))]
    (gen/tuple (gen/one-of [(gen/vector (gen/choose 1 120) 3)
                            (gen/elements (repeatedly 50 triplet))]))))

(def test-data
  [{:expected true
    :arguments [[12 5 13]]}
   {:expected true
    :arguments [[3 4 5]]}
   {:expected false
    :arguments [[8 9 7]]}
   {:expected false
    :arguments [[8 3 6]]}])

(defn solution [arr]
  (let [x (->> arr
               sort
               reverse)]
    (= (square (first x))
       (+ (square (first (rest x)))
          (square (last (rest x)))))))