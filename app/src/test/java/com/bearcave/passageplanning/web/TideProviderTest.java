package com.bearcave.passageplanning.web;

import com.bearcave.passageplanning.tides.database.TideItem;
import com.bearcave.passageplanning.tides.web.TideProvider;
import com.bearcave.passageplanning.tides.web.configurationitems.Gauge;
import com.bearcave.passageplanning.tides.web.configurationitems.MinuteStep;
import com.bearcave.passageplanning.tides.web.configurationitems.NumberOfDays;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;



/**
 * @author Michał Wąsowicz
 * @since  18.05.17.
 * @version 1.0
 */
public class TideProviderTest {

    DateTime testTime = new DateTime(
            2017,
            5,
            11,
            7,
            0
    );

    @Test
    public void load() throws Exception {
        TideProvider provider = new TideProvider();

        HashSet<TideItem> tideItems = provider.load(
                Gauge.MARGATE,
                testTime,
                NumberOfDays.ONE,
                MinuteStep.TEN
        );

        TideItem testTide = new TideItem(
                testTime,
                0.7f
        );

        Assert.assertEquals(6 * 24, tideItems.size());
        Assert.assertTrue(tideItems.contains(testTide));
    }

}