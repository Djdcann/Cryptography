import wit.security.ecc.*;
import java.math.*;

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
        Point Q = new Point(cc, new BigInteger("3"), new BigInteger("6"));

        System.out.println(cc);

        System.out.println("EXACT NUMBER OF POINTS: " + numberOfPoints(P));

        System.out.println("1P: " + P);

        Point P2 = P.add(P);
        System.out.println("2P: " + P2);

        Point P4 = P2.add(P2);
        System.out.println("4P: " + P4);

        Point P5 = P4.add(P);
        System.out.println("5P: " + P5);

        Point P9 = P5.add(P4);
        System.out.println("9P: " + P9);

        Point P18 = P9.add(P9);
        System.out.println("18P: " + P18);

        Point M5 = P.multiply(new BigInteger("5"));
        System.out.println("5M: " + M5);

        Point M4 = P.multiply(new BigInteger("4"));
        System.out.println("4M: " + M4);

        Point p_plus_q = P.add(Q);
        System.out.println("P+Q: " + p_plus_q);

        System.out.println("\n--- Points from 1 to 20 ---");
        for(int i=1; i <= 20; i++) {
            Point t = P.multiply(new BigInteger(i+""));
            System.out.println(i+"*P= "+ t);
        }
        System.out.println();

        Point Pi = new Point(cc);
        System.out.println("oo + oo= " + Pi.add(Pi));
        System.out.println("oo + P = " + Pi.add(P));
        System.out.println("P  + oo= " + P.add(Pi));
        System.out.println("oo * 3 = " + Pi.multiply(new BigInteger(3+"")));

        Point a1 = Pi.subtract(P5);
        Point a2 = a1.add(P5);
        System.out.println();
        System.out.println("oo - P5:      " + a1);
        System.out.println("oo - P5 + P5: " + a2);
        System.out.println();

        System.out.println("oo + oo: " + Pi.add(Pi));
        System.out.println("oo - oo: " + Pi.subtract(Pi));
        System.out.println();
        System.out.println("-----------");
        Point B1 = new Point(cc, new BigInteger("5"), new BigInteger("9"));
        Point B2 = new Point(cc, new BigInteger("5"), new BigInteger("2"));
        System.out.println("(5,9) - (5,2): " + B1.subtract(B2));
        System.out.println("Q+(-Q): " + Q.subtract(Q));
    }
}
