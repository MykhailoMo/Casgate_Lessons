package shoppingMall.utils;

import java.security.InvalidParameterException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ValidationInputData {
    public static int checkInputAsPositiveInt(Scanner input) {
        int result = 0;
            try {
                result = input.nextInt();
                if (result <= 0) {
                    throw new InvalidParameterException();
                }
            } catch (InputMismatchException e) {
                System.out.println("You filled invalid number: should be numeric");
            } catch (InvalidParameterException e) {
                System.out.println("You filled invalid number: should be positive");
            }
        return result;
    }

    public static int checkInputAsNaturalInt(Scanner input) {
        int result = 0;
        try {
            result = input.nextInt();
            if (result < 0) {
                throw new InvalidParameterException();
            }
        } catch (InputMismatchException e) {
            System.out.println("You filled invalid number: should be numeric");
        } catch (InvalidParameterException e) {
            System.out.println("You filled invalid number: should be natural integer");
        }
        return result;
    }
}
