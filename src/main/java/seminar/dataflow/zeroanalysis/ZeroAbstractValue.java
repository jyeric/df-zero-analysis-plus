package seminar.dataflow.zeroanalysis;

/**
 * This enum represents the possible values of the zero analysis for a variable.
 * This is the extended lattice: besides -, 0 and +, it can say that a variable is <= 0, >= 0 or != 0.
 */
public enum ZeroAbstractValue {

    /**
     * We don't have information about the variable.
     */
    BOTTOM("bottom"),

    /**
     * The variable is negative (< 0).
     */
    NEGATIVE("negative"),

    /**
     * The variable is zero.
     */
    ZERO("zero"),

    /**
     * The variable is positive (> 0).
     */
    POSITIVE("positive"),

    /**
     * The variable is negative or zero (<= 0).
     */
    NON_POSITIVE("non-positive"),

    /**
     * The variable is positive or zero (>= 0).
     */
    NON_NEGATIVE("non-negative"),

    /**
     * The variable is not zero (< 0 or > 0).
     */
    NOT_ZERO("not-zero"),

    /**
     * The variable is top (can be any value).
     */
    TOP("top");


    /**
     * The name of the ZeroAbstractValue.
     */
    private final String name;

    @Override
    public String toString() {
        return this.name;
    }

    ZeroAbstractValue(String name) {
        this.name = name;
    }

    /**
     * Returns the result of the addition between this ZeroAbstractValue and another.
     * @param another the other ZeroAbstractValue.
     * @return the result of the addition.
     */
    public ZeroAbstractValue add(ZeroAbstractValue another) {
        if (this.equals(BOTTOM) || another.equals(BOTTOM)) {
            return BOTTOM;
        }
        else if (this.equals(ZERO)) {
            return another;
        }
        else if (another.equals(ZERO)) {
            return this;
        }
        else if (this.equals(NEGATIVE)) {
            switch (another) {
                case NEGATIVE: return NEGATIVE;
                case NON_POSITIVE: return NEGATIVE;
                default: return TOP;
            }
        }
        else if (this.equals(POSITIVE)) {
            switch (another) {
                case POSITIVE: return POSITIVE;
                case NON_NEGATIVE: return POSITIVE;
                default: return TOP;
            }
        }
        else if (this.equals(NON_POSITIVE)) {
            switch (another) {
                case NON_POSITIVE: return NON_POSITIVE;
                case NEGATIVE: return NEGATIVE;
                default: return TOP;
            }
        }
        else if (this.equals(NON_NEGATIVE)) {
            switch (another) {
                case POSITIVE: return POSITIVE;
                case NON_NEGATIVE: return NON_NEGATIVE;
                default: return TOP;
            }
        }
        else {
            return TOP;
        }
    }

    /**
     * Returns the result of the division between this ZeroAbstractValue and another.
     * @param another the other ZeroAbstractValue.
     * @return the result of the division.
     */
    public ZeroAbstractValue divideBy(ZeroAbstractValue another) {
        if (this.equals(BOTTOM) || another.equals(BOTTOM)) {
            return BOTTOM;
        }
        else if (another.equals(ZERO)) {
            return BOTTOM;
        }
        else if (this.equals(ZERO)) {
            return ZERO;
        }
        else if (this.equals(NEGATIVE) || this.equals(NON_POSITIVE)) {
            switch (another) {
                case NEGATIVE: return NON_NEGATIVE;
                case POSITIVE: return NON_POSITIVE;
                case NON_NEGATIVE: return NON_POSITIVE;
                case NON_POSITIVE: return NON_NEGATIVE;
                default: return TOP;
            }
        }
        else if (this.equals(POSITIVE) || this.equals(NON_NEGATIVE)) {
            switch (another) {
                case NEGATIVE: return NON_POSITIVE;
                case POSITIVE: return NON_NEGATIVE;
                case NON_NEGATIVE: return NON_NEGATIVE;
                case NON_POSITIVE: return NON_POSITIVE;
                default: return TOP;
            }
        }
        else {
            return TOP;
        }
    }

    /**
     * Returns the result of the multiplication between this ZeroAbstractValue and another.
     * @param another the other ZeroAbstractValue.
     * @return the result of the multiplication.
     */
    public ZeroAbstractValue multiplyBy(ZeroAbstractValue another) {
        if (this.equals(BOTTOM) || another.equals(BOTTOM)) {
            return BOTTOM;
        }
        else if (this.equals(ZERO) || another.equals(ZERO)) {
            return ZERO;
        }
        else if (this.equals(POSITIVE)) {
            return another;
        }
        else if (this.equals(NEGATIVE)) {
            switch (another) {
                case POSITIVE: return NEGATIVE;
                case NEGATIVE: return POSITIVE;
                case NON_NEGATIVE: return NON_POSITIVE;
                case NON_POSITIVE: return NON_NEGATIVE;
                default: return another;
            }
        }
        else if (this.equals(NON_POSITIVE)) {
            switch (another) {
                case NEGATIVE: return NON_NEGATIVE;
                case NON_POSITIVE: return NON_NEGATIVE;
                case POSITIVE: return NON_POSITIVE;
                case NON_NEGATIVE: return NON_POSITIVE;
                default: return TOP;
            }
        }
        else if (this.equals(NON_NEGATIVE)) {
            switch (another) {
                case NEGATIVE: return NON_POSITIVE;
                case NON_POSITIVE: return NON_POSITIVE;
                case POSITIVE: return NON_NEGATIVE;
                case NON_NEGATIVE: return NON_NEGATIVE;
                default: return TOP;
            }
        }
        else if (this.equals(NOT_ZERO)) {
            if (another.equals(NON_NEGATIVE) || another.equals(NON_POSITIVE) || another.equals(TOP)) {
                return TOP;
            }
            else {
                return NOT_ZERO;
            }
        }
        else {
            return TOP;
        }
    }

    /**
     * Returns the result of the subtraction between this ZeroAbstractValue and another.
     * @param another the other ZeroAbstractValue.
     * @return the result of the subtraction.
     */
    public ZeroAbstractValue substract(ZeroAbstractValue another) {
        if (this.equals(BOTTOM) || another.equals(BOTTOM)) {
            return BOTTOM;
        }
        else if (this.equals(ZERO)) {
            switch (another) {
                case NEGATIVE: return POSITIVE;
                case POSITIVE: return NEGATIVE;
                case NON_NEGATIVE: return NON_POSITIVE;
                case NON_POSITIVE: return NON_NEGATIVE;
                default: return another;
            }
        }
        else if (another.equals(ZERO)) {
            return this;
        }
        else if (this.equals(NEGATIVE)) {
            switch (another) {
                case POSITIVE: return NEGATIVE;
                case NON_NEGATIVE: return NEGATIVE;
                default: return TOP;
            }
        }
        else if (this.equals(POSITIVE)) {
            switch (another) {
                case NEGATIVE: return POSITIVE;
                case NON_POSITIVE: return POSITIVE;
                default: return TOP;
            }
        }
        else if (this.equals(NON_POSITIVE)) {
            switch (another) {
                case POSITIVE: return NEGATIVE;
                case NON_NEGATIVE: return NON_POSITIVE;
                default: return TOP;
            }
        }
        else if (this.equals(NON_NEGATIVE)) {
            switch (another) {
                case NEGATIVE: return POSITIVE;
                case NON_POSITIVE: return NON_NEGATIVE;
                default: return TOP;
            }
        }
        else {
            return TOP;
        }
    }

    /**
     * Returns the result of the merge between this ZeroAbstractValue and another.
     * @param another the other ZeroAbstractValue.
     * @return the result of the merge.
     */
    public ZeroAbstractValue merge(ZeroAbstractValue another) {
        if (this.equals(BOTTOM)) {
            return another;
        }
        else if (another.equals(BOTTOM)) {
            return this;
        }
        else if (this.equals(another)) {
            return this;
        }
        else if (this.equals(NEGATIVE)) {
            switch (another) {
                case ZERO: return NON_POSITIVE;
                case POSITIVE: return NOT_ZERO;
                case NON_NEGATIVE: return TOP;
                case NOT_ZERO: return NOT_ZERO;
                default: return another;
            }
        }
        else if (this.equals(ZERO)) {
            switch (another) {
                case POSITIVE: return NON_NEGATIVE;
                case NEGATIVE: return NON_POSITIVE;
                case NOT_ZERO: return TOP;
                default: return another;
            }
        }
        else if (this.equals(POSITIVE)) {
            switch (another) {
                case NEGATIVE: return NOT_ZERO;
                case ZERO: return NON_NEGATIVE;
                case NON_POSITIVE: return TOP;
                default: return another;
            }
        }
        else if (this.equals(NON_POSITIVE)) {
            switch (another) {
                case NEGATIVE: return NON_POSITIVE;
                case ZERO: return NON_POSITIVE;
                default: return TOP;
            }
        }
        else if (this.equals(NON_NEGATIVE)) {
            switch (another) {
                case ZERO: return NON_NEGATIVE;
                case POSITIVE: return NON_NEGATIVE;
                default: return TOP;
            }
        }
        else if (this.equals(NOT_ZERO)) {
            switch (another) {
                case NEGATIVE: return NOT_ZERO;
                case POSITIVE: return NOT_ZERO;
                default: return TOP;
            }
        }
        else {
            return TOP;
        }
    }

}
