(defproject maas "0.1.0-SNAPSHOT"
  :description "Minecraft as a Service"
  :url "https://github.com/RadicalZephyr/maas"
  :license {:name "Eclipse Public License v1.0"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]

                 ;; Web app dependencies
                 [compojure "1.1.8"]
                 [ring/ring-jetty-adapter "1.2.2"]
                 [environ "0.5.0"]

                 ;; Pallet dependencies
                 [com.palletops/pallet "0.8.0-RC.9"]
                 [com.palletops/pallet-vmfest "0.4.0-alpha.1"]
                 [com.palletops/pallet-jclouds "1.7.3"]

                 ;; Pallet Crate dependencies
                 [com.palletops/java-crate "0.8.0-beta.6"]

                 ;; To get started we include all jclouds compute providers.
                 ;; You may wish to replace this with the specific jclouds
                 ;; providers you use, to reduce dependency sizes.
                 ;; [org.apache.jclouds/jclouds-allcompute "1.7.2"]
                 [org.apache.jclouds.driver/jclouds-slf4j "1.7.2"
                  ;; the declared version is old and can overrule the
                  ;; resolved version
                  :exclusions [org.slf4j/slf4j-api]]
                 [org.apache.jclouds.driver/jclouds-sshj "1.7.2"]
                 [ch.qos.logback/logback-classic "1.0.9"]]

  :uberjar-name "maas.jar"
  :min-lein-version "2.0.0"
  :plugins [[environ/environ.lein "0.2.1"]]
  :hooks   [environ.leiningen.hooks]
  :profiles {:dev
             {:dependencies
              [[com.palletops/pallet "0.8.0-RC.9"
                :classifier "tests"]]
              :plugins
              [[com.palletops/pallet-lein "0.8.0-alpha.1"]]}
             :leiningen/reply
             {:dependencies [[org.slf4j/jcl-over-slf4j "1.7.2"]]
              :exclusions [commons-logging]}
             :production {:env {:production true}}}
  :local-repo-classpath true
  :repositories
  {"sonatype" "https://oss.sonatype.org/content/repositories/releases/"})
