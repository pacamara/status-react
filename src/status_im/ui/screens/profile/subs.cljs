(ns status-im.ui.screens.profile.subs
  (:require [re-frame.core :refer [reg-sub]]
            [clojure.string :as string]
            [status-im.utils.build :as build]))

(reg-sub
 :get-profile-unread-messages-number
 :<- [:get-current-account]
 (fn [{:keys [seed-backed-up? mnemonic]}]
   (if (or seed-backed-up? (string/blank? mnemonic)) 0 1)))

(reg-sub
 :get-app-version
 (fn [{:keys [web3-node-version]}]
   (str build/version " (" (or web3-node-version "N/A") ")")))