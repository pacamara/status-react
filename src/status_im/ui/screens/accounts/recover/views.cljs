(ns status-im.ui.screens.accounts.recover.views
  (:require-macros [status-im.utils.views :refer [defview letsubs]
                    :as views])
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            [status-im.ui.components.text-input.view :as text-input]
            [status-im.ui.components.react :as react]
            [status-im.ui.components.status-bar.view :as status-bar]
            [status-im.ui.components.toolbar.view :as toolbar]
            [status-im.ui.components.toolbar.actions :as toolbar.actions]
            [status-im.i18n :as i18n]
            [status-im.ui.screens.accounts.recover.styles :as styles]
            [status-im.ui.components.styles :as components.styles]
            [status-im.utils.config :as config]
            [status-im.utils.core :as utils.core]
            [status-im.react-native.js-dependencies :as js-dependencies]
            [status-im.react-native.resources :as resources]
            [status-im.ui.components.common.common :as components.common]
            [status-im.utils.security :as security]
            [status-im.utils.platform :as platform]
            [clojure.string :as string]))

(defn cancel-link []
  [react/text
   {:on-press     #(re-frame/dispatch [:navigate-to-clean :intro])
    :accessibility-label :cancel-button
    :style styles/recovery-cancel}
   (i18n/label :t/cancel)])

(defview step-i-of-n [i n]
  [react/text {:style styles/step-n}
   (i18n/label :t/step-i-of-n {:step i :number n})])

(defview recovery-phrase-title []
  [react/text {:style styles/recovery-phrase-title}
   (i18n/label :t/enter-your-recovery-phrase)])

(defview recovery-phrase-explainer []
  [react/text {:style styles/recovery-phrase-explainer}
   (i18n/label :t/enter-your-recovery-phrase-explainer)])

(defview passphrase-input [passphrase error warning]
  (letsubs [input-ref (reagent/atom nil)]
    [react/text-input
     {:style               styles/recovery-phrase-input
      ;;:height              92
      :auto-focus          true
      :ref                 (partial reset! input-ref)
      :label               (i18n/label :t/recovery-phrase)
      :accessibility-label :enter-12-words
      :placeholder         ""
      :multiline           true
      :default-value       passphrase
      :auto-correct        false
      :on-change-text      #(re-frame/dispatch [:accounts.recover.ui/passphrase-input-changed (security/mask-data %)])
      :on-blur             #(re-frame/dispatch [:accounts.recover.ui/passphrase-input-blured])
      :error               (cond error (i18n/label error)
                                 warning (i18n/label warning))}]))

(defview recoverA [phase]
  (letsubs [recovered-account [:get-recover-account]
            node-status? [:node-status]]
    (let [{:keys [passphrase password passphrase-valid? password-valid?
                  password-error passphrase-error passphrase-warning processing?]} recovered-account
          node-stopped? (or (nil? node-status?)
                            (= :stopped node-status?))
          valid-form? (and passphrase-valid?)
          disabled?   (or (not recovered-account)
                          processing?
                          (not valid-form?)
                          (not node-stopped?))
          sign-in     #(re-frame/dispatch [:accounts.recover.ui/recover-account-confirmed])]
      [react/keyboard-avoiding-view {:style styles/screen-container}
       [cancel-link]
       [step-i-of-n 1 2]
       [recovery-phrase-title]
       [recovery-phrase-explainer]
       [passphrase-input (or passphrase "") passphrase-error passphrase-warning]
       (when platform/desktop?
         [react/i18n-text {:style styles/recover-release-warning
                           :key   :recover-account-warning}])
       [react/view components.styles/flex]
       (if processing?
         [react/view styles/processing-view
          [react/activity-indicator {:animating true}]
          [react/i18n-text {:style styles/sign-you-in
                            :key   :processing}]]
         [react/view {:style styles/bottom-button-container}
          [react/view {:style components.styles/flex}]
          [components.common/bottom-button
           {:forward?  true
            :label     (i18n/label :t/next)
            :disabled? disabled?
            :on-press  sign-in}]])])))

(defview recoverB [cofx]
  [react/keyboard-avoiding-view {:style styles/screen-container}
   [cancel-link]
   [react/text {:style styles/key-recovered-title} (i18n/label :t/recovery-key-recovered-title)]
   [react/text {:style styles/key-recovered-explainer} (i18n/label :t/recovery-key-recovered-explainer)]
   [react/view styles/key-recovered-image-circle
    [react/image {:source (:account-image resources/ui) :style styles/key-recovered-image}]]
   [react/text {:style styles/key-recovered-account-name} "todo"]
   [react/text {:style styles/key-recovered-account-address} (utils.core/truncate-str (cofx :address) 13 true)]
   [react/view styles/key-recovered-reencrypt-your-key-button
    [components.common/button {:button-style {:flex-direction :row}
                               :on-press     #(re-frame/dispatch [:accounts.recover.ui/recover-account-enter-pin])
                               :label        (i18n/label :t/recovery-reencrypt-key)}]]])

(defview recoverC [phase]
  [react/keyboard-avoiding-view {:style styles/screen-container}
   [cancel-link]
   [step-i-of-n 2 2]
   [react/text {:style styles/create-pin-title} (i18n/label :t/recovery-create-pin-title)]
   [react/text {:style styles/create-pin-description} (i18n/label :t/recovery-create-pin-description)]
   [react/view styles/create-pin-digit1]
   ;;;;;;;
   [react/text {:style styles/create-pin-description2} (i18n/label :t/recovery-create-pin-description2)]
   [react/text {:style styles/encrypt-with-password} (i18n/label :t/recovery-encrypt-with-password)]])

(defview recoverD [phase]
  [react/keyboard-avoiding-view {:style styles/screen-container}
   [cancel-link]
   [step-i-of-n 2 2]
   [react/text {:style styles/confirm-pin-title} (i18n/label :t/recovery-confirm-pin-title)]
   [react/view {:style styles/create-pin-digit1}]
   ;;;;;;;
   [react/text {:style styles/create-pin-description2} (i18n/label :t/recovery-create-pin-description2)]
   [react/text {:style styles/encrypt-with-password} (i18n/label :t/recovery-encrypt-with-password)]])

(defview recover [cofx]
  (let [phase-num (cofx :phase)]
    (cond
      (= phase-num 1) [recoverA cofx]
      (= phase-num 2) [recoverB cofx]
      (= phase-num 3) [recoverC cofx]
      (= phase-num 4) [recoverD cofx])))
