(ns advent-of-code-2022.day2.core
  (:require [clojure.string :as string]
            [clojure.set :as set]))

(def letter-to-value-map {"a" 1
                          "b" 2
                          "c" 3
                          "x" 1
                          "y" 2
                          "z" 3})

(def letter-to-result-map {"x" :loss
                           "y" :draw
                           "z" :win})

(def matchups-map {1 3
                   2 1
                   3 2})

(def points-map {:win 6
                 :draw 3
                 :loss 0})

(defn parse-input [input]
  (let [raw-groups (string/split-lines input)]
    (map #(string/split % #" ") raw-groups)))

(defmulti eval-points-in-round
  (fn [phase _]
    phase))

(defmethod eval-points-in-round :part-1 [_ [l1 l2]]
  (let [l1-value (letter-to-value-map (string/lower-case l1))
        l2-value (letter-to-value-map (string/lower-case l2))
        points-from-matchup (cond (= l1-value l2-value) (points-map :draw)
                                  (= l1-value (matchups-map l2-value)) (points-map :win)
                                  :else (points-map :loss))]
    (+ l2-value points-from-matchup)))

(defmethod eval-points-in-round :part-2 [_ [l1 l2]]
  (let [l1-value (letter-to-value-map (string/lower-case l1))
        match-result (letter-to-result-map (string/lower-case l2))
        inverted-matchups-map (set/map-invert matchups-map)
        opposing-value (cond (= match-result :draw) l1-value
                             (= match-result :win) (inverted-matchups-map l1-value)
                             :else (matchups-map l1-value))
        points-from-matchup (points-map match-result)]
    (+ opposing-value points-from-matchup)))

(defn solve-part-1 [input-path]
  (let [input (slurp input-path)
        rounds (parse-input input)
        points-per-round (map #(eval-points-in-round :part-1 %) rounds)]
    (apply + points-per-round)))

(defn solve-part-2 [input-path]
  (let [input (slurp input-path)
        rounds (parse-input input)
        points-per-round (map #(eval-points-in-round :part-2 %) rounds)]
    (apply + points-per-round)))

(comment
  (solve-part-1 "src/advent_of_code_2022/day2/input.txt")
  (solve-part-2 "src/advent_of_code_2022/day2/input.txt"))