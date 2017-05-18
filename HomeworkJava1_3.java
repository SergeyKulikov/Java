/*
	Created by Sergey Kulikov on 13-05-2017
	HOMEWORK JAVA 1 LESSON 3
*/

import java.util.*;

/** 
	Создать массив из слов String[] words = {"apple", "orange"....
	При запуске программы компьютер загадывает слово, запрашивает ответ у пользователя,
	сравнивает его с загаданным словом и сообщает правильно ли ответил пользователь. Если слово не угадано, 
	компьютер показывает буквы которые стоят на своих местах.
	apple – загаданное
	apricot - ответ игрока
	ap############# (15 символов, чтобы пользователь не мог узнать длину слова)
	Для сравнения двух слов посимвольно, можно пользоваться:
	String str = "apple";
	str.charAt(0); - метод, вернет char, который стоит в слове str на первой позиции.
*/

public class HomeworkJava1_3 {
	
	public static final int maxGrates = 15;
	public static int tryCount;
	public static int prevVar;
    public static final String[] jokes = {"Ok, try again.", 
	    "Are you kidding me?", "That's easy!", 
		"Wow! So many failed tries!", "Mmm...", "I'm a clever, ain't I?", 
		"Do you remember alphabet?", "O, God almighty!", "Come on!", 
		"Steer! There are 27 letters only..."};
	
	public static void main(String[] args) {
		String[] words = {"apple", "orange", "lemon", "banana", "apricot", 
		    "avocado", "broccoli", "carrot", "cherry", "garlic", "grape", 
			"melon", "leak", "kiwi", "mango",
			"mushroom", "nut", "olive", "pea", "peanut", "pear",
			"pepper", "pineapple", "pumpkin", "potato"};
		
		Scanner sc = new Scanner(System.in);
		int randIdx = (int)(Math.random() * words.length);
		String gotWord = "";
		boolean hasDone = false;
		tryCount = 0;
			
		System.out.println("\n\nI made up the word? What word is this?"+
			"\nIt can be less than "+maxGrates+" letters.\n");
		while(!checkGuess(words[randIdx], gotWord)) {
			gotWord = sc.next();
		}
	}	

	static boolean checkGuess(String sourceWord, String guessWord) {
		boolean openLetter[] = new boolean[maxGrates];
		for (int i=0; i<maxGrates; i++) openLetter[i] = false;
		
		if (guessWord.compareTo(sourceWord) == 0) { 
			System.out.println("You guessed the word. Congratulation, you won!");
			return true;
		} 
		else {
			makeJoke();
			// Тут другая логика проверки, сделал как в "Поле чудес", 
			// открываются узанные буквы везде, где они есть.
			for (int i=0; i<sourceWord.length(); i++) { 
				for (int j=0; j<guessWord.length(); j++) {
					if (!openLetter[i])	openLetter[i] = 
						(guessWord.charAt(j) == sourceWord.charAt(i)); 
				}
			}
		}
		printWord(sourceWord, openLetter);
		return false;
	}
	
	static void makeJoke() {
		int curVar = -1;
		if (tryCount++ > 2) { 
			do { 
				curVar = (int)(Math.random() * jokes.length);
			} while (curVar == prevVar); // don't say it twice in a row
			System.out.println(""+jokes[curVar]);
			prevVar = curVar;
		}
	}

	static void printWord(String sourceWord, boolean []openLetter) {
		for (int i=0; i<maxGrates; i++) { 
			System.out.print( ((openLetter[i]) ? sourceWord.charAt(i) : "#") );
		}
		System.out.print(": ");
	}
}	