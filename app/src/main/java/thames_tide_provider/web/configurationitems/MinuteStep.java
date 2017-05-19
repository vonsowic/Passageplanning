package thames_tide_provider.web.configurationitems;


public enum MinuteStep {
    ONE(1),
    FIVE(5),
    TEN(10),
    FIFTEEN(15),
    HALF_HOUR(30),
    HOUR(60);

    private final int step;

    MinuteStep(int step) {
        this.step = step;
    }

    public int getStep() {
        return step;
    }
}
