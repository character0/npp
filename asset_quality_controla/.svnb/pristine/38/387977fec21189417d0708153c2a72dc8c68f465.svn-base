package states

public enum ManzanitaResultStates {
    PASSED(0), WARNINGS(1), FAILED(2)

    private final int value

    ManzanitaResultStates(int value) {
        this.value = value
    }

    public int value() {
        return value
    }

    public static String stringValue(int value) {
        switch(value) {
            case 0:
                return PASSED.toString()
                break;
            case 1:
                return WARNINGS.toString()
                break;
            case 2:
                return FAILED.toString()
                break;
            default:
                return "?"
        }
    }

}
