(defproject nyssa "0.1.0-SNAPSHOT"
  :description "Quick project for experimenting with stasis framework for Clojure."
  :url "https://github.com/timotheosh/nyssa"
  :license {:name "MIT License"
            :url "https://www.opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [ring/ring-jetty-adapter "1.7.1"]
                 [ring/ring-defaults "0.3.2"]
                 [hiccup "1.0.5"]
                 [markdown-clj "1.10.0"]
                 [enlive "1.1.6"]
                 [optimus "0.20.2"]
                 [stasis "2.5.0"]]
  :plugins [[lein-ring "0.12.5"]]
  :ring {:port 8080
         :handler nyssa.handler/dev-app
         :auto-reload? true}
  :main ^:skip-aot nyssa.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
