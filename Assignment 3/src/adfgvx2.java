

/*
 * David Almeida 
 * Micheal Nall
 * Assignment3
 * ADFGVX cipher
 * 9/19/18
 * 
 * If program doesn't run properly, try uncommenting the print statements... this however requires manual input
 * Currently encode works but decoding does not.
 */



import java.util.*;

public class adfgvx2 {

	final private String LETTERS = "ADFGVX";
	private static String[] letterLookUp;
	private static String[] digitLookUp;
	private static char[][] keyArray = new char[6][6];
	 //create global int array
	static int[] permutation;
	
	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		String keyLetters = new String();
		
		
		
		//get the key table for encrypting
		for (int i=0; i<6; i++)
			for (int j=0; j<6; j++){
				//System.out.println("Enter a Key Value");
				keyLetters = input.next().toUpperCase();
				char ch = keyLetters.charAt(0);
				keyArray[i][j] = ch;
			}
		
		for (int i=0; i<6; i++)
			for (int j=0; j<6; j++){
				System.out.print(keyArray[i][j]);
				if(j == 5)
					System.out.print("\n");
				}
		
		//get the keyword
		//System.out.println("enter the keyword");
		String keyword = new String();
		keyword = input.next().toUpperCase();
		char[] codeWord = keyword.toCharArray();
		
		int keywordLength = keyword.length();
		
		//
		//System.out.println("Please enter the number of messages to encrypt or decrypt");
		int numTimes = input.nextInt();
		
		int k = 0;
		
		String plainText;
		String cipherText;
		//int plainLength = plainText.length();
		//int cipherLength = cipherText.length();
		
		adfgvx2 myCipher = new adfgvx2(keyArray, codeWord);
		
		while(k != numTimes) {
			
			//System.out.println("Enter 1 for encode, 2 for decode");
			int instruction = input.nextInt();
			if(instruction == 1) {
				//System.out.println("Print message to encode");
				plainText = input.next().toUpperCase();
				String encoded = myCipher.encode(plainText);
				System.out.println(encoded);
				}
			
			if(instruction == 2) {
				//System.out.println("Message to decrypt");
				cipherText = input.next().toUpperCase();
				String decoded = myCipher.decode(cipherText);
				System.out.println(decoded);
				
				//char[] cipherChar = new char[cipherText.length()];
				
				
				}
			
			k++;
		}
	
	
	
		System.out.println("end");
		input.close();
	
	
	
	
	}
	
	public adfgvx2(char[][] keyTable, char[] keyWord) {
		letterLookUp = new String[26];
		digitLookUp = new String[10];
		
		
		//goes through the table
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j<6; j++) {
				if(Character.isLetter(keyTable[i][j]))
					letterLookUp[keyTable[i][j]-'A'] = "" + LETTERS.charAt(i) + LETTERS.charAt(j);
				else
					digitLookUp[keyTable[i][j]-'0'] = "" + LETTERS.charAt(i) + LETTERS.charAt(j);

			}
		}
		
		permutation = new int[keyWord.length];
		tile[] myTiles = new tile[keyWord.length];
		for (int i=0; i<keyWord.length; i++)
			myTiles[i] = new tile(keyWord[i], i);
		Arrays.sort(myTiles);
		
		for (int i=0; i<keyWord.length; i++) {
			permutation[i] = myTiles[i].index;
			System.out.print(permutation[i]+" ");
		}
		System.out.println();
		
	}
	
	
	public static String encode(String plain) {
		String middle = substitution(plain);
		System.out.println(middle);
		return transposition(middle);
	}
	
	public static String decode(String cipher) {
		String middle = revTransposition(cipher);
		System.out.println(middle);
		return revSubstitution(middle);
	}

	private static String transposition(String middle) {
		
		// These dimensional items are important.
				int shortCol = middle.length()/permutation.length;
				int numLongCol = middle.length()%permutation.length;

				// Use this for a better run time...
				StringBuilder sb = new StringBuilder();

				// Go through each column.
				for (int i=0; i<permutation.length; i++) {

					// Nice way to avoid calculating whether it's a short or long column.
					for (int j=permutation[i]; j<middle.length(); j+= permutation.length)
						sb.append(middle.charAt(j));
				}

				return new String(sb);
	}

	private static String substitution(String plain) {
		String res = "";
		
		// Add each character code, one by one.
				for (int i=0; i<plain.length(); i++) {
					char c = plain.charAt(i);
					if (Character.isLetter(c))
						res = res + letterLookUp[c-'A'];
					else
						res = res + digitLookUp[c-'0'];
				}
	

		return res;
	}
private static String revTransposition(String middle) {
		
		// These dimensional items are important.
				int shortCol = middle.length()/permutation.length;
				int numLongCol = middle.length()%permutation.length;

				// Use this for a better run time...
				StringBuilder sb = new StringBuilder();

				// Go through each column.
				for (int i=0; i<permutation.length; i++) {

					// Nice way to avoid calculating whether it's a short or long column.
					for (int j=permutation[i]; j<middle.length(); j+= permutation.length)
						sb.append(middle.charAt(j));
				}

				return new String(sb);
	}

	private static String revSubstitution(String cipher) {
		String res = "";

		// Add each character code, one by one.
				for (int i=0; i<cipher.length(); i++) {
					char one = cipher.charAt(i);
					char two = cipher.charAt(i+1);
					
					for(int k = 0; k < 6; k++) {
						for(int j = 0; j<6; j++) {
							res = res + letterLookUp[keyArray[one][two]+'A'];
					
						}
					}
					
					i= i + 1;
				}

		return res;
	}
}	
	
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

