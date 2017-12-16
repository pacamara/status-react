(ns status-im.utils.pre-receiver
  (:require-macros [cljs.core.async.macros :as async])
  (:require [cljs.core.async :as async]))

;; See status-im.test.utils.pre-receiver for justification.

;; TODO(oskarth): Doesn't belong here, mocking real add-msg - fix signature
(defn- add-message [{:keys [id clock-value] :as msg}]
  (println "add-message:" id clock-value))

(defn- delay-message [msg out ms]
  (async/go (async/<! (async/timeout ms))
            (async/put! out msg)))

(defn- earliest-clock-value-seen? [seen id clock-value]
  (->> seen
       (filter (fn [[_ x]] (= x id)))
       sort
       ffirst
       (= clock-value)))

;; TODO: Update docstring after add-message fixed
(defn start!
  "Starts a pre-receiver that returns channel to put messages on."
  [& [{:keys [delay-ms reorder? add-fn]
       :or {delay-ms 50 reorder? true add-fn add-message}}]]
  (let [in-ch     (async/chan)
        mature-ch (async/chan)
        seen      (atom #{})]
    (async/go-loop []
      (let [{:keys [id clock-value] :as msg} (async/<! in-ch)]
        (swap! seen conj [clock-value id])
        (delay-message msg mature-ch delay-ms))
      (recur))
    (async/go-loop []
      (let [{:keys [id clock-value] :as msg} (async/<! mature-ch)]
        (if reorder?
          (if (earliest-clock-value-seen? @seen id clock-value)
            (do (swap! seen disj [clock-value id])
                (add-fn msg))
            (async/put! mature-ch msg))
          (add-fn msg))
        (recur)))
    in-ch))
