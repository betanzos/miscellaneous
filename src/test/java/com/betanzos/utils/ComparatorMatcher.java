/**
 * Copyright 2019 Eduardo E. Betanzos Morales
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.betanzos.utils;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * @author Eduardo Betanzos
 * @since October 26, 2019
 */
public class ComparatorMatcher<T extends Number> extends BaseMatcher<T> {
    private enum ComparationType {
        GREATER,
        LESSER
    }

    private final Object expectedValue;
    private final ComparationType type;

    private ComparatorMatcher(T equalArg, ComparationType type) {
        this.expectedValue = equalArg;
        this.type = type;
    }

    public boolean matches(Object actualValue) {
        if (actualValue instanceof Comparable && expectedValue instanceof Comparable) {
            int result = ((Comparable) actualValue).compareTo(expectedValue);
            return ComparationType.GREATER == type ? result > 0 : result < 0;
        }

        return false;
    }

    public void describeTo(Description description) {
        description.appendValue(this.expectedValue);
    }

    public static <T extends Number> Matcher<T> greaterThan(T operand) {
        return new ComparatorMatcher<T>(operand, ComparationType.GREATER);
    }

    public static <T extends Number> Matcher<T> lesserThan(T operand) {
        return new ComparatorMatcher<T>(operand, ComparationType.LESSER);
    }
}
