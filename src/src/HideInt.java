import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.util.Scanner;

public class HideInt {


    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        System.out.print("Please give me a number: ");
        int N = in.nextInt();

        System.out.println("\nAn integer is " + Integer.SIZE + " bits long.\n");

        for(int i=Integer.SIZE-1; i >= 0; i-- ) {
            int b = (N >> i) & 0x01;
            System.out.print(b + " ");
        }
        System.out.println();
    }
}
