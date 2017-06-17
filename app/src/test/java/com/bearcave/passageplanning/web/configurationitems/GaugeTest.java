package com.bearcave.passageplanning.web.configurationitems;

import com.bearcave.passageplanning.tides.web.configurationitems.Gauge;
import com.bearcave.passageplanning.tides.web.configurationitems.exceptions.GaugeNotFoundException;

import org.junit.Assert;
import org.junit.Test;



public class GaugeTest {
    @Test
    public void getById() throws Exception {
        Assert.assertEquals(Gauge.CHELSEA_BRIDGE, Gauge.Companion.getById(7));
    }

    @Test(expected = GaugeNotFoundException.class)
    public void getByIdWhenDoesNotExist() throws Exception {
        Gauge.Companion.getById(243);
    }

    @Test
    public void getByCode() throws Exception {
        Assert.assertEquals(Gauge.RICHMOND, Gauge.Companion.getByCode("0116"));
    }

    @Test(expected = GaugeNotFoundException.class)
    public void getByCodeWhenDoesNotExist() throws Exception {
        Gauge.Companion.getByCode("trelemorele gdzie moj wyjatek??");
    }

}