public class ReverseHash {
    public static byte[] hash(byte[] message){
        byte[] digest = new byte[message.length];
        for(int i=0;i<message.length;i++){
            if (i == 0){
                digest[i] = (byte)((129 * message[i]) % 256);
            }else {
                digest[i] =  (byte)(((129 * message[i]) ^ message[i - 1]) % 256);
            }
        }

        return digest;
    }

    public static byte[] unhash(byte[] d){
        byte[] m = new byte[d.length];
        byte[] m2 = new byte[d.length];
        for(int i=0;i<d.length;i++){
            for (int j=0;j<256;j++) {
                if (i == 0) {
                    m[i] = (byte)((129 * j) % 256);
                } else {
                    m[i] = (byte)(((129 * j) ^ m2[i - 1]) % 256);
                }
                if(m[i] == d[i]){
                    m2[i] = (byte)j;
                    System.out.print((char)j);
                    System.out.println();
                    break;
                }
            }
        }

        return m2;
    }

    public static void printb(byte[] b){
        for(int i=0;i<b.length;i++){
            System.out.print(b[i]);
            System.out.print(" ");
        }
        System.out.println();
    }

    public static void main(String[] args) {

        byte[] message = new byte[9];
        int[] a = {71, 111, 111, 100, 32, 74, 111, 98, 33};
        for (int i=0;i<a.length;i++){
            message[i] = (byte)a[i];
        }

        System.out.print("Message: ");
        printb(message);

        byte[] h = hash(message);
        byte[] u = unhash(h);

        System.out.print("Unhashed: ");
        printb(u);
    }
}
