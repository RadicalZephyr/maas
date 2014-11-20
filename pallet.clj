;;; Pallet project configuration file

(require
 '[maas.groups.maas :refer [maas]])

(defproject maas
  :provider {:jclouds
             {:node-spec
              {:image {:os-family :ubuntu :os-version-matches "12.04"
                       :os-64-bit true}}}}

  :groups [maas])
