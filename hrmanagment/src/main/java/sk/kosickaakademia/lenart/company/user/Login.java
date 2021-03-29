package sk.kosickaakademia.lenart.company.user;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Login {
    private Map<String, Date> blocked;
    private Map<String, Integer> attempt;
    private final String PASSWORD = "Samo";

    public Login() {
        blocked = new HashMap<>();
        attempt = new HashMap<>();
    }

    public String loginUser(String username, String password) {
        for (int passAttempts = 0; passAttempts < 3; passAttempts++) {
            Scanner input = new Scanner(System.in);
            String inputPass = input.nextLine();

            if (!(inputPass.equals(password))) {
                System.out.println("Wrong Password Try Again");
            } else {
                System.out.println("Welcome!");
                break;
            }
            return username;
        }
        return null;
    }
}
