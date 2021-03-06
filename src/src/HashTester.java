import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;


/**
 * This is a sample program using several message digest algorithms.
 * This program supports the following algorithms: MD2, MD5, SHA-1, SHA-256, SHA-384, SHA-512
 * How to use this program:
 * <OL>
 * 	<LI> Get an instance of an algorithm (by calling the constructor and passing it one of the supported algorithm names).
 * 	<LI> Call the update() function and update the digest (can call it several times within a loop).
 * 	<LI> At the end call the digest() method.
 * 	<LI> Use the formatHashValue() to print your result.
 * </OL>
 */
public class HashTester {
    private MessageDigest md = null;

    /**
     * Converts an array of bytes into a hex-number and returns the result as a formated string.
     * @param hashBytes is an array of bytes.
     * @return a formatted string containing the number of bits used to represent the hash value and the hash value itself in radix 16.
     */
    public String formatHashValue(byte[] hashBytes) {
        BigInteger bi = new BigInteger(1, hashBytes);

        return new String("(" + hashBytes.length + " bytes) " + bi.toString(16));
    }


    /**
     * Valid algorithms are: MD2, MD5, SHA-1, SHA-256, SHA-384, SHA-512
     */
    public HashTester(String alg) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        md = MessageDigest.getInstance(alg);
    }

    /**
     * Updates the Message Digest object.
     */
    public void update(byte[] data) {
        md.update(data);
    }

    /**
     * Updates the Message Digest object.
     */
    public void update(String str) {
        md.update(str.getBytes());
    }

    /**
     * Completes the hash computation by performing final operations such as padding.
     * The digest is reset after this call is made.
     */
    public byte[] digest() {
        return md.digest();
    }

    /**
     * Reset the digest for future uses.
     * Note that digest() does reset the digest.
     */
    public void reset() {
        md.reset();
    }



    /**
     * Optional main for testing this class.
     */
    public static void main(String[] args) {
        try{
            if (args.length<1){
                System.out.println("Provide file name please.");
                System.exit(1);
            }
            HashTester ht = new HashTester("SHA-256");
            FileReader fileReader = new FileReader(args[0]);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String str;
            while((str = bufferedReader.readLine()) != null) {
                ht.update(str);
            }
            String ss = ht.formatHashValue(ht.digest());

            System.out.println(ss);

        }
        catch(Exception e){
            System.out.println(e);
            System.exit(-1);
        }
    }
}

