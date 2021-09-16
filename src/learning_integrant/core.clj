(ns learning-integrant.core
  (:require [integrant.core :as ig]))

; 設定から始まる。ig/refで再帰的に設定する。遅延評価っぽい
(def config
  {:team/alice {:id 0 :info (ig/ref :alice/info)}
   :alice/info {:name "Alice" :age 18 :power 120}})


(defmethod ig/init-key :team/alice [_ alice]
  alice)

; 設定値をもとに広げてみる。
(defmethod ig/init-key :alice/info [_ {:keys [name age power] :as info}]
  (assoc info :intro (str "Name is " name
                          ". Age is " age
                          ". Power is " power)))

(def system (ig/init config))
;; {:alice/info {:name "Alice", :age 18, :power 120, :intro "Name is Alice. Age is 18. Power is 120"},
;;  :team/alice {:id 0, :info {:name "Alice", :age 18, :power 120, :intro "Name is Alice. Age is 18. Power is 120"}}}