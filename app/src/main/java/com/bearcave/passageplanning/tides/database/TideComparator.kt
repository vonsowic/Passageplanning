package com.bearcave.passageplanning.tides.database

/**
 *
 * @author Michał Wąsowicz
 * @since 13.07.17
 * @version 1.0
 */
class TideComparator: Comparator<TideItem> {
    override fun compare(o1: TideItem, o2: TideItem): Int = o1.id.compareTo(o2.id)
}