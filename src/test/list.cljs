(ns test.list
  (:require ["react-bootstrap" :as bs]
            [re-frame.core :as rf]))

(rf/reg-event-db :add-list-item
                 (fn [db [_ new-item]]
                   (update db :list-items conj new-item)))

(rf/reg-sub :list-items
            (fn [db _] (:list-items db)))

(defn page []
  [:> bs/Grid
   [:> bs/Row {:class-name "show-grid"}
    [:> bs/Col {:md 2}
     [:input {:type "button"
              :value "add item"
              :on-click #(rf/dispatch [:add-list-item (js/Date.)])}]]
    [:> bs/Col {:md 10}
     [:ul
      (for [item @(rf/subscribe [:list-items])]
        ^{:key (.getTime item)} [:li (str "Time: " item)])]]]])


