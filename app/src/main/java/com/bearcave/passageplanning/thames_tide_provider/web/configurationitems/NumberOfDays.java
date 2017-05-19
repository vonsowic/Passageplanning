package com.bearcave.passageplanning.thames_tide_provider.web.configurationitems;



public enum NumberOfDays {
    ONE(1),
    WEEK(7),
    MONTH(30);

    private final int length;

    NumberOfDays(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }
}
