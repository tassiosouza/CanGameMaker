package hollowsoft.cangamemaker.model;

public enum Gender {

    MALE (1), FEMALE (2);

    private final int value;

    Gender(final int value) {
        this.value = value;
    }

    public final int getValue() {
        return value;
    }

    public static Gender getBy(final int value) {

        for (final Gender gender : Gender.values()) {

            if (gender.getValue() == value) {
                return gender;
            }
        }

        throw new IllegalArgumentException("The Gender has not found.");
    }
}
