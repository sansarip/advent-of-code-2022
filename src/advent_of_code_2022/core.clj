(ns advent-of-code-2022.core
  (:require [advent-of-code-2022.day1.core :as day1]
            [advent-of-code-2022.day2.core :as day2]
            [advent-of-code-2022.day3.core :as day3]
            [advent-of-code-2022.day4.core :as day4]
            [advent-of-code-2022.day5.core :as day5]))

(defn print-banner [text]
  (println (str "================================================================================\n"
                text "\n"
                "================================================================================")))

(defn print-solution [day-num & solutions]
  (print-banner (str "Day " day-num " solutions"))
  (let [count (atom 1)]
    (doseq [solution solutions]
      (println "Part" (str @count ":") solution)
      (swap! count inc)))
  (println "\n"))

(def day-1-input-path "src/advent_of_code_2022/day1/input.txt")
(def day-2-input-path "src/advent_of_code_2022/day2/input.txt")
(def day-3-input-path "src/advent_of_code_2022/day3/input.txt")
(def day-4-input-path "src/advent_of_code_2022/day4/input.txt")
(def day-5-input-path "src/advent_of_code_2022/day5/input.txt")

(print-solution 1 (day1/solve-part-1 day-1-input-path) (day1/solve-part-2 day-1-input-path))
(print-solution 2 (day2/solve-part-1 day-2-input-path) (day2/solve-part-2 day-2-input-path))
(print-solution 3 (day3/solve-part-1 day-3-input-path) (day3/solve-part-2 day-3-input-path))
(print-solution 4 (day4/solve-part-1 day-4-input-path) (day4/solve-part-2 day-4-input-path))
(print-solution 5 (day5/solve-part-1 day-5-input-path) (day5/solve-part-2 day-5-input-path))
