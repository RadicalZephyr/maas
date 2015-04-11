(ns maas.crate.minecraft
  (:require [clojure.java.io :refer [file]]
            [pallet.actions :refer [user
                                    group
                                    directory
                                    fifo
                                    remote-file
                                    service
                                    service-script]]
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

(def server-log   (with-root "server.log"))

(def minecraft-service
  (str "# This is a simple upstart job for a minecraft server\n"
       "\n"
       "console log\n"
       "chdir " root-folder "\n"
       "setuid minecraft\n"
       "setgid minecraft\n"
       "\n"
       "respawn\n"
       "respawn limit 20 5\n"
       "\n"
       "exec java -Xms1536M -Xmx2048M -jar " server-jar " nogui "
       "<" server-input " >" server-log "\n"
       ))

(def username "minecraft")
(def groupname "minecraft")
(def servicename "minecraft")

(defplan start-minecraft []
  ;; First create a minecraft user and group
  (user username
        :action :create
        :shell :false
        :create-home false
        :system true
        :home root-folder)
  (group groupname
         :system true)

  (directory root-folder
             :action :create
             :path   true
             :owner username
             :group groupname)

  (remote-file server-jar
               :url server-jar-url)

  ;; Implicitly accept the EULA
  (remote-file eula-file :content "eula=true")

  ;; Setup the world folder

  ;; Create the FIFO's for communicating with the server
  (fifo server-input
        :action :create
        :owner username
        :group groupname)

  ;; Install the minecraft service script
  (service-script servicename
                  :action :create
                  :service-impl :upstart
                  :content minecraft-service)
  (service servicename
           :action :start
           :service-impl :upstart))


;; java -Xmx1024M -Xms1024M -jar minecraft_server.jar nogui
