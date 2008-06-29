package org.disco.easyb

import org.disco.easyb.listener.ExecutionListener
import org.disco.easyb.delegates.NarrativeDelegate
import org.disco.easyb.listener.BroadcastListener
import org.disco.easyb.listener.ResultsCollector

class BehaviorKeywords {
    ExecutionListener listener

    BehaviorKeywords(ExecutionListener listener) {
        this.listener = listener
    }

    def narrative(description, closure) {
        closure.delegate = new NarrativeDelegate()
    }

    def description(description) {
        listener.describeStep(description)
    }

    ResultsCollector easybResults() {
        if (listener instanceof ResultsCollector)
            return (ResultsCollector)listener

        if (listener instanceof BroadcastListener) {
            BroadcastListener broadcaster = (BroadcastListener) listener
            def result = broadcaster.listeners.find {
                if (it instanceof ResultsCollector)
                    return true
            }
            if (result != null)
                return (ResultsCollector)result
        }

        throw new IllegalStateException('No results collector available to provide easyb results')
    }
}