(ns test.core
  (:require [test.main :as main]
            [secretary.core :as secretary]
            [accountant.core :as accountant]
            [reagent.core :as r]))

(defn- mount-root []
  (r/render [main/page]
            (.getElementById js/document "app")))

(defn- setup-accountant []
  (accountant/configure-navigation!
   {:nav-handler
    (fn [path]
      (secretary/dispatch! path))
    :path-exists? ;; will make server request if `FALSE`, otherwise SPA will handle it
    (fn [path]
      (secretary/locate-route path))})
  (accountant/dispatch-current!))

(defn stop []
  (js/console.log "Stopping..."))

(defn start []
  (js/console.log "Starting...")
  (setup-accountant)
  (mount-root))

(defn ^:export init []
  (start))

