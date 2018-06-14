(ns test.counter
  (:require ["react-bootstrap" :as bs]
            [re-frame.core :as rf]))

(rf/reg-event-db :change-counter
                 (fn [db [_ value]]
                   (update db :counter + value)))

(rf/reg-sub :counter-value
            (fn [db _] (or (:counter db) 0)))

(rf/reg-sub :db
            (fn [db _] db))

(defn page [app-state]
  [:> bs/Grid
   [:> bs/Row {:class-name "show-grid"}
    [:> bs/Col {:md 2} [:input {:type "button"
                                :value "increase counter!"
                                :on-click #(rf/dispatch [:change-counter 1])}]]
    [:> bs/Col {:md 10} (str "Counter is " @(rf/subscribe [:counter-value]))]]])
