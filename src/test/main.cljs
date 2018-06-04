(ns test.main
  (:require ["react-bootstrap" :as bs]
            [reagent.core :as r]
            [secretary.core :as secretary]
            [test.menu :as menu]
            [test.about :as about]
            [test.counter :as counter]
            [test.list :as listing]))

(def content (r/atom ""))

(defn- bold-if-active
  "Depending on `LINK-KEY` being marked as currently active, return `TEXT` with `BOLD` tag around"
  [link-key text]
  (assert (keyword? link-key))
  (if (= (:link-key @content) link-key)
    [:b text]
    text))

(secretary/defroute counter-page "/counter" []
  (reset! content {:link-key :counter :content [counter/page]}))

(secretary/defroute about-page "/about" []
  (reset! content {:link-key :about :content [about/page]}))

(secretary/defroute list-page "/list" []
  (reset! content {:link-key :list :content [listing/page]}))

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
       [:> bs/NavItem {:event-key 1 :href "/list"} [bold-if-active :list "List"]]
       ]]]]
   [:> bs/Row {:class-name "show-grid"}
    [:> bs/Col {:md 12}
     (:content @content)]]])

