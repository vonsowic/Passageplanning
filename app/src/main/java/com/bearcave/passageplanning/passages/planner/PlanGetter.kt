package com.bearcave.passageplanning.passages

import com.bearcave.passageplanning.passages.planner.PassagePlan

/**
 *
 * @author Michał Wąsowicz
 * @since 11.07.17
 * @version 1.0
 */
interface PlanGetter {
    fun getPlan(): PassagePlan
}