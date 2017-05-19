package com.bearcave.passageplanning.thames_tide_provider.web.configurationitems;


public enum FileType {
    XML("xml");

    private final String forUrl;

    FileType(String forUrl) {
        this.forUrl = forUrl;
    }

    public String getTypeName() {
        return forUrl;
    }
}
