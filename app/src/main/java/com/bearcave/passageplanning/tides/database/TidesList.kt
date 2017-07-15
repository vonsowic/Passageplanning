package com.bearcave.passageplanning.tides.database

import org.joda.time.DateTime

/**
 *
 * @author Michał Wąsowicz
 * @since 15.07.17
 * @version 1.0
 */


inline fun List<TideItem>.filterByStep(step: Int) = this.filter { it.id.minuteOfHour % step == 0 }

inline fun List<TideItem>.filterByDate(dateFilter: (TideItem) -> Boolean) = this.filter(dateFilter)

inline fun List<TideItem>.filterOnlyTides(): List<TideItem> {
    val demandedIndexes = HashSet<Int>()

    val heights = this
            .map { it.predictedTideHeight }
            .withIndex()
            .toList()

    var first = 0
    var last = 0

    var isFirstSmallerThan = false
    var isFirstBiggerThan = false

    var isLastSmallerThanFirst = false
    var isLastBiggerThanFirst  = false

    for ((index, height) in heights){
        if( !(isFirstBiggerThan || isFirstSmallerThan)) {
            isFirstSmallerThan = height > heights[first].value
            isFirstBiggerThan = height < heights[first].value

            if( isFirstBiggerThan || isFirstSmallerThan){
                first = index
            }
        } else {
            isLastSmallerThanFirst = height < heights[first].value
            isLastBiggerThanFirst  = height > heights[first].value

            if( isLastBiggerThanFirst || isLastSmallerThanFirst){
                last = index

                if ((isFirstBiggerThan && isLastSmallerThanFirst) || (isFirstSmallerThan && isLastBiggerThanFirst)){  // the point that was looked for is found
                    demandedIndexes.add((first + last) / 2)
                }

                first = index
                isFirstBiggerThan = isLastBiggerThanFirst
                isFirstSmallerThan= isLastSmallerThanFirst
                isLastSmallerThanFirst = false
                isLastBiggerThanFirst  = false

            }
        }
    }

    return this
            .filterIndexed { index, _ -> demandedIndexes.contains(index) }


}

class DateFilter {

    companion object {


        fun TODAY(i: TideItem): Boolean {
            val today = DateTime.now()
            return today.dayOfMonth == i.id.dayOfMonth
                    && today.monthOfYear == i.id.monthOfYear
        }


        fun TOMORROW(i: TideItem): Boolean {
            val tomorrow = DateTime.now().plusDays(1)
            return tomorrow.dayOfMonth == i.id.dayOfMonth && tomorrow.monthOfYear == i.id.monthOfYear
        }

        fun WEEK(i: TideItem): Boolean {
            val today = DateTime.now()
            val dayNextWeek = DateTime.now().plusDays(7)
            return (i.id.dayOfMonth >= today.dayOfMonth && i.id.dayOfMonth <= dayNextWeek.dayOfMonth)
                    && (today.monthOfYear == i.id.monthOfYear || dayNextWeek.monthOfYear == i.id.monthOfYear)
        }

        fun ALL(i: TideItem): Boolean = true
    }
}
