import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
public class RC4 {
    public void ksa(byte state[], byte key[], int len){

        for(int i=0;i<state.length;i++){
            state[i] = (byte)i;
        }

        int j = 0;
        for(int i=0;i<state.length;i++){
            j = (j + state[i] + key[i%len]);
            while(j<0) j+=256; //fix for negative numbers
            j = j % 256;
            byte t = state[i];
            state[i] = state[j];
            state[j] = t;
        }
    }

    public void prga(byte state[], String input, String output)  {
        byte [] buffer = new byte[1];
        int i = 0,j = 0;
        try {
            FileInputStream fis = new FileInputStream(input);
            FileOutputStream fos = new FileOutputStream(output);
            while( fis.read(buffer) == 1 ) {
                byte[] result = new byte[1];
                // run the prga on the byte buffer[0]
                // store the result in byte result[0]
                i = (i+1) % 256;
                j = (j + state[i]) % 256;
                while(j<0) j+=256; //fix for negative numbers
                byte t = state[i];
                state[i] = state[j];
                state[j] = t;
                int index = (state[i] + state[j]) % 256;
                while (index<0) index+=256; //fix for negative numbers
                byte K = state[index];
                result[0]=(byte)(buffer[0] ^ K);

                fos.write(result);
            }
            fis.close();
            fos.close();

        }
        catch(IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }


    public static void main(String args[]) {
        byte key[]="MyPassword".getBytes();
        int PASS_size = key.length;

        byte state[] = new byte[256];

        RC4 rc4 = new RC4();

        // ENCRYPT
        rc4.ksa(state, key, PASS_size);
        rc4.prga(state, "a.png", "b.png");

        // DECRYPT
        rc4.ksa(state, key, PASS_size);
        rc4.prga(state, "b.png", "c.png");

        // Files a.png and c.png must be identical.
    }
}