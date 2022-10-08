package govtech.utils;

import java.util.Random;

public class Utils {

    public static String generateRandomNumber(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max-min)+1)+min;
        return String.valueOf(randomNum);
    }
}
