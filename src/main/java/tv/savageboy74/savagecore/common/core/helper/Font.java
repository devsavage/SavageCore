package tv.savageboy74.savagecore.common.core.helper;

/*
 * Font.java
 * Copyright (C) 2014 - 2015 Savage - github.com/savageboy74
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

/**
 * Defines font constants such as colors and formatting.
 */
public final class Font
{
    private static final String SECTION_SIGN = "\u00a7";

    private Font()
    {
    }

    /**
     * All possible font colors
     */
    public final class Color
    {
        public static final String BLACK = SECTION_SIGN + "0";
        public static final String DARKBLUE = SECTION_SIGN + "1";
        public static final String DARKGREEN = SECTION_SIGN + "2";
        public static final String DARKAQUA = SECTION_SIGN + "3";
        public static final String DARKRED = SECTION_SIGN + "4";
        public static final String PURPLE = SECTION_SIGN + "5";
        public static final String GOLD = SECTION_SIGN + "6";
        public static final String GRAY = SECTION_SIGN + "7";
        public static final String DARKGRAY = SECTION_SIGN + "8";
        public static final String BLUE = SECTION_SIGN + "9";
        public static final String GREEN = SECTION_SIGN + "A";
        public static final String AQUA = SECTION_SIGN + "B";
        public static final String RED = SECTION_SIGN + "C";
        public static final String LIGHTPURPLE = SECTION_SIGN + "D";
        public static final String YELLOW = SECTION_SIGN + "E";
        public static final String WHITE = SECTION_SIGN + "F";
    }

    /**
     * All possible font formats.
     */
    public final class Format
    {
        public static final String OBFUSCATED = SECTION_SIGN + "k";
        public static final String BOLD = SECTION_SIGN + "l";
        public static final String STRIKE = SECTION_SIGN + "m";
        public static final String UNDERLINE = SECTION_SIGN + "n";
        public static final String ITALIC = SECTION_SIGN + "o";
        public static final String RESET = SECTION_SIGN + "r";
    }
}