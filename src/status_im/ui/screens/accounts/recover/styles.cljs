(ns status-im.ui.screens.accounts.recover.styles
  (:require-macros [status-im.utils.styles :refer [defstyle]])
  (:require [status-im.ui.components.colors :as colors]
            [status-im.ui.components.react :as react]))

(def window-width (:width (react/get-dimensions "window")))
(def window-height (:height (react/get-dimensions "window")))
(defn scaled-x [n] (* (/ window-width 375) n))
(defn scaled-y [n] (* (/ window-height 812) n))

(def screen-container
  {:flex 1})

;;(defstyle inputs-container
;;  {:margin  16
;;   :desktop {:padding-top 15}})

(def password-input
  {:margin-top 10})

(def bottom-button-container
  {:flex-direction    :row
   :margin-horizontal 12
   :margin-vertical   15})

(def processing-view
  {:flex            1
   :align-items     :center
   :justify-content :center})

(def sign-you-in
  {:margin-top     16
   :font-size      13})

(def recover-release-warning
  {:margin-top     16
   :font-size      12
   :color          colors/gray})

(def recovery-phrase-input
  {:position :absolute
   :left "6.4%"
   :right "6.4%"
   :top "29.06%"
   :bottom "65.52%"
   ;; Subtitle 1 — 16px bold   
   :font-weight "700"
   :font-size 16
   :line-height 22
   :text-align :center
   :color          colors/black})

;;=============================================================
(def top-line-container
  {:margin  16
   :desktop {:padding-top 15}})

(def recovery-cancel
  {:position :absolute
   :width        "10.4%" ;; 39/375
   :height       "2.71%" ;; 22/812
   :left    "5.6%" ;; 21/375 
   :top     "7.64%" ;; 62/812
   :font-size      15
   :line-height    22
   :text-align     :center
   :color          colors/blue})

(def step-n
  {:position :absolute
   :left "8.53%"
   :right "8.53%"
   :top "7.76%"
   :bottom "89.53%"
   :font-size 15
   :line-height 22
   :text-align :center
   ;; Dark Grey
   :color "#939BA1"})

(def recovery-phrase-title
  {:position :absolute
   :left "4.27%"
   :right "4.27%"
   :top "14.29%"
   :bottom "82.27%"
   :font-size 22
   :line-height 28
   :text-align :center
   :color      colors/black})

(def recovery-phrase-explainer
  {:position :absolute
   :left "6.4%"
   :right "6.13%"
   :top "19.7%"
   :bottom "74.88%"
   :font-size 15
   :line-height 22
   :text-align :center
   :color "#939BA1"})

(def key-recovered-title
  {:position :absolute
   :left "6.93%"
   :right "6.93%"
   :top "14.29%"
   :bottom "78.82%"
   :font-size  22  ;;px;
   :line-height 28 ;;px;
   :text-align :center
   :color "#000000"})

(def key-recovered-explainer
  {:position :absolute
   :left "6.4%"
   :right "6.13%"
   :top "23.03%"
   :bottom "71.55%"
   :font-size 15 ;;px;
   :line-height 22 ;;px;
   :text-align :center
   :color "#939BA1"})

(def key-recovered-image-circle
  {:position :absolute
   :width (scaled-x 61)
   :height (scaled-x 61)
   :left (scaled-x 157)
   :top (scaled-y 347)
   :border-width 1
   :border-radius (scaled-x 30.5)
   :border-color      colors/black
   :align-items     :center
   :justify-content :center})

(def key-recovered-image
  {:align-items :center
   :color "#D88CCC"})

(def key-recovered-account-name
  {:position :absolute
   :width "47.2%" ; 177/375px
   :height "2.71%" ; 22/812px
   :left "26.4%" ; 99/375px;
   :top "51.6%" ; 419/812px;
   :font-weight "500"
   :font-size 15 ;px;
   :line-height 22 ;px;
   :flex 1
   :align-items :center
   :text-align :center
   :color "#000000"})

(def key-recovered-account-address
  {:position :absolute
   :left "11.7%" ; 44/375px;
   :right "11.7%" ; 44/375px;
   :top "54.8%" ; 445/812px;
   :bottom "42.7%" ; 347/812px;
   :font-family "Roboto Mono"
   :font-size 15 ; px;
   :line-height 22 ; px;
   :flex 1
   :align-items :center
   :text-align :center
   :color "#939BA1"})

(def key-recovered-reencrypt-your-key-button
  {:position :absolute
   :height "5.42%" ; 44/812px;
   :left "24.3%" ; 91/375px;
   :right "24.3%" ; 91/375px;
   :top "87.4%"
   :align-items :center
   :text-align :center}) ; 710/812px

(def create-pin-title
  {:position :absolute
   :left "6.4%"
   :right "6.4%"
   :top "14.29%"
   :bottom "82.27%"
   :font-size 22 ;px;
   :line-height 28 ;px;
   :flex 1
   :align-items :center
   :text-align :center
   :color "#000000"})

(def create-pin-description
  {:position :absolute
   :left "8.53%"
   :right "8.53%"
   :top "19.7%"
   :bottom "77.59%"
   :font-size 15 ;px;
   :line-height 22 ;px;
   :text-align :center
   :color "#939BA1"})

(def create-pin-digit1
  {:position :absolute
   :width (scaled-x 64)
   :height (scaled-x 64)
   :left (scaled-x 60)
   :top (scaled-y 308)
   :border-radius (scaled-x 32)
   :background-color "#ECEFFC"})

(def create-pin-description2
  {:position :absolute
   :left "8.53%"
   :right "8.53%"
   :top "82.27%"
   :bottom "12.32%"
   :font-size 15 ;px;
   :line-height 22 ;px;
   :text-align :center
   :color "#939BA1"})

; NB bounding box in Figma seems incorrectly narrow
; Using reasonable width instead
(def encrypt-with-password
  {:position :absolute
   :width "33.3%" ; 125/375px;
   :height "2.71%" ; 22/812px;
   :left "33.3%" ; (375/2 - 125/2);
   :top "90.8%" ; 737/812px;
   :font-size 15 ;px;
   :line-height 22 ;px;
   :text-align :center
   :color "#4360DF"})

(def confirm-pin-title
  {:position :absolute
   :left "6.4%"
   :right "6.4%"
   :top "14.29%"
   :bottom "82.27%"
   :font-size 22 ;px;
   :line-height 28 ;px;
   :flex 1
   :align-items :center
   :text-align :center
   :color "#000000"})
