(ns advent-of-code-2022.day3.core
  (:require [clojure.set :as set]
            [clojure.string :as str]))

(def letters [\a \b \c \d \e \f \g \h \i \j \k \l \m \n \o \p \q \r \s \t \u \v \w \x \y \z \A \B \C \D \E \F \G \H \I \J \K \L \M \N \O \P \Q \R \S \T \U \V \W \X \Y \Z])

(def priorities (zipmap letters (range 1 (inc (count letters)))))

(defn compartmentalize [rucksack]
  (let [len (count rucksack)
        mid-index (/ len 2)]
    [(subs rucksack 0 mid-index) (subs rucksack mid-index)]))

(defmulti parse-input
  (fn [phase _]
    phase))

(defmethod parse-input :part-1 [_ input]
  (let [rucksacks (str/split-lines input)]
    (map compartmentalize rucksacks)))

(defmethod parse-input :part-2 [_ input]
  (let [rucksacks (str/split-lines input)]
    (partition 3 rucksacks)))

(defn duplicate-letters [groups]
  (let [group-sets (map set groups)]
    (apply set/intersection group-sets)))

(defn groups->priority-sum [groups]
  (->> groups
       duplicate-letters
       (map priorities)
       (apply +)))

(defn solve-part-1 [input-path]
  (let [input (slurp input-path)
        compartmentalized-rucksacks (parse-input :part-1 input)
        priority-sums (map groups->priority-sum compartmentalized-rucksacks)]
    (apply + priority-sums)))

(defn solve-part-2 [input-path]
  (let [input (slurp input-path)
        groups (parse-input :part-2 input)
        priority-sums (map groups->priority-sum groups)]
    (apply + priority-sums)))

(comment
  (solve-part-1 "src/advent_of_code_2022/day3/input.txt")
  (solve-part-2 "src/advent_of_code_2022/day3/input.txt"))