package logic;

public class RomanNumeral {

    private static int[] numbers = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

    private static String[] letters = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    private int num;

    public RomanNumeral(int arabic) {
        if (arabic < 1) {
            throw new NumberFormatException("Value of Numeral must be positive.");
        }
        if (arabic > 100) {
            throw new NumberFormatException("Value of Numeral must be 100 or less.");
        }
        num = arabic;
    }

    public RomanNumeral(String roman) {
        if (roman.length() == 0) {
            throw new NumberFormatException("An empty string does not define a Roman numeral.");
        }
        int i = 0;
        int arabic = 0;
        while (i < roman.length()) {
            char letter = roman.charAt(i);
            int number = letterToNumber(letter);
            i++;
            if (i == roman.length()) {
                arabic += number;
            } else {
                int nextNumber = letterToNumber(roman.charAt(i));
                if (nextNumber > number) {
                    arabic += nextNumber - number;
                    i++;
                } else {
                    arabic += number;
                }
            }
        }
        if (arabic > 10) {
            throw new NumberFormatException("Roman numeral must have value 10 or less.");
        }
        num = arabic;
    }

    private int letterToNumber(char letter) {
        switch (letter) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                throw new NumberFormatException(
                        "Illegal character \"" + letter + "\" in Roman numeral");
        }
    }

    public int getNum() {
        return num;
    }

    public String toString() {
        String roman = "";
        int n = num;
        for (int i = 0; i < numbers.length; i++) {
            while (n >= numbers[i]) {
                roman += letters[i];
                n -= numbers[i];
            }
        }
        return roman;
    }
}
