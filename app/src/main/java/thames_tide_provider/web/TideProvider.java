package thames_tide_provider.web;


import com.bearcave.thames_tide_provider.TideItem;
import com.bearcave.thames_tide_provider.web.*;
import com.bearcave.thames_tide_provider.web.configurationitems.Gauge;
import com.bearcave.thames_tide_provider.web.configurationitems.MinuteStep;
import com.bearcave.thames_tide_provider.web.configurationitems.NumberOfDays;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.stream.Collectors;

public class TideProvider {

    private com.bearcave.thames_tide_provider.web.UrlBuilder url;

    public TideProvider() {
        this.url = new com.bearcave.thames_tide_provider.web.UrlBuilder();
    }

    public HashSet<TideItem> load(Gauge gauge, LocalDateTime time, NumberOfDays numberOfDays, MinuteStep step) throws IOException {
        HashSet<TideItem> result = new HashSet<>();
        Elements data = get(gauge, time, numberOfDays, step).getElementsByTag("item");

        for (Element element: data){
            TideItem item = convertElement(element);
            item.setGaugeId(gauge.getId());
            result.add(item);
        }

        return result;
    }

    private Document get(Gauge gauge, LocalDateTime time, NumberOfDays numberOfDays, MinuteStep step) throws IOException {
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

    private LocalDateTime getTimeFrom(Element element){
        String date = element.getElementsByTag("date").first().text();
        String time = element.getElementsByTag("time").first().text();

        return LocalDateTime.of(
                Integer.valueOf(date.substring(0, 4)),
                Integer.valueOf(date.substring(5, 7)),
                Integer.valueOf(date.substring(8, 10)),
                Integer.valueOf(time.substring(0, 2)),
                Integer.valueOf(time.substring(3, 5))
        );
    }

    public static void main(String args[]){

    }
}
