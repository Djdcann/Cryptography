import java.math.BigInteger;
import java.util.Random;
import java.security.SecureRandom;

public class DiffieHellman {
    private static void exchange(BigInteger q, BigInteger a){
        Random rand = new Random();
        System.out.println("Public info: a = " + a + ", and q = " + q);

        BigInteger XA = new BigInteger(Integer.toString(rand.nextInt(q.intValue())));
        System.out.println("UserA selects XA: " + XA);
        BigInteger YA = a.modPow(XA, q);
        System.out.println("UserA calculates YA: " + YA);
        System.out.println("UserA ----- YA("+YA+") -----> UserB");

        BigInteger XB = new BigInteger(Integer.toString(rand.nextInt(q.intValue())));
        System.out.println("UserB selects XB: " + XB);
        BigInteger YB = a.modPow(XB, q);
        System.out.println("UserB calculates YB: " + YB);
        System.out.println("UserA <----- YB("+YB+") ----- UserB");

        BigInteger KA = YB.modPow(XA, q);
        BigInteger KB = YA.modPow(XB, q);

        System.out.println("\nUserA gets KA: " + KA);
        System.out.println("UserB gets KB: " + KB);
    }

    public static void main(String[] args){
        // q to prime 353 and a to 3 (primitive root)
        BigInteger q = new BigInteger("353");
        BigInteger a = new BigInteger("3");

        exchange(q, a);
        System.out.println("");

        // update q to random prime of 80 bits length
        int numberOfBits = 80;
        q = BigInteger.probablePrime(numberOfBits, new SecureRandom());
        exchange(q, a);
    }
}
