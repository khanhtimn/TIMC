package com.teamti.timc.util;

public class RandomUtil {

    public static int nextInt(final int startInclusive, final int endExclusive) {
        if (endExclusive - startInclusive <= 0)
            return startInclusive;

        return startInclusive + new java.util.Random().nextInt(endExclusive - startInclusive);
    }

    public static double nextDouble(final double startInclusive, final double endInclusive) {
        if (startInclusive == endInclusive || endInclusive - startInclusive <= 0D)
            return startInclusive;

        return startInclusive + ((endInclusive - startInclusive) * Math.random());
    }

    public static float nextFloat(final float startInclusive, final float endInclusive) {
        if (startInclusive == endInclusive || endInclusive - startInclusive <= 0F)
            return startInclusive;

        return (float) (startInclusive + ((endInclusive - startInclusive) * Math.random()));
    }

    public static String randomNumber(final int length) {
        return random(length, "123456789");
    }

    public static String randomString(final int length) {
        return random(length, "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
    }

    public static String random(final int length, final String chars) {
        return random(length, chars.toCharArray());
    }

    public static String random(final int length, final char[] chars) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++)
            stringBuilder.append(chars[new java.util.Random().nextInt(chars.length)]);
        return stringBuilder.toString();
    }

    public static double randomNumber(double max, double min) {
        return Math.random() * (max - min) + min;
    }

    public enum RandomShit {

        INSTANCE;
        public int getRandomInt(int max, int min) {
            return (int) (min + Math.random() * (max - min + 1));
        }

        public String getRandomString() {
            int randomNumber = 0;

            randomNumber = getRandomInt(0, 25);

            if (randomNumber == 0) {
                return "a";
            }
            if (randomNumber == 1) {
                return "b";
            }
            if (randomNumber == 2) {
                return "c";
            }
            if (randomNumber == 3) {
                return "d";
            }
            if (randomNumber == 4) {
                return "e";
            }
            if (randomNumber == 5) {
                return "f";
            }
            if (randomNumber == 6) {
                return "g";
            }
            if (randomNumber == 7) {
                return "h";
            }
            if (randomNumber == 8) {
                return "i";
            }
            if (randomNumber == 9) {
                return "j";
            }
            if (randomNumber == 10) {
                return "k";
            }
            if (randomNumber == 11) {
                return "l";
            }
            if (randomNumber == 12) {
                return "m";
            }
            if (randomNumber == 13) {
                return "n";
            }
            if (randomNumber == 14) {
                return "o";
            }
            if (randomNumber == 15) {
                return "p";
            }
            if (randomNumber == 16) {
                return "q";
            }
            if (randomNumber == 17) {
                return "r";
            }
            if (randomNumber == 18) {
                return "s";
            }
            if (randomNumber == 19) {
                return "t";
            }
            if (randomNumber == 20) {
                return "u";
            }
            if (randomNumber == 21) {
                return "v";
            }
            if (randomNumber == 22) {
                return "w";
            }
            if (randomNumber == 23) {
                return "x";
            }
            if (randomNumber == 24) {
                return "y";
            }
            if (randomNumber == 25) {
                return "z";
            }

            return "";

        }

    }
}
