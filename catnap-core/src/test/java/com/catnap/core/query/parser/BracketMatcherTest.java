package com.catnap.core.query.parser;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 *
 * @author gwhit7
 */
public class BracketMatcherTest
{
    @Test
    public void matchParenthesis()
    {
        assertEquals(2, BracketMatcher.getClosingParenthesisIndex("(a)", 0));
    }

    @Test
    public void matchOuterNestedParenthesis()
    {
        assertEquals(9, BracketMatcher.getClosingParenthesisIndex("a(a1(a1a))", 1));
    }

    @Test
    public void matchInnerNestedParenthesis()
    {
        assertEquals(8, BracketMatcher.getClosingParenthesisIndex("a(a1(a1a))", 4));
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidOpenParenthesisStartIndexThrowsException()
    {
        BracketMatcher.getClosingParenthesisIndex("a(a1)", 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void unclosedParenthesisThrowsException()
    {
        BracketMatcher.getClosingParenthesisIndex("a(a1", 1);
    }

    @Test
    public void matchSquareBracket()
    {
        assertEquals(2, BracketMatcher.getClosingSquareBracketIndex("[a]", 0));
    }

    @Test
    public void matchCurlyBrace()
    {
        assertEquals(2, BracketMatcher.getClosingCurlyBraceIndex("{a}", 0));
    }

    @Test
    public void matchTag()
    {
        assertEquals(2, BracketMatcher.getClosingTagIndex("<a>", 0));
    }
}
