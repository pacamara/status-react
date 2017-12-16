(ns status-im.ui.screens.wallet.utils
  (:require [status-im.utils.money :as money]))

(defn format-amount [amount decimals]
  (money/to-fixed (money/token->unit (or amount (money/bignumber 0)) decimals)))