package com.bearcave.passageplanning.tasks

/**
 * Class which represents current status of updating tides tables.
 *
 * @author Michał Wąsowicz
 * @since 04.06.17
 * @version 1.0
 */
enum class TideManagerStatus {
    UNCHECKED,
    UPDATE_NECESSARY,
    IN_PROGRESS,
    UP_TO_DATE
}
