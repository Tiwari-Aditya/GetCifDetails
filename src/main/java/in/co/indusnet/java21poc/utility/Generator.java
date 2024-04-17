package in.co.indusnet.java21poc.utility;

import java.util.Random;

public class Generator {
    static Random random = new Random();
    public static String randomNumberGenerator(int length) {

        long number = random.nextLong();
        if(number<=0) {
            number = number * (-1);
        }
        String numberString = Long.toString(number);
        String value=numberString.substring(0,length);
        return value;
    }
}
