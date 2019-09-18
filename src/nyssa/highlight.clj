(ns nyssa.highlight
  (:require [clojure.java.io :as io]
            [net.cgrand.enlive-html :as enlive]
            [clygments.core :as pygments]
            [hiccup.core :refer [html]]))

(defn print-node [node]
  (println node))

(defn def-node [node]
  (def anode node)
  (println node))

(defn- extract-code
  [highlighted]
  (-> highlighted
      java.io.StringReader.
      enlive/html-resource
      (enlive/select [:pre])
      first
      :content))

(defn- highlight [node]
  (let [code (->> node :content (apply str))
        lang (->> node :attrs :class (apply str))]
    (assoc-in node [:attrs :class]
              (str "language-" lang
                   " line-numbers"))))

(defn- pyglight [node]
  (let [code (->> node :content (apply str))
        lang (->> node :attrs :class keyword)]
    (assoc node :content (-> code
                             (pygments/highlight lang :html)
                             extract-code))))

(defn highlight-code-blocks [page]
  (enlive/sniptest page
                   [:pre :code] highlight))
