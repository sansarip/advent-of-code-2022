(ns advent-of-code-2022.day4.core
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(defn parse-input [input]
  (let [pairs (map #(str/split % #",") (str/split-lines input))]
    (map (fn ranges [[r1 r2]]
           (let [[start1 end1] (map #(Integer/parseInt %) (str/split r1 #"-"))
                 [start2 end2] (map #(Integer/parseInt %) (str/split r2 #"-"))]
             [(range start1 (inc end1)) (range start2 (inc end2))]))
         pairs)))

(defn range-covers? [[r1 r2]]
  (let [range-set1 (set r1)
        range-set2 (set r2)
        intersection (set/intersection range-set1 range-set2)]
    (or (= intersection range-set1) (= intersection range-set2))))

(defn range-overlaps? [[r1 r2]]
  (let [range-set1 (set r1)
        range-set2 (set r2)]
    (-> range-set1
        (set/intersection range-set2)
        not-empty
        boolean)))

(defn solve-part-1 [input-path]
  (let [input (slurp input-path)
        range-pairs (parse-input input)]
    (count (filter range-covers? range-pairs))))

(defn solve-part-2 [input-path]
  (let [input (slurp input-path)
        range-pairs (parse-input input)]
    (count (filter range-overlaps? range-pairs))))

(comment
  (solve-part-1 "src/advent_of_code_2022/day4/input.txt")
  (solve-part-2 "src/advent_of_code_2022/day4/input.txt"))