(ns test.main
  (:require ["react-bootstrap" :as bs]
            [reagent.core :as r]
            [secretary.core :as secretary]
            [test.menu :as menu]
            [test.about :as about]
            [test.counter :as counter]
            [test.list :as listing]))

(def app-state (r/atom {}))

(defmulti current-page #(@app-state :link-key))

(defn- bold-if-active
  "Depending on `LINK-KEY` being marked as currently active, return `TEXT` with `BOLD` tag around"
  [link-key text]
  (assert (keyword? link-key))
  (if (= (:link-key @app-state) link-key)
    [:b text]
    text))


(secretary/defroute counter-page "/counter" []
  (swap! app-state #(merge % {:link-key :counter})))

(defmethod current-page :counter []
  [counter/page app-state])


(secretary/defroute about-page "/about" []
  (swap! app-state #(merge % {:link-key :about})))

(defmethod current-page :about []
  [about/page])

(defmethod current-page :default []
  [about/page])


(secretary/defroute list-page "/list" []
  (swap! app-state #(merge % {:link-key :list})))

(defmethod current-page :list []
  [listing/page app-state])


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

