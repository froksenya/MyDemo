import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Calculate {
    public static void main(String[] args) {
        System.out.println("Введите математическое выражение с двумя переменными, используя арабские или римские цифры от 1 до 10");
        Scanner data = new Scanner(System.in);
        InputString input = new InputString();
        input.inputString = data.nextLine();

        try {
            input.inputString = input.inputStringCheck(input.inputString);
            input.specialCharacter = input.specialCharacterSearching(input.inputString);
            input.arrayNumbersInString = input.NumbersSearching(input.inputString, input.specialCharacter);

            if (input.IsNumeric(input.arrayNumbersInString[0]) && input.IsNumeric(input.arrayNumbersInString[1])) {
                input.numberOne = Integer.parseInt(input.arrayNumbersInString[0]);
                input.numberTwo = Integer.parseInt(input.arrayNumbersInString[1]);
                if (input.IsFromOnetoTen(input.numberOne) && input.IsFromOnetoTen(input.numberTwo)) {
                    input.result = input.MathOperation(input.numberOne, input.numberTwo, input.specialCharacter);
                    System.out.println(input.result);
                }
            } else {
                    if ((input.IsFromOnetoTen(Roman.valueOf(input.arrayNumbersInString[0]).toInt())) &&
                            (input.IsFromOnetoTen(Roman.valueOf(input.arrayNumbersInString[1]).toInt()))) {

                        input.numberOne = Roman.valueOf(input.arrayNumbersInString[0]).toInt();
                        input.numberTwo = Roman.valueOf(input.arrayNumbersInString[1]).toInt();
                        input.result = input.MathOperationRoman(input.MathOperation(input.numberOne, input.numberTwo, input.specialCharacter));
                        System.out.println(Roman.values()[input.result-1]);
                    }

            }

        } catch (EmptyString e) {
            System.out.println(e.getMessage());
            System.exit(0);
        } catch (IncorrectNumber e) {
            System.out.println(e.getMessage());
            System.exit(0);
        } catch (IncorrectOperation e) {
            System.out.println(e.getMessage());
            System.exit(0);
        } catch (IllegalArgumentException e) {
                System.out.println("Числа введены не корректно! Используйте либо только римские, либо только арабские цифры от 1 до 10!");
        }

    }

}

class InputString {

    String inputString;
    char specialCharacter;
    int numberOne;
    int numberTwo;
    int result;
    String[] arrayNumbersInString = new String[2];

    String inputStringCheck(String s) throws EmptyString {
        if (s.isBlank() || s == null) {
            throw new EmptyString("Строка не должна быть пустой!");
        }
        return s;
    }


    char specialCharacterSearching(String st) throws IncorrectOperation {

        List<Character> listSpecialCharacters = new ArrayList<>();
        int j = 0;

        char arrayCharacters[] = st.toCharArray();

        for (Character character : arrayCharacters) {
            if (character.equals('+') || character.equals('-') || character.equals('*') || character.equals('/')) {
                listSpecialCharacters.add(j, character);
                j++;
            }
        }

        if (listSpecialCharacters.size() > 1)
            throw new IncorrectOperation("Математическая операция задана не корректно");

        if (listSpecialCharacters.size() == 0)
            throw new IncorrectOperation("Введённая строка не является математической операцией");

        return listSpecialCharacters.get(0);
    }

    String[] NumbersSearching(String string, char character) throws IncorrectOperation {

        if ((character == '-') || (character == '/')) {
            String s = String.valueOf(character);
            String arrayNumbers[] = string.split(s, 2);
            return StringNumberCheck(arrayNumbers);

        } else {
            if (character == '*') {
                String arrayNumbers[] = string.split("\\*", 2);
                return StringNumberCheck(arrayNumbers);
                }

            else {
                String arrayNumbers[] = string.split("\\+", 2);
                return StringNumberCheck(arrayNumbers);
            }

        }

    }

    String [] StringNumberCheck (String [] array) throws IncorrectOperation {
        for (int i = 0; i < array.length; i++) {
            if (array[i].isBlank()) {
                throw new IncorrectOperation("Введённая строка не является математической операцией");
            } else
                array[i] = array[i].trim();
                array[i] = array[i].toUpperCase();
        }
        return array;
    }


    boolean IsNumeric (String str)  {
        try {
            int check = Integer.parseInt(str);
            return true;

        } catch (NumberFormatException e) {
            return false;
        }
    }

    boolean IsFromOnetoTen(int number) throws IncorrectNumber {
        if ((number >= 1) && (number <= 10))
            return true;
        else
            throw new IncorrectNumber ("Числа должны быть от 1 до 10!");

    }

    int MathOperation (int one, int two, char operation) {

        int result = 0;
        switch (operation) {
            case '-' : result = one - two;
            break;
            case '/' : result = (int) (one / two);
            break;
            case '+' : result = one + two;
            break;
            case '*' : result = one * two;
            break;
        }
        return result;
    }

    int MathOperationRoman (int result) throws IncorrectNumber {
        int resultRoman = 0;
        if (result >= 1) {
            resultRoman = result;
            return resultRoman;
        } else
            throw new IncorrectNumber("Результата нет - в римской системе исчисления нет отрицательных чисел и ноля!");

    }

}

class EmptyString extends Exception {
    public EmptyString (String message) {
        super(message);
    }

}

class IncorrectOperation extends Exception {
    public IncorrectOperation (String message) {
        super(message);
    }
}

class IncorrectNumber extends Exception {
    public IncorrectNumber (String message) {
        super(message);
    }
}