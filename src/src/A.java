import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import java.util.Scanner;

public class A {
	private static void displayNumber(int NUM) {
		for(int i=Integer.SIZE-1; i >= 0; i-- ) {
			int b = (NUM >> i) & 0x01;
			System.out.print(b + " ");
		}
		System.out.println();
	}
	private static void hideIntInImage(int toHide, String imageFileName, String outFile){
		BufferedImage inI  = null;
		BufferedImage outI = null;
		try{
			inI = ImageIO.read(new File(imageFileName));	// Load input image
			int W = inI.getWidth();				// get the width of the image
			int H = inI.getHeight();			// get the height of the image

			if(W * H < Integer.SIZE){
				System.err.println("Image not large enough to store number");
				System.exit(-1);
			}

			outI = new BufferedImage(W, H, inI.getType());
			int bitsLeft = Integer.SIZE;

			for(int y=0; y<H; y++){
				for(int x=0; x<W; x++) {

					int rgb = inI.getRGB(x,y);	// (1) Getting RGB of a pixel (done)

					if (bitsLeft > 0){
						//change the LSB
						rgb = (rgb & 0xfffffffe) | (toHide >> (--bitsLeft)) & 0x01;
						outI.setRGB(x,y,rgb); // set the pixel of the output image to edited rgb
					}else{
						outI.setRGB(x,y,rgb); // set the pixel of the output image to new_rgb
					}
				}
			}

			ImageIO.write(outI, "png", new File(outFile));

		}catch(IOException ee){
			System.err.println(ee);
			System.exit(-1);
		}
	}

	private static void findHiddenInt(String imageFileName){
		BufferedImage inI  = null;
		int hiddenNum = 0x00000000;
		try{
			inI = ImageIO.read(new File(imageFileName));	// Load input image
			int W = inI.getWidth();				// get the width of the image
			int H = inI.getHeight();			// get the height of the image

			int bitsLeft = Integer.SIZE;

			for(int y=0; y<H; y++){
				for(int x=0; x<W; x++) {
					if (bitsLeft == 0){
						break;
					}

					int rgb = inI.getRGB(x,y);	// (1) Getting RGB of a pixel (done)
					hiddenNum |= ((rgb%2) & 0x01) << (--bitsLeft); // update the current bit
				}
				if (bitsLeft == 0){
					break;
				}
			}

		}catch(IOException ee){
			System.err.println(ee);
			System.exit(-1);
		}

		System.out.println(hiddenNum);
	}

	private static void displayNumberAsByte(int NUM) {
		for(int i=Byte.SIZE-1; i >= 0; i-- ) {
			int b = (NUM >> i) & 0x01;
			System.out.print(b + " ");
		}
		System.out.println();
	}

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		System.out.print("Please give me a number: ");

		int N = in.nextInt();
		A.displayNumber(N);

		A.hideIntInImage(N, "sample.png","hidden.png");
		System.out.print("The hidden number is: ");
		A.findHiddenInt("hidden.png");
	}
}
