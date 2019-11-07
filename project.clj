(defproject feedeen-stat "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :main feedeen-stat.core
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-http "3.10.0"]
                 [buddy/buddy-sign "3.1.0"] ; https://funcool.github.io/buddy-sign/latest/
                 [clj-time "0.15.2"] ; http://clj-time.github.io/clj-time/doc/index.html
                 [org.clojure/tools.namespace "0.3.1"] ; https://github.com/clojure/tools.namespace
                 [org.clojure/data.json "0.2.6"]])


