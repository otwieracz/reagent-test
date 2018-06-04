(ns test.list
  (:require [reagent.core :as r]
            ["react-bootstrap" :as bs]))

(def some-list (r/atom '()))

(defn page []
  [:> bs/Grid
   [:> bs/Row {:class-name "show-grid"}
    [:> bs/Col {:md 2}
     [:input {:type "button"
              :value "add item"
              :on-click #(swap! some-list (fn [x] (cons (js/Date.) x)))}]]
    [:> bs/Col {:md 10}
     [:ul
      (for [item @some-list]
        ^{:key (.getTime item)} [:li (str "Time: " item)])]]]])


