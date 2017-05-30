package com.bearcave.passageplanning.thames_tide_provider.web;


import com.bearcave.passageplanning.thames_tide_provider.database.TideItem;
import com.bearcave.passageplanning.thames_tide_provider.web.configurationitems.Gauge;
import com.bearcave.passageplanning.thames_tide_provider.web.configurationitems.MinuteStep;
import com.bearcave.passageplanning.thames_tide_provider.web.configurationitems.NumberOfDays;

import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

public class TideProvider {

    private UrlBuilder url;

    public TideProvider() {
        this.url = new UrlBuilder();
    }

    public HashSet<TideItem> load(Gauge gauge, DateTime time, NumberOfDays numberOfDays, MinuteStep step) throws IOException {
        HashSet<TideItem> result = new HashSet<>();
        Elements data = get(gauge, time, numberOfDays, step).getElementsByTag("item");

        for (Element element: data){
            TideItem item = convertElement(element);
            result.add(item);
        }

        return result;
    }

    private Document get(com.bearcave.passageplanning.thames_tide_provider.web.configurationitems.Gauge gauge, DateTime time, com.bearcave.passageplanning.thames_tide_provider.web.configurationitems.NumberOfDays numberOfDays, com.bearcave.passageplanning.thames_tide_provider.web.configurationitems.MinuteStep step) throws IOException {
        url.setGaugeCode(gauge);
        url.setNumberOfDays(numberOfDays);
        url.setTime(time);
        url.setStep(step);
        return Jsoup.connect(url.build()).get();
    }

    private TideItem convertElement(Element data){
        return new TideItem(
                Float.valueOf(data.getElementsByTag("pred").first().text()),
                getTimeFrom(data)
        );
    }

    private DateTime getTimeFrom(Element element){
        String date = element.getElementsByTag("date").first().text();
        String time = element.getElementsByTag("time").first().text();

        return new DateTime(
                Integer.valueOf(date.substring(0, 4)),
                Integer.valueOf(date.substring(5, 7)),
                Integer.valueOf(date.substring(8, 10)),
                Integer.valueOf(time.substring(0, 2)),
                Integer.valueOf(time.substring(3, 5))
        );

    }
}
