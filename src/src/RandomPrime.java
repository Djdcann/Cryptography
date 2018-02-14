import java.math.BigInteger;
import java.util.Random;
import java.security.SecureRandom;

public class RandomPrime {
    // Returns a random prime of length N bits
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        SecureRandom random = new SecureRandom();
        BigInteger prime = BigInteger.probablePrime(N, random);
        System.out.println(prime);
    }
}