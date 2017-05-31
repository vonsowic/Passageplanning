package com.bearcave.passageplanning.tides.web;

import com.bearcave.passageplanning.tides.web.configurationitems.FileType;
import com.bearcave.passageplanning.tides.web.configurationitems.Gauge;
import com.bearcave.passageplanning.tides.web.configurationitems.MinuteStep;
import com.bearcave.passageplanning.tides.web.configurationitems.NumberOfDays;

import org.joda.time.DateTime;


public class UrlBuilder {

    private static final String URL = "http://tidepredictions.pla.co.uk/listing_page";
    private static final String separator = "/";

    private String fileType;
    private String gaugeCode;
    private DateTime time;
    private int numberOfDays;
    private int step;

    public UrlBuilder(){
        setFileType(FileType.XML);
        setNumberOfDays(NumberOfDays.ONE);
        setStep(MinuteStep.TEN);
    }

    public String build() {
        return URL
                + separator + this.gaugeCode
                + separator + this.time.getYear()
                + separator + this.time.getMonthOfYear()
                + separator + this.time.getDayOfMonth()
                + separator + this.numberOfDays
                + separator + this.step
                + separator + 0
                + separator + this.fileType
                ;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType.getTypeName();
    }

    public void setGaugeCode(Gauge gaugeCode) {
        this.gaugeCode = gaugeCode.getCode();
    }

    public void setTime(DateTime time) {
        this.time = time;
    }

    public void setNumberOfDays(NumberOfDays numberOfDays) {
        this.numberOfDays = numberOfDays.getLength();
    }

    public void setStep(MinuteStep step) {
        this.step = step.getStep();
    }
}
