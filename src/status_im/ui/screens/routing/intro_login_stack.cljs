(ns status-im.ui.screens.routing.intro-login-stack
  (:require [status-im.utils.config :as config]))

(def all-screens
  #{:login
    :progress
    :create-account
    :recover
    :accounts
    :intro
    :hardwallet-authentication-method
    :hardwallet-connect
    :enter-pin-login
    :hardwallet-setup
    :hardwallet-success
    :keycard-onboarding-intro
    :keycard-onboarding-start
    :keycard-onboarding-puk-code
    :keycard-onboarding-preparing
    :keycard-onboarding-pairing
    :keycard-onboarding-finishing
    :keycard-onboarding-connection-lost
    :keycard-onboarding-pin
    :keycard-onboarding-pair
    :keycard-onboarding-nfc-on
    :keycard-onboarding-recovery-phrase
    :keycard-onboarding-recovery-phrase-confirm-word1
    :keycard-onboarding-recovery-phrase-confirm-word2})

(defn login-stack [view-id]
  {:name    :login-stack
   :screens (cond-> [:login
                     :progress
                     :create-account
                     :recover
                     :accounts]

              config/hardwallet-enabled?
              (concat [:hardwallet-authentication-method
                       :hardwallet-connect
                       :enter-pin-login
                       :hardwallet-setup
                       :hardwallet-success]))
   :config  (if
             ;; add view-id here if you'd like that view to be
             ;; first view when app is started
             (#{:login :progress :accounts :enter-pin-login} view-id)
              {:initialRouteName view-id}
              {:initialRouteName :login})})

(defn intro-stack []
  (-> (login-stack :intro)
      (update :screens conj
              :intro
              :keycard-onboarding-intro
              :keycard-onboarding-start
              :keycard-onboarding-puk-code
              :keycard-onboarding-preparing
              :keycard-onboarding-pairing
              :keycard-onboarding-finishing
              :keycard-onboarding-connection-lost
              :keycard-onboarding-pin
              :keycard-onboarding-pair
              :keycard-onboarding-nfc-on
              :keycard-onboarding-recovery-phrase
              :keycard-onboarding-recovery-phrase-confirm-word1
              :keycard-onboarding-recovery-phrase-confirm-word2)
      (assoc :name :intro-stack)
      (assoc :config {:initialRouteName :intro})))
