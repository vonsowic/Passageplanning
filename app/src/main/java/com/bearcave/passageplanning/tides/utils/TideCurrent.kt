package com.bearcave.passageplanning.tides.utils

import android.util.SparseArray
import com.bearcave.passageplanning.App
import com.bearcave.passageplanning.R
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

/**
 *
 * @author Michał Wąsowicz
 * @since 22.08.17
 * @version 1.0
 */
class TideCurrent{
    companion object {

        /**
         * @return number of tides gauges
         */
        val size: Int
            get() = 8

        /**
         * @return names of tides gauges
         */
        fun names(): ArrayList<String> {
            val names = ArrayList<String>(size)

            return names
        }

        /**
         * @param type if null, then take a mean
         */
        fun getValuesOf(id: Int, type: TideType? = null): SparseArray<Float> {
            val values = SparseArray<Float>(13)

            when (type) {
                TideType.NEAP -> 0
                TideType.SPRING -> 0
                null -> 0
            }

            return values
        }

        private fun getParser(): XmlPullParser {
            val stream = App.context.resources.openRawResource(R.xml.tide_currents)
            val parser = XmlPullParserFactory.newInstance().newPullParser()
            parser.setInput(stream, null)
            return parser
        }

        private fun getTideType(highWaterHeight: Float, lowWaterHeight: Float): TideType? {
            val diff = highWaterHeight - lowWaterHeight
            if (diff < 4.0f) {
                return TideType.NEAP
            } else if (diff > 5.0f) {
                return TideType.SPRING
            } else {
                return null
            }
        }
    }
}