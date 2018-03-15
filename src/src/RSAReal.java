import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.security.SecureRandom;

public class RSAReal
{
    private final int NB = 4;

    private static final BigInteger one = new BigInteger("1");
    private static final SecureRandom random = new SecureRandom();

    private BigInteger privateKey;
    private BigInteger publicKey;
    private BigInteger modulus;
    private BigInteger p = null;
    private BigInteger q = null;
    private BigInteger phi = null;

    private BigInteger encrypt(BigInteger paramBigInteger)
    {
        return paramBigInteger.modPow(publicKey, modulus);
    }

    private BigInteger decrypt(BigInteger paramBigInteger)
    {
        return paramBigInteger.modPow(privateKey, modulus);
    }

    public String toString()
    {
        String str = "";
        str = str + "+--------------------------------------\n";
        str = str + "|  public  = " + publicKey + "\n";
        str = str + "|  private = " + privateKey + "\n";
        str = str + "|  modulus = " + modulus + "\n";
        str = str + "+--------------------------------------";
        return str;
    }

    public void PrintParameters()
    {
        System.out.println("+--------------------------------------");
        System.out.println("|p:       (" + p.bitLength() + "-bits) " + p);
        System.out.println("|q:       (" + q.bitLength() + "-bits) " + q);
        System.out.println("|phi:     (" + phi.bitLength() + "-bits) " + phi);
        System.out.println("|modulus: (" + modulus.bitLength() + "-bits) " + modulus);
        System.out.println("|PU key:  (" + publicKey.bitLength() + "-bits) " + publicKey);
        System.out.println("|PR key:  (" + privateKey.bitLength() + "-bits) " + privateKey);
        System.out.println("+--------------------------------------");
    }

    public RSAReal(String paramString1, String paramString2, String paramString3)
    {
        p = new BigInteger(paramString1);
        q = new BigInteger(paramString2);
        phi = p.subtract(one).multiply(q.subtract(one));

        modulus = p.multiply(q);
        publicKey = new BigInteger(paramString3);
        privateKey = publicKey.modInverse(phi);
    }

    private void Write_BigInteger_to_file(FileOutputStream paramFileOutputStream, BigInteger paramBigInteger)
    {
        int i = modulus.bitLength() / 8 + 1;

        byte[] arrayOfByte1 = paramBigInteger.toByteArray();
        byte[] arrayOfByte2 = new byte[i];


        if (arrayOfByte1.length > i) {
            System.err.println("SIZE IS: " + arrayOfByte1.length);
            System.exit(-1);
        }


        for (int j = 0; j < arrayOfByte2.length; j++) {
            arrayOfByte2[j] = 0;

            j = arrayOfByte2.length - 1;
            for (int k = arrayOfByte1.length - 1; k >= 0; j--) {
                arrayOfByte2[j] = arrayOfByte1[k];
                k--;
            }
        }

        try
        {
            paramFileOutputStream.write(arrayOfByte2);
            paramFileOutputStream.flush();
        }
        catch (IOException localIOException) {
            System.err.println(localIOException);
            System.exit(-1);
        }
    }

    public void EncryptDecrypt(String paramString1, String paramString2)
    {
        FileOutputStream localFileOutputStream = null;
        FileInputStream localFileInputStream = null;
        try {
            localFileOutputStream = new FileOutputStream(new File(paramString2));
            localFileInputStream = new FileInputStream(new File(paramString1));
        }
        catch (IOException localIOException1) {
            System.err.println(localIOException1);
            System.exit(-1);
        }

        int i = 0;
        byte[] arrayOfByte1 = new byte[4];
        try {
            while ((i = localFileInputStream.read(arrayOfByte1)) != 0) {
                if (i == 4) {
                    BigInteger localBigInteger1 = new BigInteger(1, arrayOfByte1);
                    BigInteger localBigInteger2 = encrypt(localBigInteger1);
                    BigInteger localBigInteger3 = decrypt(localBigInteger2);

                    byte[] arrayOfByte2 = localBigInteger3.toByteArray();
                    byte[] arrayOfByte3;
                    int j;
                    int k; if (arrayOfByte2.length > 4) {
                        arrayOfByte3 = new byte[4];
                        for (j = 0; j < 4; j++) { arrayOfByte3[j] = 0;
                        }
                        j = 3; for (k = arrayOfByte2.length - 1; (j >= 0) && (k >= 0); j--) {
                            arrayOfByte3[j] = arrayOfByte2[k];k--;
                        }
                        localFileOutputStream.write(arrayOfByte3);
                    }
                    else if (arrayOfByte2.length == 4) {
                        localFileOutputStream.write(localBigInteger3.toByteArray());
                    }
                    else {
                        arrayOfByte3 = new byte[4];
                        for (j = 0; j < 4; j++) { arrayOfByte3[j] = 0;
                        }
                        j = 3; for (k = arrayOfByte2.length - 1; (j >= 0) && (k >= 0); k--) {
                            arrayOfByte3[j] = arrayOfByte2[k];j--;
                        }
                        localFileOutputStream.write(arrayOfByte3);
                    }
                }
                else if (i == -1)
                {
                    break;
                }
            }
        }
        catch (IOException localIOException2)
        {
            System.err.println(localIOException2);
            System.exit(-1);
        }

        try
        {
            localFileOutputStream.close();
            localFileInputStream.close();
        }
        catch (IOException localIOException3) {
            System.err.println(localIOException3);
            System.exit(-1);
        }
    }

    public void EncryptFile(String paramString1, String paramString2)
    {
        FileOutputStream localFileOutputStream = null;
        FileInputStream localFileInputStream = null;
        try {
            localFileOutputStream = new FileOutputStream(new File(paramString2));
            localFileInputStream = new FileInputStream(new File(paramString1));
        }
        catch (IOException localIOException1) {
            System.err.println(localIOException1);
            System.exit(-1);
        }

        int i = 0;
        byte[] arrayOfByte = new byte[4];
        try {
            while ((i = localFileInputStream.read(arrayOfByte)) != 0) { Object localObject;
                if (i == 4) {
                    BigInteger localBigInteger1 = new BigInteger(1, arrayOfByte);
                    localObject = encrypt(localBigInteger1);

                    Write_BigInteger_to_file(localFileOutputStream, (BigInteger)localObject);
                }
                else if (i != -1)
                {
                    int j = 0;
                    localObject = new byte[4];

                    int k = 4 - i;
                    for (j = 0; j < k; j++) {
                        localObject[j] = 0;
                    }

                    for (int m = 0; m < i; j++)
                    {
                        localObject[j] = arrayOfByte[m];m++;
                    }

                    BigInteger localBigInteger2 = new BigInteger(1, (byte[])localObject);
                    BigInteger localBigInteger3 = encrypt(localBigInteger2);

                    Write_BigInteger_to_file(localFileOutputStream, localBigInteger3);
                }
            }
        }
        catch (IOException localIOException2)
        {
            System.err.println(localIOException2);
            System.exit(-1);
        }

        try
        {
            localFileOutputStream.close();
            localFileInputStream.close();
        }
        catch (IOException localIOException3) {
            System.err.println(localIOException3);
            System.exit(-1);
        }
    }

    public void DecryptFile(String paramString1, String paramString2)
    {
        FileOutputStream localFileOutputStream = null;
        FileInputStream localFileInputStream = null;
        try {
            localFileOutputStream = new FileOutputStream(new File(paramString2));
            localFileInputStream = new FileInputStream(new File(paramString1));
        }
        catch (IOException localIOException1) {
            System.err.println(localIOException1);
            System.exit(-1);
        }

        int i = modulus.bitLength() / 8 + 1;
        byte[] arrayOfByte1 = new byte[i];
        try {
            int j = -1;
            while ((j = localFileInputStream.read(arrayOfByte1)) == i) {
                BigInteger localBigInteger1 = new BigInteger(arrayOfByte1);
                BigInteger localBigInteger2 = decrypt(localBigInteger1);

                byte[] arrayOfByte2 = localBigInteger2.toByteArray();

                byte[] arrayOfByte3;
                int k;
                int m;
                if (arrayOfByte2.length > 4) {
                    arrayOfByte3 = new byte[4];
                    for (k = 0; k < 4; k++) { arrayOfByte3[k] = 0;
                    }
                    k = 3; for (m = arrayOfByte2.length - 1; (k >= 0) && (m >= 0); k--) {
                        arrayOfByte3[k] = arrayOfByte2[m];m--;
                    }
                    localFileOutputStream.write(arrayOfByte3);
                }
                else if (arrayOfByte2.length == 4) {
                    localFileOutputStream.write(localBigInteger2.toByteArray());
                }
                else if (localFileInputStream.available() > 0) {
                    arrayOfByte3 = new byte[4];
                    for (k = 0; k < 4; k++) { arrayOfByte3[k] = 0;
                    }
                    k = 3; for (m = arrayOfByte2.length - 1; (k >= 0) && (m >= 0); m--) {
                        arrayOfByte3[k] = arrayOfByte2[m];k--;
                    }
                    localFileOutputStream.write(arrayOfByte3);
                }
                else {
                    localFileOutputStream.write(arrayOfByte2);
                }
            }
            return;
        }
        catch (IOException localIOException3) {
            System.err.println(localIOException3);
            System.exit(-1);
        }
        finally {
            try {
                if (localFileInputStream != null) localFileInputStream.close();
                if (localFileOutputStream != null) localFileOutputStream.close();
            }
            catch (IOException localIOException5) {}
        }
    }
}