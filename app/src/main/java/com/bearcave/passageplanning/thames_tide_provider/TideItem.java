package com.bearcave.passageplanning.thames_tide_provider;

import org.joda.time.DateTime;

import java.io.Serializable;


public class TideItem implements Serializable{
    private float predictedTideHeight;
    private DateTime time;
    private int gaugeId = -1;

    public TideItem(float predictedTideHeight, DateTime time) {
        this.predictedTideHeight = predictedTideHeight;
        this.time = time;
    }

    public void setPredictedTideHeight(float predictedTideHeight) {
        this.predictedTideHeight = predictedTideHeight;
    }

    public void setTime(DateTime time) {
        this.time = time;
    }

    public void setGaugeId(int gaugeId) {
        this.gaugeId = gaugeId;
    }

    public float getPredictedTideHeight() {
        return predictedTideHeight;
    }

    public DateTime getTime() {
        return time;
    }

    public int getDayOfMonth(){
        return time.getDayOfMonth();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TideItem)) return false;

        TideItem tideItem = (TideItem) o;

        if (Float.compare(tideItem.getPredictedTideHeight(), getPredictedTideHeight()) != 0) return false;
        return getTime().equals(tideItem.getTime());

    }

    @Override
    public int hashCode() {
        int result = (getPredictedTideHeight() != +0.0f ? Float.floatToIntBits(getPredictedTideHeight()) : 0);
        result = 31 * result + getTime().hashCode();
        return result;
    }
}
