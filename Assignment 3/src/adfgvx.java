import java.io.File;
import java.util.*;

//Arup Guha
//9/14/2016
//ADFGVX encrypter
/*** File format:

1. 6 x 6 square, space separated on six lines
2. 7th line will be keyword all caps
3. 8th line will be n, # of messages to encrypt.
4. Next n lines will have each message, one by one. (A-Z, 0-9 only)

***/

import java.util.*;

public class adfgvx {

	final private static String cipherType = "ADFGVX";
	private String[] letterLookUp;
	private String[] digitLookUp;
	private int[] perm;
	
	public static void main(String[] args) {
		
		
		Scanner input = new Scanner(System.in);
		String fromUser = new String();
		char[][] key = new char[6][6];
		for (int i=0; i<6; i++)
			for (int j=0; j<6; j++){
				System.out.println("Enter a Key Value");
				fromUser = input.next().toUpperCase();
				char ch = fromUser.charAt(0);
				key[i][j] = ch; //input.next().charAt(0);
			}
		for (int i=0; i<6; i++)
			for (int j=0; j<6; j++){
				System.out.print(key[i][j]);
				if(j == 5)
					System.out.print("\n");
			}
	
	
		String keyword = new String();
		keyword = input.next();
		char[] mess = keyword.toCharArray();
	
		char[] keywordChar = mess;//input.next().toCharArray();


		adfgvx myCode = new adfgvx(key, keywordChar);
		int numTimes = input.nextInt(); //number of times we are encyrpting or decrypting
		
		int k = 0;
		String plainText;
		String cipherText;
		
		while(k != numTimes) {
		
		int instruction = input.nextInt();
		if(instruction == 1) {
			plainText = input.next();
			String encoded = myCode.encrypt(plainText);
			System.out.println(encoded);
		}
		if(instruction == 2) {
			cipherText = input.next();
			
		}
		k++;
		}
		
		System.out.println("done");
		input.close();
	}

	// Creates an adfgvx object
	public adfgvx(char[][] myKey1, char[] myKey2) {

		letterLookUp = new String[26];
		digitLookUp = new String[10];

		// Goes through each item in the 6 x 6 square
		for (int i=0; i<6; i++) {
			for (int j=0; j<6; j++) {

				if (Character.isLetter(myKey1[i][j]))
					letterLookUp[myKey1[i][j]-'A'] = "" + cipherType.charAt(i) + cipherType.charAt(j);
				else
					digitLookUp[myKey1[i][j]-'0'] = "" + cipherType.charAt(i) + cipherType.charAt(j);
			}
		}

		// Set up permutation for reading columns
		perm = new int[myKey2.length];
		tile[] myTiles = new tile[myKey2.length];
		for (int i=0; i<myKey2.length; i++)
			myTiles[i] = new tile(myKey2[i], i);
		Arrays.sort(myTiles);

		// Copy in rankings for tiles.
		for (int i=0; i<myKey2.length; i++) {
			perm[i] = myTiles[i].index;
			System.out.print(perm[i]+" ");
		}
		System.out.println();
	}
		
	public String encrypt(String message) {
		String middle = sub(message);
		System.out.println(middle);
		return trans(middle);
	}
	
	public String decrypt(String message) {
		String middle = sub(message);
		System.out.println(middle);
		return trans(middle);
	}

	// Performs a basic column transposition on message using the object's second key.
	public String trans(String message) {

		// These dimensional items are important.
		int shortCol = message.length()/perm.length;
		int numLongCol = message.length()%perm.length;

		// Use this for a better run time...
		StringBuilder sb = new StringBuilder();

		// Go through each column.
		for (int i=0; i<perm.length; i++) {

			// Nice way to avoid calculating whether it's a short or long column.
			for (int j=perm[i]; j<message.length(); j+= perm.length)
				sb.append(message.charAt(j));
		}

		return new String(sb);
	}

	public String sub(String message) {

		String res = "";

		// Add each character code, one by one.
		for (int i=0; i<message.length(); i++) {
			char c = message.charAt(i);
			if (Character.isLetter(c))
				res = res + letterLookUp[c-'A'];
			else
				res = res + digitLookUp[c-'0'];
		}

		return res;
	}
}

// Just so we can let Java resort the second key word.
class tile implements Comparable<tile> {

	public char letter;
	public int index;

	public tile(char c, int i) {
		letter = c;
		index = i;
	}

	public int compareTo(tile other) {
		if (this.letter != other.letter)
			return this.letter - other.letter;
		return this.index - other.index;
	}
		
	
	 
	
}