(ns maas.crate.minecraft
  (:require [clojure.java.io :refer [file]]
            [pallet.actions :refer [user
                                    group
                                    directory
                                    fifo
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

(def server-input (with-root "server-input"))

(defplan minecraft []
  ;; First create a minecraft user and group
  (user "minecraft"
        :action :create
        :shell :false
        :create-home false
        :system true
        :home root-folder)
  (group "minecraft"
         :system true)

  (directory root-folder
             :action :create
             :path   true
             :owner "minecraft"
             :group "minecraft")

  (remote-file server-jar
               :url server-jar-url)

  ;; Implicitly accept the EULA
  (remote-file eula-file :content "eula=true")

  ;; Setup the world folder

  ;; Create the FIFO's for communicating with the server
  (fifo server-input
        :action :create
        :owner "minecraft"
        :group "minecraft")
  )


;; java -Xmx1024M -Xms1024M -jar minecraft_server.jar nogui
