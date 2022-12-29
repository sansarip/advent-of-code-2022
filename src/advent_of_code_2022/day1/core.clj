(ns advent-of-code-2022.day1.core
  (:require [clojure.string :as str]))

(defn strings->ints [strings]
  (map #(Integer/parseInt %) strings))

(defn parse-input [input]
  (let [raw-groups (str/split input #"\n\n")]
    (map (comp strings->ints #(str/split-lines %)) raw-groups)))

(defn solve-part-1 [input-path]
  (let [input (slurp input-path)
        groups (parse-input input)
        maximums (mapv #(apply + %) groups)]
    (apply max maximums)))

(defn solve-part-2 [input-path]
  (let [input (slurp input-path)
        groups (parse-input input)
        maximums (mapv #(apply + %) groups)
        desc-sorted-maximums (reverse (sort maximums))
        top-3 (take 3 desc-sorted-maximums)]
    (apply + top-3)))