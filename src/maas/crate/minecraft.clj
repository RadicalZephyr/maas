(ns maas.crate.minecraft
  (:require [clojure.java.io :refer [file]]
            [pallet.actions :refer [directory
                                    remote-file]]
            [pallet.crate :refer [defplan]]))

(def root-folder "/var/minecraft")

(def server-jar (str (file root-folder "minecraft_server.jar")))

(defplan minecraft []
  (directory root-folder
             :action :create
             :path   true)
  (remote-file server-jar
               :url "https://s3.amazonaws.com/Minecraft.Download/versions/1.8/minecraft_server.1.8.jar"))
