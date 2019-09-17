(ns nyssa.handler
  (:require [nyssa.web :as web]))


(def app (web/serve-pages))
