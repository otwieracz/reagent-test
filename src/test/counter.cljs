(ns test.counter
  (:require [reagent.core :as r]
            ["react-bootstrap" :as bs]))

(def +counter+ (r/atom 0))

(defn page []
  [:> bs/Grid
   [:> bs/Row {:class-name "show-grid"}
    [:> bs/Col {:md 2} [:input {:type "button"
                                :value "increase counter!"
                                :on-click #(swap! +counter+ inc)}]]
    [:> bs/Col {:md 10} (str "Counter is " @+counter+)]]])
