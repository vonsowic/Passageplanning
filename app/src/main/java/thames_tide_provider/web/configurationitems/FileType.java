package thames_tide_provider.web.configurationitems;

/**
 * Created by miwas on 18.05.17.
 */
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
