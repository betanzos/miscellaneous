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
package com.betanzos.comparators;

import com.betanzos.utils.ComparatorMatcher;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @author Eduardo Betanzos
 * @since October 26, 2019
 */
public class AlphanumericStringComparatorTest {

    private static final AlphanumericStringComparator COMPARATOR = new AlphanumericStringComparator();
    private static final String str1 = "My serial 2x01.mp4";
    private static final String str2 = "My serial 2x1.mp4";
    private static final String str3 = "My serial 2x00000001.mp4";

    private static final String str4 = "1 - Lesson.mp4";
    private static final String str5 = "01 - Lesson.mp4";
    private static final String str6 = "00000000001 - Lesson.mp4";
    private static final String str7 = "10 - Lesson.mp4";

    @Test
    public void testAlphanumericEquals() {
        assertEquals("'" + str1 + "' is not equal to '" + str2 + "'", 0, COMPARATOR.compare(str1, str2));
        assertEquals("'" + str2 + "' is not equal to '" + str3 + "'", 0, COMPARATOR.compare(str2, str3));
        assertEquals("'" + str1 + "' is not equal to '" + str3 + "'", 0, COMPARATOR.compare(str1, str3));

        assertEquals("'" + str4 + "' is not equal to '" + str5 + "'", 0, COMPARATOR.compare(str4, str5));
        assertEquals("'" + str5 + "' is not equal to '" + str6 + "'", 0, COMPARATOR.compare(str5, str6));
        assertEquals("'" + str4 + "' is not equal to '" + str6 + "'", 0, COMPARATOR.compare(str4, str6));
    }

    @Test
    public void testAlphanumericLesserThan() {
        assertThat("'" + str4 + "' is not lesser than '" + str7 + "'", 0, ComparatorMatcher.greaterThan(COMPARATOR.compare(str4, str7)));
        assertThat("'" + str5 + "' is not lesser than '" + str7 + "'", 0, ComparatorMatcher.greaterThan(COMPARATOR.compare(str5, str7)));
        assertThat("'" + str6 + "' is not lesser than '" + str7 + "'", 0, ComparatorMatcher.greaterThan(COMPARATOR.compare(str6, str7)));
    }

    @Test
    public void testOnlyTextEquals() {
        assertEquals(0, COMPARATOR.compare("Java programming languaje", "Java programming languaje"));
    }

    @Test
    public void testOnlyTextLesserThan() {
        assertThat(0, ComparatorMatcher.greaterThan(COMPARATOR.compare("Java programming languaje", "Python programming languaje")));
    }

    @Test
    public void testNumericTextEquals() {
        assertEquals("123 is not equal to 123", 0, COMPARATOR.compare("123", "123"));
        assertEquals("0123 is not equal to 00123", 0, COMPARATOR.compare("0123", "00123"));
    }

    @Test
    public void testNumericTextLesserThan() {
        assertThat("1 is not lesser than 2", 0, ComparatorMatcher.greaterThan(COMPARATOR.compare("1", "2")));
        assertThat("001 is not lesser than 2", 0, ComparatorMatcher.greaterThan(COMPARATOR.compare("001", "2")));
        assertThat("2 is not lesser than 10", 0, ComparatorMatcher.greaterThan(COMPARATOR.compare("2", "10")));
        assertThat("0002 is not lesser than 10", 0, ComparatorMatcher.greaterThan(COMPARATOR.compare("0002", "10")));
    }
}
