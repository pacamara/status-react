(ns ^{:doc "API to init and stop whisper messaging"}
 status-im.transport.core
  (:require
   [re-frame.core :as re-frame]
   [status-im.native-module.core :as status]
   [status-im.mailserver.core :as mailserver]
   [status-im.transport.message.core :as message]
   [status-im.transport.filters.core :as transport.filters]
   [status-im.utils.publisher :as publisher]
   [status-im.utils.fx :as fx]
   [status-im.utils.handlers :as handlers]
   [taoensso.timbre :as log]
   status-im.transport.shh
   [status-im.utils.config :as config]))

(defn set-node-info [{:keys [db]} node-info]
  {:db (assoc db :node-info node-info)})

(defn fetch-node-info []
  (let [args    {:jsonrpc "2.0"
                 :id      2
                 :method  "admin_nodeInfo"}
        payload (.stringify js/JSON (clj->js args))]
    (status/call-private-rpc payload
                             (handlers/response-handler #(re-frame/dispatch [:transport.callback/node-info-fetched %])
                                                        #(log/error "node-info: failed error" %)))))

(re-frame/reg-fx
 ::fetch-node-info
 (fn []
   (fetch-node-info)))

(fx/defn fetch-node-info-fx [cofx]
  {::fetch-node-info []})

(fx/defn init-whisper
  "Initialises whisper protocol by:
  - adding fixed shh discovery filter
  - restoring existing symetric keys along with their unique filters
  - (optionally) initializing mailserver"
  [{:keys [db web3] :as cofx}]
  (fx/merge cofx
            (fetch-node-info-fx)
            (transport.filters/load-filters)
            (publisher/start-fx)
            (mailserver/connect-to-mailserver)
            (message/resend-contact-messages [])))

(fx/defn stop-whisper
  "Stops whisper protocol by removing all existing shh filters
  It is necessary to remove the filters because status-go there isn't currently a logout feature in status-go
  to clean-up after logout. When logging out of account A and logging in account B, account B would receive
  account A messages without this."
  [{:keys [db] :as cofx} callback]
  (let [{:transport/keys [filters]} db]
    (fx/merge
     cofx
     (transport.filters/stop-filters callback)
     (publisher/stop-fx))))
