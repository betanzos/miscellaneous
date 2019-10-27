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

import java.util.Comparator;

/**
 * Compare the {@link String} representation of two {@link Object}{@code s}. {@link String} representation mean that
 * this class will compare the result of call {@code toString()} over the parameter objects.<br>
 * <br>
 * If the {@link String} representation result in an alphanumeric {@link String} this class allows to treat the
 * sequential numeric parts present in the strings as numbers, which will compares as such. With this in mind,
 * fallowing {@link String}{@code s} will be equals:<br>
 * {@code "My serial 2x01.mp4"},<br>
 * {@code "My serial 2x1.mp4"} and<br>
 * {@code "My serial 2x00000001.mp4"}<br>
 * <br>
 * This behaivor is very useful to sort by name files and folders within a directory.<br>
 * <br>
 *
 *
 * @author Eduardo Betanzos
 * @since October 26, 2019
 */
public final class AlphanumericStringComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        char[] chars1 = o1.toString().toCharArray();
        char[] chars2 = o2.toString().toCharArray();

        char a;
        char b;
        int result = 0;

        for (int i = 0, j = 0; i < chars1.length && j < chars2.length; i++, j++) {
            a = chars1[i];
            b = chars2[j];

            if (Character.isDigit(a) && Character.isDigit(b)) {
                int[] compResult = compareNumSequence(i, chars1, j, chars2);

                if (compResult[0] != 0) {
                    result = compResult[0];
                    break;
                }

                i = compResult[1];
                j = compResult[2];
            } else if (a != b) {
                result = a - b;
                break;
            }
        }

        return result;
    }

    private int[] compareNumSequence(int index1, char[] arr1, int index2, char[] arr2) {
        var sb1 = new StringBuilder();
        var sb2 = new StringBuilder();

        while (index1 < arr1.length && Character.isDigit(arr1[index1])) {
            sb1.append(arr1[index1]);
            index1++;
        }

        while (index2 < arr2.length && Character.isDigit(arr2[index2])) {
            sb2.append(arr2[index2]);
            index2++;
        }

        int num1 = Integer.parseInt(sb1.toString());
        int num2 = Integer.parseInt(sb2.toString());

        return new int[] {num1 - num2, --index1, --index2};
    }
}
