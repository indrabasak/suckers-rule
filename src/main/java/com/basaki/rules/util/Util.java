package com.basaki.rules.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class Util {

    public static Integer getInteger(String param) {
        return isNumber(param) ? NumberUtils.createInteger(param) : null;
    }

    private static boolean isNumber(String param) {
        return StringUtils.isNotEmpty(param) && NumberUtils.isCreatable(param);
    }

    /**
     * Compares an age in days with minimum and maximum age in days. The algorithm
     * used for comparing the age:
     * <ul>
     *     <li>if only minimum age is present, returns true if the age is
     *     greater than equal to the minimum age.</li>
     *     <li>if both minimum and maximum ages are present, returns true
     *     if the age is greater than equal to the minimum age or less than
     *     equal to the maximum age provided minimum age is less than maximum age.
     *     If minimum and maximum ages are same, returns true if the age is equal
     *     to the minimum age.</li>
     *     <li>if only maximum age is present, returns true if the age is less than
     *     equal to the maximum age.</li>
     * </ul>
     *
     * @param age    age of a device or an account
     * @param minAge minimum age to compare with
     * @param maxAge maximum age to compare with
     * @return true if the condition matches the above algorithm, false otherwise
     */
    public static boolean compareAge(long age, Integer minAge, Integer maxAge) {
        boolean retVal = false;
        if (minAge != null && maxAge == null) {
            retVal = (age >= minAge);
        } else if (minAge != null && minAge < maxAge) {
            retVal = (age >= minAge) && (age <= maxAge);
        } else if (minAge != null && minAge.equals(maxAge)) {
            retVal =  (age == minAge);
        } else if (minAge == null) {
            retVal = (age <= maxAge);
        }

        return retVal;
    }
}
