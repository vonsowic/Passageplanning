package com.bearcave.passageplanning.thames_tide_provider.web.configurationitems;


import com.bearcave.passageplanning.thames_tide_provider.web.configurationitems.exceptions.GaugeNotFoundException;

public enum Gauge {
    MARGATE(1,"Margate", "0103"),
    SOUTHEND(2, "Southend", "0110"),
    CORYTON(3, "Coryton", "0110A"),
    TILBURY(4, "Tilbury", "0111"),
    NORTH_WOOLWICH(5, "North Woolwich", "0112"),
    LONDON_BRIDGE(6, "London Bridge", "0113"),
    CHELSEA_BRIDGE(7, "Chealse Bridge", "0113A"),
    RICHMOND(8, "Richmond", "0116"),
    SHIVERING_SAND(9, "Shivering Sand", "0116A"),
    WALTON_ON_THE_NAZE(10, "Walton on the Naze", "0129");

    private final String codeId;
    private final String name;
    private final int id;

    Gauge(int id, String name, String code) {
        this.id = id;
        this.name = name;
        this.codeId = code;
    }

    public int getId(){
        return this.id;
    }

    public String getCode() {
        return codeId;
    }

    public String getName() {
        return name;
    }

    public static Gauge getById(int id){
        for(Gauge item: Gauge.values()){
            if(item.getId() == id){
                return item;
            }
        }

        throw new GaugeNotFoundException();
    }

    public static Gauge getByCode(String code){
        for(Gauge item: Gauge.values()){
            if(item.getCode() == code){
                return item;
            }
        }

        throw new GaugeNotFoundException();
    }
}
