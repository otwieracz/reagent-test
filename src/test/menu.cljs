(ns test.menu
  (:require ["react-bootstrap" :as bs]))

(defn page []
  [:div
   [bs/Alert {:bs-style "warning"} [:strong "Foo!"]]])



