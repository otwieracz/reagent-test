(ns test.main
  (:require ["react-bootstrap" :as bs]
            [reagent.core :as r]
            [re-frame.core :as rf]
            [secretary.core :as secretary]
            [test.menu :as menu]
            [test.about :as about]
            [test.counter :as counter]
            [test.list :as listing]))

(defmulti current-page (fn [] @(rf/subscribe [:current-page])))

(rf/reg-event-db :change-page
                 (fn [db [_ new-page]]
                   (merge db {:current-page new-page})))

(rf/reg-sub :current-page
            (fn [db _] (or (:current-page db)
                           :about)))

(defn- bold-if-active
  "Depending on `LINK-KEY` being marked as currently active, return `TEXT` with `BOLD` tag around"
  [link-key text]
  (assert (keyword? link-key))
  (if (= @(rf/subscribe [:current-page]) link-key)
    [:b text]
    text))


(secretary/defroute counter-page "/counter" []
  (rf/dispatch [:change-page :counter]))

(defmethod current-page :counter []
  [counter/page])

(secretary/defroute about-page "/about" []
  (rf/dispatch [:change-page :about]))

(defmethod current-page :about []
  [about/page])

(defmethod current-page :default []
  [about/page])

(secretary/defroute list-page "/list" []
  (rf/dispatch [:change-page :list]))

(defmethod current-page :list []
  [listing/page])


(defn page []
  [:> bs/Grid
   [:> bs/Row {:class-name "show-grid"}
    [:> bs/Col {:md 12}
     [:div [:h2 "Reagent Test"]]]]
   [:> bs/Row {:class-name "show-grid"}
    [:> bs/Col {:md 12}
     [:> bs/Navbar
      [:> bs/Navbar.Header
       [:> bs/Navbar.Brand
        "reagent-test:menu"]]
      [:> bs/Nav
       [:> bs/NavItem {:event-key 1 :href "/about"} [bold-if-active :about "About"]]
       [:> bs/NavItem {:event-key 1 :href "/counter"} [bold-if-active :counter "Counter"]]
       [:> bs/NavItem {:event-key 1 :href "/list"} [bold-if-active :list "List"]]]]]]
   [:> bs/Row {:class-name "show-grid"}
    [:> bs/Col {:md 12}
     [current-page]]]])

