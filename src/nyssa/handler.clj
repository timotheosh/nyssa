(ns nyssa.handler
  (:require [nyssa.web :as web]
            [optimus.optimizations :as optimizations]
            [optimus.prime :as optimus]
            [optimus.strategies :refer [serve-live-assets-autorefresh
                                        serve-frozen-assets]]
            [stasis.core :as stasis]))


(def app
  (optimus/wrap
   (stasis/serve-pages (web/get-pages))
   web/get-assets
   optimizations/all
   serve-frozen-assets))

(def dev-app
  (optimus/wrap
   (stasis/serve-pages (web/get-pages))
   web/get-assets
   optimizations/none
   serve-live-assets-autorefresh))
