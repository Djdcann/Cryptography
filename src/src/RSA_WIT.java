import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;

public class RSA_WIT extends java.lang.Object{

    private BigInteger p;
    private BigInteger q;
    private BigInteger public_key;
    private BigInteger n;
    private BigInteger phi;
    private BigInteger private_key;

    private static BigInteger ONE = new BigInteger("1");

    public RSA_WIT(java.lang.String the_p,
                   java.lang.String the_q,
                   java.lang.String the_public_key){
        this.p = new BigInteger(the_p);
        this.q = new BigInteger(the_q);
        this.public_key = new BigInteger(the_public_key);

        this.n = p.multiply(q);
        this.phi = (p.subtract(ONE)).multiply(q.subtract(ONE));
        this.private_key = public_key.modInverse(phi);

    }

    public void EncryptFile(java.lang.String inf,
                            java.lang.String outf){

        FileInputStream inFile;
        FileOutputStream outFile;
        try {
            inFile = new FileInputStream(inf);
            outFile = new FileOutputStream(outf);
            BigInteger message = new BigInteger(inFile.readAllBytes());
            outFile.write(message.modPow(public_key, n).toByteArray());
            inFile.close();
            outFile.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void DecryptFile(java.lang.String fname,
                            java.lang.String outfile){

        FileInputStream inFile;
        FileOutputStream outFile;
        try {
            inFile = new FileInputStream(fname);
            outFile = new FileOutputStream(outfile);
            BigInteger message = new BigInteger(inFile.readAllBytes());
            outFile.write(message.modPow(private_key, n).toByteArray());
            inFile.close();
            outFile.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void EncryptDecrypt(java.lang.String inf,
                               java.lang.String outf){
        String e = "encrypt.txt";
        EncryptFile(inf, e);
        DecryptFile(e, outf);
    }

    public void PrintParameters(){
        System.out.println("+--------------------------------------");
        System.out.printf("|p:\t\t\t(%d-bits) %s\n", p.bitLength(), p);
        System.out.printf("|q:\t\t\t(%d-bits) %s\n", q.bitLength(), q);
        System.out.printf("|phi:\t\t(%d-bits) %s\n", phi.bitLength(), phi);
        System.out.printf("|modulus:\t(%d-bits) %s\n", n.bitLength(), n);
        System.out.printf("|PU key:\t(%d-bits) %s\n", public_key.bitLength(), public_key);
        System.out.printf("|PR key:\t(%d-bits) %s\n", private_key.bitLength(), private_key);
        System.out.println("+--------------------------------------");
    }

    @Override
    public String toString() {
        String s = String.format("+--------------------------------------\n"+
                "|\tpublic  = %d\n"+
                "|\tprivate = %d\n"+
                "|\tmodulus = %d\n"+
                "+--------------------------------------", public_key, private_key, n);
        return s;
    }
}
