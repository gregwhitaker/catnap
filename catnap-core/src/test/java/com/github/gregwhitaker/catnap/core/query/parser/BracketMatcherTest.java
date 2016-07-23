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

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BracketMatcherTest {

    @Test
    public void matchParenthesis() {
        assertEquals(2, BracketMatcher.getClosingParenthesisIndex("(a)", 0));
    }

    @Test
    public void matchOuterNestedParenthesis() {
        assertEquals(9, BracketMatcher.getClosingParenthesisIndex("a(a1(a1a))", 1));
    }

    @Test
    public void matchInnerNestedParenthesis() {
        assertEquals(8, BracketMatcher.getClosingParenthesisIndex("a(a1(a1a))", 4));
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidOpenParenthesisStartIndexThrowsException() {
        BracketMatcher.getClosingParenthesisIndex("a(a1)", 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void unclosedParenthesisThrowsException() {
        BracketMatcher.getClosingParenthesisIndex("a(a1", 1);
    }

    @Test
    public void matchSquareBracket() {
        assertEquals(2, BracketMatcher.getClosingSquareBracketIndex("[a]", 0));
    }

    @Test
    public void matchCurlyBrace() {
        assertEquals(2, BracketMatcher.getClosingCurlyBraceIndex("{a}", 0));
    }

    @Test
    public void matchTag() {
        assertEquals(2, BracketMatcher.getClosingTagIndex("<a>", 0));
    }
}
