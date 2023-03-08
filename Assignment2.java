import sheffield.*;

public class Assignment2 {
	public static void main (String [] args){
		
		EasyReader fileinput = new EasyReader("duck3.txt");
				
		//MULTIPLIER describes how many times the duck image's pixels must be repeated. i.e. how much larger the image is - scale factor - this was especially useful for bug fixing
		final int MULTIPLIER = 2;
		//BASE is the base number of pixels for the duck image
		final int BASE = 130;
		//PIXELS is the total number of pixels needed to display each image of a duck
		final int PIXELS = BASE * MULTIPLIER;
		
		//All the different body parts are assigned codes to be used in the array and for drawing them
		final int BODY = 1;
		final int FOOT = 2;
		final int BEAK = 3;
		final int EYE = 4;
		final int BACKGROUND = 0;
		
		//The part of the image of the duck being drawn is saved using the variable 'imagePart'.
		int imagePart = 0;
		
		//A graphics window of 520*520 pixels is drawn
		EasyGraphics g = new EasyGraphics(PIXELS * 2, PIXELS * 2);

		//Sky rectangle
		g.setColor( 0, 200, 255);
		g.fillRectangle(0, (PIXELS * 3/2), (PIXELS * 2), (PIXELS / 2));
		//Grass rectangle
		g.setColor(0, 150, 0);
		g.fillRectangle(0, (PIXELS / 2), (PIXELS * 2), (PIXELS));
		//River rectangle
		g.setColor(0, 150, 150);
		g.fillRectangle(0, 0, (PIXELS * 2), (PIXELS / 2));

		for (int i = 0; i < PIXELS; i += MULTIPLIER){
			int[] row = new int[PIXELS]; //An array is created for every iteration of the main for loop which contains all of the information for drawing the duck for each row
			for (int j = 0; j < PIXELS; j += MULTIPLIER) {
			    int ascii = fileinput.readChar();//Each character's ascii code value is assigned to a new variable
			    if (ascii % 2 != 0) //If the ascii code for the letter is odd then it represent a part of the duck's body (excluding the eye)
			        if ((ascii % 5 == 0) && (ascii % 7 != 0)) //If the ascii code is divisible by only 5, the letter represents the foot
			            imagePart = FOOT;
			        else if ((ascii % 7 == 0) && (ascii % 5 != 0)) //If the ascii code is divisible by only 7, the letter represent the beak
			            imagePart = BEAK;
			        else //If the ascii code is not exclusively divisible by 5 or 7, it represents the body
			            imagePart = BODY;
			    else if (ascii % 22 == 0) //If the ascii code is able to be divided by 22, is must represent the eye (as only one letter's code is divisble by 22)
			        imagePart = EYE;
			    else //If none of the above conditions apply to the letter, then it must be a part of the background
			        imagePart = BACKGROUND;
			    for (int count = 0; count < MULTIPLIER; count++) //This for loop will repeat each body part code in the array equivalent to the constant 'MULTIPLIER', in order to turn the standard 130x130 size duck into whatever size is required.
			        row[(j + count)] = imagePart; //The array has the imagePart variable added to it
			}
			for (int x = 0; x < PIXELS; x++) {
				imagePart = row[x]; //This for loop goes through each element in the array and assigns it to the variable 'imagePart' to determine which part of the duck is to be drawn
				if(imagePart == BODY)
					g.setColor(200, 200, 0);
				else if (imagePart == FOOT)
					g.setColor(100, 100, 0);
				else if (imagePart == BEAK)
					g.setColor(250, 150, 0);
				else if (imagePart == EYE)
					g.setColor(0, 0, 0);
				if (imagePart != BACKGROUND) // As the background is clear, any imagePart value for the background is not drawn
					for (int count = 0; count < MULTIPLIER; count++) {
						g.plot((x), (PIXELS * 2 - i + count)); //Top left large duck
						g.plot((x / 2 + PIXELS * 5 / 4), (PIXELS * 7 / 4 - i / 2)); //Top right small duck
						g.plot((PIXELS * 5 / 4 - x / 2), (PIXELS * 5 / 4 - i / 2)); //Middle small duck
						if (imagePart != FOOT)
							g.plot((x + PIXELS), (PIXELS - i + count)); //Bottom right large duck
					}
			}
		}
	}
}
