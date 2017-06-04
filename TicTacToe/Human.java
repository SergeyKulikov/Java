/*
 * Java 1. Lesson 8. Game Tic Tac Toe
 * Class: Human
 *
 * @author Sergey Iryupin
 * @version 0.1 dated March 11, 2017
 *
 * Modified by Sergey Kulikov on 03.06.2017
 */
class Human {
    private final char DOT;
	// private ImageIcon icon;

    Human(char ch) { DOT = ch; }

    void turn(int x, int y, Field field, AI ai) {
        if (field.isCellEmpty(x, y)) {
            if (!field.isGameOver()) field.setDot(x, y, DOT);
			
			// System.out.println("DOT is: "+DOT);
            // if (!field.isGameOver()) ai.turn(field);
        }
    }
}