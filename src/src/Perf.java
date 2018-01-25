
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/*
 * To Compile: place the DesEncrypter.java and this (Perf.java) in the same directory
 *             and type:    javac *.java
 *
 * To Execute: java Perf
 */


public class Perf {

    public static void main(String [] args) {

        try {
            /***********************************************************************
             *       ENCRYPT AND THEN DECRYPT USING DES
             ***********************************************************************/
            DesEncrypter DES = new DesEncrypter("test passphrase", "PBEWithMD5AndDES" );
            int s = 1024*100000;

            ByteArrayInputStream  bais  = new ByteArrayInputStream(new byte[s]);
            ByteArrayOutputStream baos  = new ByteArrayOutputStream();
            ByteArrayOutputStream baos2  = new ByteArrayOutputStream();

            //long start= System.currentTimeMillis();
            long start= System.nanoTime();
            for(int i = 0; i < 10000; i++ ) {
                DES.encrypt(bais, baos);
                DES.decrypt(new ByteArrayInputStream(baos.toByteArray()), baos2 );
                baos.reset(); // discard accumulated output
                baos2.reset(); // discard accumulated output
            }
            long end= System.nanoTime();
            long elapsedTime = end - start;
            System.out.println("DES: " + elapsedTime + "(nsec)");
            System.out.println("DES: " + (elapsedTime/1000000 )+ "(msec) and " + (elapsedTime%1000000) + "(nsec)");





            /***********************************************************************
             *       ENCRYPT AND THEN DECRYPT USING Triple DES
             ***********************************************************************/
            DesEncrypter TDES = new DesEncrypter("test passphrase", "PBEWithMD5AndTripleDES" );
            bais  = new ByteArrayInputStream(new byte[s]);
            baos  = new ByteArrayOutputStream();
            baos2  = new ByteArrayOutputStream();
            start= System.nanoTime();
            for(int i = 0; i < 10000; i++ ) {
                TDES.encrypt(bais, baos);
                TDES.decrypt(new ByteArrayInputStream(baos.toByteArray()), baos2 );
                baos.reset(); // discard accumulated output
                baos2.reset(); // discard accumulated output
            }
            end= System.nanoTime();
            elapsedTime = end - start;
            System.out.println("3DES: " + elapsedTime + "(nsec)");
            System.out.println("3DES: " + (elapsedTime/1000000 )+ "(msec) and " + (elapsedTime%1000000) + "(nsec)");
        }
        catch (Exception e) { System.err.println(e); }
    }
}
