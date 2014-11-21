(ns maas.crate.minecraft
  (:require [clojure.java.io :refer [file]]
            [pallet.actions :refer [directory
                                    remote-file]]
            [pallet.crate :refer [defplan]]))

(def root-folder "/var/minecraft")

(def with-root
  (comp str
        (partial file root-folder)))

(def server-jar (with-root "minecraft_server.jar"))

(def server-jar-url "https://s3.amazonaws.com/Minecraft.Download/versions/1.8/minecraft_server.1.8.jar")

(def eula-file (with-root "eula.txt"))

(def world-folder (with-root "world"))

(defplan minecraft []
  (directory root-folder
             :action :create
             :path   true)
  (remote-file server-jar
               :url server-jar-url)

  ;; Implicitly accept the EULA
  (remote-file eula-file :content "eula=true")

  ;; Setup the world folder

  )


;; java -Xmx1024M -Xms1024M -jar minecraft_server.jar nogui
