(defproject loan "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [compojure "1.1.5"]
                 [ring.velocity "0.1.2"]]
  :resource-paths ["conf" "templates"]
  :plugins [[lein-ring "0.8.2"]]
  :ring {:handler house.handler/app}
  :aot :all
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.3"]]}})
