/*
 * Copyright 2016 Greg Whitaker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.gregwhitaker.catnap.core.query.parser;

/**
 *
 */
public class BracketMatcher {

    /**
     * @param chars
     * @param startIndex
     * @return
     */
    public static int getClosingParenthesisIndex(CharSequence chars, int startIndex) {
        return getClosingBracketIndex(chars, startIndex, "(", ")");
    }

    /**
     * @param chars
     * @param startIndex
     * @return
     */
    public static int getClosingSquareBracketIndex(CharSequence chars, int startIndex) {
        return getClosingBracketIndex(chars, startIndex, "[", "]");
    }

    /**
     * @param chars
     * @param startIndex
     * @return
     */
    public static int getClosingCurlyBraceIndex(CharSequence chars, int startIndex) {
        return getClosingBracketIndex(chars, startIndex, "{", "}");
    }

    /**
     * @param chars
     * @param startIndex
     * @return
     */
    public static int getClosingTagIndex(CharSequence chars, int startIndex) {
        return getClosingBracketIndex(chars, startIndex, "<", ">");
    }

    /**
     * @param chars
     * @param startIndex
     * @param openBracket
     * @param closeBracket
     * @return
     */
    public static int getClosingBracketIndex(CharSequence chars, int startIndex, String openBracket, String closeBracket) {
        if (!isBracket(chars, startIndex, openBracket)) {
            throw new IllegalArgumentException(String.format("The opening bracket, '%s', was not found at index %s",
                    openBracket, startIndex));
        }

        int index = startIndex + openBracket.length();
        int openBracketCount = 1;

        while (openBracketCount > 0 && index < chars.length()) {
            if (isBracket(chars, index, openBracket)) {
                openBracketCount++;
            } else if (isBracket(chars, index, closeBracket)) {
                openBracketCount--;
            }

            if (openBracketCount != 0) {
                index++;
            }
        }

        if (openBracketCount > 0) {
            throw new IllegalArgumentException(String.format("Not all opening '%s' have a corresponding closing '%s'",
                    openBracket, closeBracket));
        }

        return index;
    }

    private static boolean isBracket(CharSequence chars, int index, String bracket) {
        try {
            StringBuilder buffer = new StringBuilder(bracket.length());
            buffer.append(chars.subSequence(index, index + bracket.length()));

            if (buffer.toString().equals(bracket)) {
                return true;
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }

        return false;
    }
}
