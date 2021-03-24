package sk.kosickaakademia.lenart.company.user;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Login {
    private Map<String, Date> blocked;
    private Map<String, Integer> attempt;

    public Login() {
        blocked = new HashMap<>();
        attempt = new HashMap<>();
    }

    public String loginUser(String username){

        return username;
    }
}
