(ns test.counter
  (:require ["react-bootstrap" :as bs]))

(defn page [app-state]
  (when (not (:counter app-state))
    (swap! app-state #(merge {:counter 0} %)))
  [:> bs/Grid
   [:> bs/Row {:class-name "show-grid"}
    [:> bs/Col {:md 2} [:input {:type "button"
                                :value "increase counter!"
                                :on-click #(swap! app-state update :counter inc)}]]
    [:> bs/Col {:md 10} (str "Counter is " (:counter @app-state))]]])
