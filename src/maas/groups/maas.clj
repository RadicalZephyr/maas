(ns maas.groups.maas
    "Node defintions for maas"
    (:require
     [environ.core :refer [env]]

     [pallet.api :refer [group-spec server-spec node-spec plan-fn]]
     [pallet.compute :refer [instantiate-provider]]
     [pallet.crate.automated-admin-user :refer [automated-admin-user]]

     [maas.crate.minecraft :as mc]
     [pallet.crate.java :as java]))

(def aws-id  (env :aws-id ""))

(def aws-key (env :aws-key ""))

(def default-node-spec
  (node-spec
   :image {:os-family :ubuntu
           :os-version-matches "14.04"
           :os-64-bit true}
   :hardware {:min-cores 1}
   :network  {:inbound-ports [22
                              25565]}))

(def
  ^{:doc "Defines the type of node maas will run on"}
  base-server
  (server-spec
   :phases
   {:bootstrap (plan-fn (automated-admin-user))}))

(def
  ^{:doc "Define a server spec for maas"}
  maas-server
  (server-spec
   :phases
   {:configure (plan-fn
                (mc/start-minecraft))
    }))

(def
  ^{:doc "Defines a group spec that can be passed to converge or lift."}
  maas
  (group-spec
   "maas"
   :extends [base-server
             maas-server
             (java/server-spec {:vendor     :openjdk
                                :components #{:jre}
                                :version    "7"})]
   :node-spec default-node-spec))

(def ^{:doc "Defines the AWS EC2 service provider using secrets from environment variables."}
  aws-ec2
  (future
    (instantiate-provider
     "aws-ec2"
     :identity aws-id
     :credential aws-key)))

(def vmfest (instantiate-provider "vmfest"))

;; (require '[vmfest.manager :as vmm])
;; (require '[vmfest.virtualbox.image :refer [setup-model]])

;; (def my-server (vmm/server))

;; (setup-model "https://vagrantcloud.com/ubuntu/boxes/trusty64/versions/1/providers/virtualbox.box"
;;              my-server
;;              :meta {:os-family :ubuntu
;;                     :os-version "14.04"
;;                     :os-64-bit true})

;; (setup-model "http://files.vagrantup.com/lucid64.box" my-server
;;              :meta {:os-family :ubuntu :os-version "10.04" :os-64-bit true})
