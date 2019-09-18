(ns nyssa.web
  (:require [optimus.assets :as assets]
            [optimus.link :as link]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [hiccup.page :refer [html5]]
            [markdown.core :as md]
            [stasis.core :as stasis]
            [nyssa.highlight :as highlight]))

(defn get-assets []
  (assets/load-assets "public" [#".*"]))

(defn layout-page [request page]
  (html5
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:name "viewport"
            :content "width=device-width, initial-scale=1.0"}]
    [:title "Tech blog"]
    [:link {:rel "stylesheet" :href (link/file-path request "/styles/prism.css")}]]
   [:body
    [:script {:src "/js/prism.js" :language "javascript"}]
    [:script {:language "javascript"} "Prism.plugins.NormalizeWhitespace.setDefaults({
	'remove-trailing': true,
	'remove-indent': true,
	'left-trim': true,
	'right-trim': true,
	'break-lines': 100,
	/*'indent': 2,
	'remove-initial-line-feed': false,
	'tabs-to-spaces': 4,
	'spaces-to-tabs': 4*/
});"]
    [:div.logo "cjohansen.no"]
    [:div.body page]]))

(defn partial-pages [pages]
  (zipmap (keys pages)
          (map #(fn [req] (layout-page req %)) (vals pages))))

(defn markdown-pages [pages]
  (zipmap (map #(str/replace % #"\.md$" "/") (keys pages))
          (map #(fn [req] (layout-page req (md/md-to-html-string %))) (vals pages))))

(defn get-raw-pages []
  (stasis/merge-page-sources
   {:public (stasis/slurp-directory "resources/public" #".*\.(html|css|js)$")
    :partials (partial-pages (stasis/slurp-directory "resources/partials" #".*\.html$"))
    :markdown (markdown-pages (stasis/slurp-directory "resources/md" #".*\.md$"))}))

(defn prepare-page [page req]
  (-> (if (string? page) page (page req))
      highlight/highlight-code-blocks))

(defn prepare-pages [pages]
  (zipmap (keys pages)
          (map #(partial prepare-page %) (vals pages))))

(defn get-pages []
  (prepare-pages (get-raw-pages)))

(defn get-r-pages []
  (get-raw-pages))

(defn serve-pages
  []
  (stasis/serve-pages get-pages))
