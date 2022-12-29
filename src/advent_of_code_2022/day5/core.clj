(ns advent-of-code-2022.day5.core
  (:require [clojure.string :as str]
            [clojure.core.matrix :as mtrx]))

(defn parse-int [s]
  (Integer/parseInt s))

(defn parse-instructions [instructions]
  (let [instruction-matches (re-seq #"move (\d+) from (\d+) to (\d+)" instructions)]
    (map (comp (fn [[amount from to]]
                 {:amount (parse-int amount)
                  :from (dec (parse-int from))
                  :to (dec (parse-int to))})
               rest)
         instruction-matches)))

(defn parse-crates [crates]
  (let [raw-rows (str/split-lines crates)
        partitions->letters (fn [partitions]
                              (map (fn [[_ second-item _]]
                                     (when (not= second-item \ )
                                       second-item))
                                   partitions))]
    (->> raw-rows
         (take (dec (count raw-rows)))
         (map (comp partitions->letters #(partition 4 4 [\ ] %)))
         mtrx/transpose
         (mapv (comp seq #(filter identity %))))))

(defn parse-input [input]
  (let [[raw-crates raw-instructions] (str/split input #"\n\n")]
    {:crates (parse-crates raw-crates)
     :instructions (parse-instructions raw-instructions)}))

(defn move-conj [crates {:keys [amount from to]}]
  (let [from-seq (crates from)
        to-seq (crates to)
        move-seq (take amount from-seq)]
    (-> crates
        (assoc from (drop amount from-seq))
        (assoc to (apply conj to-seq move-seq)))))

(defn move-concat [crates {:keys [amount from to]}]
  (let [from-seq (crates from)
        to-seq (crates to)
        move-seq (take amount from-seq)]
    (-> crates
        (assoc from (drop amount from-seq))
        (assoc to (concat move-seq to-seq)))))

(defn solve-part-1 [input-path]
  (let [input (slurp input-path)
        {:keys [instructions crates]} (parse-input input)
        moved-crates (reduce move-conj crates instructions)]
    (str/join (map first moved-crates))))

(defn solve-part-2 [input-path]
  (let [input (slurp input-path)
        {:keys [instructions crates]} (parse-input input)
        moved-crates (reduce move-concat crates instructions)]
    (str/join (map first moved-crates))))

(comment
  (solve-part-1 "src/advent_of_code_2022/day5/input.txt")
  (solve-part-2 "src/advent_of_code_2022/day5/input.txt"))