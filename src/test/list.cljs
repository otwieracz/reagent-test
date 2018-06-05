(ns test.list
  (:require ["react-bootstrap" :as bs]))

(defn page [app-state]
  (when (not (:counter app-state))
    (swap! app-state #(merge {:list nil} %)))
  [:> bs/Grid
   [:> bs/Row {:class-name "show-grid"}
    [:> bs/Col {:md 2}
     [:input {:type "button"
              :value "add item"
              :on-click #(swap! app-state update :list conj (js/Date.))}]]
    [:> bs/Col {:md 10}
     [:ul
      (for [item (:list @app-state)]
        ^{:key (.getTime item)} [:li (str "Time: " item)])]]]])


