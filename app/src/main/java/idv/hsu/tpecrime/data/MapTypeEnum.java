package idv.hsu.tpecrime.data;

public enum MapTypeEnum {
    TYPE_HOUSE(0),
    TYPE_CAR(1),
    TYPE_BIKE(2);

    private final int value;

    MapTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
