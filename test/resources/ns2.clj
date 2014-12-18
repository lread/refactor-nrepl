(ns resources.ns2
  (:require
   [clojure.pprint :refer [fresh-line]]
   [clojure.pprint :refer [get-pretty-writer]]
   [clojure.pprint :refer [cl-format]]
   [clojure.pprint :refer [get-pretty-writer fresh-line cl-format]]
   [clojure.instant :refer [read-instant-calendar]]
   [clojure.instant :refer :all])
  (:use clojure.test
        clojure.test
        [clojure.string :rename {replace foo
                                 reverse bar}]
        [clojure.edn :rename {read-string rs
                              read rd}]))
(defn use-everything []
  (get-pretty-writer)
  (fresh-line)
  (cl-format)
  (compose-fixtures)
  (escape)
  (read-instant-date)
  (rs))
