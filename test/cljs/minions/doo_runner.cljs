(ns minions.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [minions.core-test]))

(doo-tests 'minions.core-test)

