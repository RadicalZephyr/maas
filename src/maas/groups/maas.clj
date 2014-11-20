(ns maas.groups.maas
    "Node defintions for maas"
    (:require
     [pallet.api :refer [group-spec server-spec node-spec plan-fn]]
     [pallet.crate.automated-admin-user :refer [automated-admin-user]]))

(def default-node-spec
  (node-spec
   :image {:os-family :ubuntu}
   :hardware {:min-cores 1}))

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
                 ;; Add your crate class here
                 )}))

(def
  ^{:doc "Defines a group spec that can be passed to converge or lift."}
  maas
  (group-spec
   "maas"
   :extends [base-server maas-server]
   :node-spec default-node-spec))
