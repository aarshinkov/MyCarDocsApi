package bg.forcar.api.beans;

import java.security.*;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
public class RandomBuilder {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final Random RANDOM = new SecureRandom();
    private final String DIGITS = "0123456789";
    private final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private final String DIG_ALPHA = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public String generatePublicId(int length) {
        return generateRandomString(length, true);
    }

    /**
     * Having in mind the given length of the number the method generated a
     * random number. It automatically check not to return a number that starts
     * with the 0 as a first digit
     *
     * @param length the length of the random number
     * @return the newly generated random number
     */
    public Integer generateRandomNumber(int length) {
        StringBuilder builder = new StringBuilder(length);

        boolean isZero = true;
        char currChar = '0';
        while (isZero) {
            currChar = DIGITS.charAt(RANDOM.nextInt(DIGITS.length()));
            isZero = currChar == '0';
        }

        builder.append(currChar);

        for (int i = 1; i < length; i++) {
            builder.append(DIGITS.charAt(RANDOM.nextInt(DIGITS.length())));
        }

        return Integer.parseInt(String.valueOf(builder));
    }

    /**
     * Takes length as a parameter and returns a random string with the english
     * alphabet
     *
     * @param length the length of the string as characters
     * @param addDigits if is true it will include digits in the string
     * @return the randomly generated string
     */
    public String generateRandomString(int length, boolean addDigits) {
        StringBuilder builder = new StringBuilder(length);

        String currAlphabet = ALPHABET;
        boolean hasDigit = false;

        if (addDigits) {
            currAlphabet = DIG_ALPHA;

            while (!hasDigit) {
                builder = new StringBuilder();

                for (int i = 0; i < length; i++) {
                    builder.append(currAlphabet.charAt(RANDOM.nextInt(currAlphabet.length())));
                }

                hasDigit = hasDigitInString(String.valueOf(builder));
            }

            return String.valueOf(builder);
        }

        for (int i = 0; i < length; i++) {
            builder.append(currAlphabet.charAt(RANDOM.nextInt(currAlphabet.length())));
        }

        return String.valueOf(builder);
    }

    public boolean hasDigitInString(String randomString) {
        String[] split = randomString.split("");

        for (String character : split) {
            if (Character.isDigit(character.charAt(0))) {
                return true;
            }
        }

        return false;
    }
}
