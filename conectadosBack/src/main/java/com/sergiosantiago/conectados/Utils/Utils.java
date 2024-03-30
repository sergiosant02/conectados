package com.sergiosantiago.conectados.Utils;

import java.util.Random;

import org.springframework.security.core.userdetails.UserDetails;

import com.sergiosantiago.conectados.models.User;
import com.sergiosantiago.conectados.services.UserService;

public class Utils {

    public static Random random = new Random();

    public static String getRandomChar() {
        int ascii = random.nextInt(25) + 65;
        return String.valueOf((char) ascii);
    }

    public static String getRandomString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(getRandomChar());
        }
        return sb.toString();
    }

    public static User getUserFromRequest(UserDetails userDetails, UserService userService) {
        String username = userDetails.getUsername();
        User user = userService.loadUserByUsername(username);
        return user;
    }

}
