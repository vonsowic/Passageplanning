package com.bearcave.passageplanning.tides.database

import org.joda.time.DateTime

/**
 *
 * @author Michał Wąsowicz
 * @since 15.07.17
 * @version 1.0
 */


fun List<TideItem>.filterByStep(step: Int) = this.filter { it.id.minuteOfHour % step == 0 }

inline fun List<TideItem>.filterByDate(dateFilter: (TideItem) -> Boolean) = this.filter(dateFilter)

fun List<TideItem>.filterOnlyTides(): List<TideItem> {
    if (size == 0) return this
    val demandedIndexes = HashSet<Int>()

    val heights = this
            .map { it.predictedTideHeight }
            .withIndex()
            .toList()

    val initialPoint = heights.find { it.value != first().predictedTideHeight }
    var first = 0
    val last = initialPoint!!.index

    var isLastSmallerThanFirst = initialPoint.value < first().predictedTideHeight
    var isLastBiggerThanFirst  = initialPoint.value > first().predictedTideHeight
    var isFirstSmallerThan = isLastSmallerThanFirst
    var isFirstBiggerThan = isLastBiggerThanFirst

    for ((index, height) in heights.subList(last, size)){
        // looking for first difference
        if( !(isFirstBiggerThan || isFirstSmallerThan)) {
            isFirstSmallerThan = height > heights[first].value
            isFirstBiggerThan = height < heights[first].value

            if( isFirstBiggerThan || isFirstSmallerThan){ // first difference is found
                first = index
            }
        } else {    // looking for second difference
            isLastSmallerThanFirst = height < heights[first].value
            isLastBiggerThanFirst  = height > heights[first].value

            if( isLastBiggerThanFirst || isLastSmallerThanFirst){   // second difference is found

                if ((isFirstBiggerThan && isLastSmallerThanFirst) || (isFirstSmallerThan && isLastBiggerThanFirst)){  // the extreme point that is found
                    demandedIndexes.add((first + index) / 2)
                }

                // initialize and start searching for the next point
                first = index
                isFirstBiggerThan = isLastBiggerThanFirst
                isFirstSmallerThan= isLastSmallerThanFirst
            }
        }
    }

    return filterIndexed { index, _ -> demandedIndexes.contains(index) }
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
