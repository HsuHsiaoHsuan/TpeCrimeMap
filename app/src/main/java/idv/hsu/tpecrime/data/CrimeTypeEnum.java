package idv.hsu.tpecrime.data;

public enum CrimeTypeEnum {
    TYPE_HOUSE(0),
    TYPE_CAR(1),
    TYPE_BIKE(2),
    TYPE_WOMAN_CHILD_INJURED(3);

    private final int value;

    CrimeTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
