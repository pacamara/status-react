(ns status-im.data-store.realm.schemas.account.v19.user-status)

(def schema {:name       :user-status
             :primaryKey :message-id
             :properties {:message-id       :string
                          :chat-id          :string
                          :whisper-identity :string
                          :status           :string}})
