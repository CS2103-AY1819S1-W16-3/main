package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Patient's NRIC in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Nric {

    public static final String MESSAGE_NRIC_INVALID_LENGTH = "NRIC length is invalid. It should only contain 9 "
            + "alphanumeric characters";
    public static final String MESSAGE_NRIC_INVALID = "NRIC is provided is invalid/does not exist. Please check again.";
    public static final String MESSAGE_NRIC_CONSTRAINTS = "NRIC should contain only alphanumeric "
            + "characters, beginning with [S,T,F,G] characters followed by a 7 digits and ending with another "
            + "character";
    public static final String NRIC_VALIDATION_REGEX = "(?i)^[STFG]\\d{7}[A-Z]";
    public final String code;

    private static final int NRIC_LENGTH = 9;

    /**
     * Constructs a {@code NRIC}.
     *
     * @param code A NRIC with valid format.
     */
    public Nric(String code) {
        requireNonNull(code);
        checkArgument(isValidNric(code));
        this.code = code;
    }

    public static boolean isValidNric(String code) throws IllegalArgumentException{
        return hasValidLength(code) && hasValidFormat(code) && passCheckSumAlgo(code);
    }

    private static boolean hasValidLength(String code) throws IllegalArgumentException {
        if (code.length() != NRIC_LENGTH) {
            throw new IllegalArgumentException(MESSAGE_NRIC_INVALID_LENGTH);
        }
        return true;
    }

    private static boolean hasValidFormat(String code) throws IllegalArgumentException{
        if (!code.matches(NRIC_VALIDATION_REGEX)) {
            throw new IllegalArgumentException(MESSAGE_NRIC_CONSTRAINTS);
        }
        return true;
    }

    private static boolean passCheckSumAlgo(String code) throws IllegalArgumentException {
        char firstLetter = code.charAt(0);
        int firstDigit = code.charAt(1) - '0';
        int secondDigit = code.charAt(2) - '0';
        int thirdDigit = code.charAt(3) - '0';
        int fourthDigit = code.charAt(4) - '0';
        int fifthDigit = code.charAt(5) - '0';
        int sixthDigit = code.charAt(6) - '0';
        int seventhDigit = code.charAt(7) - '0';
        char lastLetter = code.charAt(8);

        int sum = firstDigit * 2 + secondDigit * 7 + thirdDigit * 6 + fourthDigit * 5 + fifthDigit * 4
                + sixthDigit * 3 + seventhDigit * 2;

        switch (firstLetter) {
            case 'T': case 'G':
                sum += 4;
                break;
            default:
        }

        int remainder = sum % 11;

        if (!hasValidLastChar(lastLetter, remainder, firstLetter)) {
            throw new IllegalArgumentException(MESSAGE_NRIC_INVALID);
        }
        return true;
    }

    private static boolean hasValidLastChar(char toCompare, int remainder, char firstChar) {
        if (firstChar == 'S' || firstChar == 'T') {
            // 0=J, 1=Z, 2=I, 3=H, 4=G, 5=F, 6=E, 7=D, 8=C, 9=B, 10=A
            switch (remainder) {
            case 0:
                return toCompare == 'J';
            case 1:
                return toCompare == 'Z';
            case 2:
                return toCompare == 'I';
            case 3:
                return toCompare == 'H';
            case 4:
                return toCompare == 'G';
            case 5:
                return toCompare == 'F';
            case 6:
                return toCompare == 'E';
            case 7:
                return toCompare == 'D';
            case 8:
                return toCompare == 'C';
            case 9:
                return toCompare == 'B';
            case 10:
                return toCompare == 'A';
            default:
            }

        } else {
            assert firstChar == 'F' || firstChar == 'G';
            // 0=X, 1=W, 2=U, 3=T, 4=R, 5=Q, 6=P, 7=N, 8=M, 9=L, 10=K
            switch (remainder) {
            case 0:
                return toCompare == 'X';
            case 1:
                return toCompare == 'W';
            case 2:
                return toCompare == 'U';
            case 3:
                return toCompare == 'T';
            case 4:
                return toCompare == 'R';
            case 5:
                return toCompare == 'Q';
            case 6:
                return toCompare == 'P';
            case 7:
                return toCompare == 'N';
            case 8:
                return toCompare == 'M';
            case 9:
                return toCompare == 'L';
            case 10:
                return toCompare == 'K';
            default:
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return this.code;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this // short circuit if same object
                || (obj instanceof Nric // instanceof handles nulls
                && code.equalsIgnoreCase(((Nric) obj).code)); // state check
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}
