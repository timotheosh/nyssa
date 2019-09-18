(ns nyssa.handler
  (:require [nyssa.web :as web]
            [optimus.optimizations :as optimizations]
            [optimus.prime :as optimus]
            [optimus.strategies :refer [serve-live-assets
                                        serve-frozen-assets]]
            [stasis.core :as stasis]))


(def app
  (optimus/wrap
   (stasis/serve-pages web/get-pages)
   (web/get-assets)
   optimizations/all
   serve-live-assets))
