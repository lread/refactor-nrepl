(defproject refactor-nrepl "2.5.0"
  :description "nREPL middleware to support editor-agnostic refactoring"
  :url "http://github.com/clojure-emacs/refactor-nrepl"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[nrepl "0.6.0"]
                 ^:inline-dep [http-kit "2.3.0"]
                 ^:inline-dep [cheshire "5.8.1"]
                 ^:inline-dep [org.clojure/tools.analyzer.jvm "0.7.3"]
                 ^:inline-dep [org.clojure/tools.namespace "0.3.1" :exclusions [org.clojure/tools.reader]]
                 ^:inline-dep [org.clojure/tools.reader "1.3.2"]
                 ^:inline-dep [cider/orchard "0.5.6"]
                 ^:inline-dep [cljfmt "1d8cdff6f3a6ef283cc5fc7f602cd09428641ff0"
                               ;; let's not confuse things for testing and just use rewrite-cljc from below
                               :exclusions [rewrite-cljc]]
                 ^:inline-dep [clj-commons/fs "1.5.0"]
                 ^:inline-dep [rewrite-cljc "4eeac8576341cfd0b205e21745b8ca4063b70476"
                               ;; let's exclude cljs from equation
                               :exclusions [org.clojure/clojurescript]]
                 ^:inline-dep [version-clj "0.1.2"]]
  :middleware [lein-git-down.plugin/inject-properties]
  :exclusions [org.clojure/clojure] ; see versions matrix below

  :deploy-repositories [["clojars" {:url "https://clojars.org/repo"
                                    :username :env/clojars_username
                                    :password :env/clojars_password
                                    :sign-releases false}]]
  :plugins [[thomasa/mranderson "0.5.1"]
            [reifyhealth/lein-git-down "0.3.6"]]
  :mranderson {:project-prefix  "refactor-nrepl.inlined-deps"
               :expositions     [[org.clojure/tools.analyzer.jvm org.clojure/tools.analyzer]]
               :unresolved-tree false}
  :filespecs [{:type :bytes :path "refactor-nrepl/refactor-nrepl/project.clj" :bytes ~(slurp "project.clj")}]

  :profiles {;; Clojure versions matrix
             :provided {:dependencies [[cider/cider-nrepl "0.24.0"]
                                       [org.clojure/clojure "1.8.0"]]}
             :1.8 {:dependencies [[org.clojure/clojure "1.8.0"]
                                  [org.clojure/clojurescript "1.8.51"]
                                  [javax.xml.bind/jaxb-api "2.3.1"]]}
             :1.9 {:dependencies [[org.clojure/clojure "1.9.0"]
                                  [org.clojure/clojurescript "1.9.946"]
                                  [javax.xml.bind/jaxb-api "2.3.1"]]}
             :1.10 {:dependencies [[org.clojure/clojure "1.10.1"]
                                   [org.clojure/clojurescript "1.10.520"]]}

             :test {:dependencies [[print-foo "1.0.2"]]
                    :src-paths ["test/resources"]}
             :dev {:plugins [[jonase/eastwood "0.2.0"]]
                   :global-vars {*warn-on-reflection* true}
                   :dependencies [[org.clojure/clojurescript "1.9.946"]
                                  [cider/piggieback "0.4.1"]
                                  [leiningen-core "2.9.0"]
                                  [commons-io/commons-io "2.6"]]
                   :repl-options {:nrepl-middleware [cider.piggieback/wrap-cljs-repl]}
                   :java-source-paths ["test/java"]
                   :resource-paths ["test/resources"
                                    "test/resources/testproject/src"]
                   :repositories [["snapshots" "https://oss.sonatype.org/content/repositories/snapshots"]]}
             :cljfmt [:test
                      {:plugins [[lein-cljfmt "lread-rewrite-cljc-test"
                                  :exclusions [org.clojure/clojurescript]]]
                       :cljfmt {:indents {as-> [[:inner 0]]
                                          as->* [[:inner 0]]
                                          cond-> [[:inner 0]]
                                          cond->* [[:inner 0]]
                                          with-debug-bindings [[:inner 0]]
                                          merge-meta [[:inner 0]]
                                          try-if-let [[:block 1]]}}}]}
  :jvm-opts ["-Djava.net.preferIPv4Stack=true"]
  :repositories [["public-github" {:url "git://github.com"}]]
  :git-down {rewrite-cljc {:coordinates lread/rewrite-cljc-playground}
             cljfmt {:coordinates lread/cljfmt
                     :manifest-root "cljfmt"}
             lein-cljfmt {:coordinates lread/cljfmt
                          :manifest-root "lein-cljfmt"}})
