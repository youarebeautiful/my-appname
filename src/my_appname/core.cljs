(ns my-appname.core
  (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(println "Edits to this text should show up in your developer console.")

;; define your app data so that it doesn't get over-written on reload

(def selected (atom nil))

(def grid-size [18 16])

(defn pointy-topped []
  [:svg {:style {:width "100vw" :height "100vh"}}
   [:g {:transform "scale(25)"}
    (doall
      (for [x (range 0 (first grid-size))
            y (range 0 (second grid-size))
            :let [y-offset (* y 3.5)
                  x-offset-raw (* x 4.5)
                  x-offset (if (even? y)
                             x-offset-raw
                             (+ x-offset-raw 2.25))]]
       [:g {:key [x y]
            :transform (str "translate (" x-offset "," y-offset ")")}
        [:polygon {:points "0,2 2,1 2,-1 0,-2 -2,-1, -2,1"
                   :style {:fill (if (or (= x (first @selected))
                                         (= y (second @selected)))
                                   "grey"
                                   "yellow")
                           :stroke "grey"
                           :stroke-width 0.3}
                   :on-mouse-over #(reset! selected [x y])}]]))]])

(defn flat-topped []
  "odd-q layout"
  [:svg {:style {:width "100vw" :height "100vh"}}
   [:g {:transform "translate(100, 100), scale(20)"}
    (doall
     (for [x (range 0 (first grid-size))
           y (range 0 (second grid-size))
           :let [x-offset (* x 3)
                 y-offset-raw (* y 4)
                 y-offset (if (even? x)
                              y-offset-raw
                              (+ y-offset-raw 2))]]
       [:g {:key [x y]
            :transform (str "translate (" x-offset "," y-offset ")")}
        [:polygon {:points "-1,2 1,2 2,0 1,-2 -1,-2 -2,0"
                   :style {:fill (if (or (= x (first @selected))
                                         (= y (second @selected)))
                                   "grey"
                                   "white")
                           :stroke "black"
                           :stroke-width 0.3}
                   :on-click #(reset! selected [x y])}]]))]])

(defn hello-world []
  pointy-topped)

(reagent/render-component [hello-world]
                          (. js/document (getElementById "app")))


(defn on-js-reload [])
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
