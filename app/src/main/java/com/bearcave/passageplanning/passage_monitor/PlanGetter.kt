package com.bearcave.passageplanning.passage_monitor

/**
 *
 * @author Michał Wąsowicz
 * @since 11.07.17
 * @version 1.0
 */
interface PlanGetter {
    fun getPlan(): PassagePlan
}