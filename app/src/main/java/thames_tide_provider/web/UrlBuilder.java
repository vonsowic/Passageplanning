package thames_tide_provider.web;

import com.bearcave.thames_tide_provider.web.configurationitems.FileType;
import com.bearcave.thames_tide_provider.web.configurationitems.Gauge;
import com.bearcave.thames_tide_provider.web.configurationitems.MinuteStep;
import com.bearcave.thames_tide_provider.web.configurationitems.NumberOfDays;

import org.joda.time.LocalDateTime;


public class UrlBuilder {

    private static final String URL = "http://tidepredictions.pla.co.uk/listing_page";
    private static final String separator = "/";

    private String fileType;
    private String gaugeCode;
    private LocalDateTime time;
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

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setNumberOfDays(NumberOfDays numberOfDays) {
        this.numberOfDays = numberOfDays.getLength();
    }

    public void setStep(MinuteStep step) {
        this.step = step.getStep();
    }
}
