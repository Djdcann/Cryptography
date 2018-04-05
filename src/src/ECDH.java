import wit.security.ecc.*;
import java.math.*;
import java.util.Random;

public class ECDH {

    public static int numberOfPoints(Point p) {
        int count=1;
        Point t = new Point(p);
        while(true) {
            t = t.add(p);
            if( t.equals(p) ) {
                return count;
            }
            count++;
        }
    }

    public static void main(String[] args) {

        EllipticCurve cc = new EllipticCurve(new BigInteger("11"), new BigInteger("1"), new BigInteger("6"));
        Point P = new Point(cc, new BigInteger("2"), new BigInteger("7"));
        int n = numberOfPoints(P);
        Random rand = new Random();

        System.out.println(cc);
        System.out.println("Starting point P: " + P);
        System.out.println("Number of points in the curve: " + n);

        BigInteger XA = new BigInteger(Integer.toString(rand.nextInt(n)));
        Point PA = P.multiply(XA);

        System.out.println("User A selects a random private key: " + XA);
        System.out.println("User A calculates point PA: " + PA);

        BigInteger XB = new BigInteger(Integer.toString(rand.nextInt(n)));
        Point PB = P.multiply(XB);

        System.out.println("User B selects a random private key: " + XB);
        System.out.println("User B calculates point PB: " + PB);

        // XB and XA are exchanged between users
        System.out.println("User A and B exchange the calculated points.");
        Point KA = PB.multiply(XA);
        System.out.println("User A calculates point KA: " + KA);
        Point KB = PA.multiply(XB);
        System.out.println("User B calculates point KB: " + KB);
    }
}
