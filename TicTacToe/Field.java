/*
 * Java 1. Lesson 8. Game Tic Tac Toe
 * Class: Field
 *
 * @author Sergey Iryupin
 * @version 0.3 dated May 30, 2017
 *
 * Modified by Sergey Kulikov on 03.06.2017
*/
import java.awt.*;
import java.awt.geom.*; // for Graphics2D
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.Icon;
import javax.swing.ImageIcon;


class Field {
    int FIELD_SIZE;
    int BLOCK_SIZE;
    int CELL_SIZE;
    private final char HUMAN_DOT = 'x';
    private final char HUMAN2_DOT = '#';
    private final char AI_DOT = 'o';
    private final char EMPTY_DOT = '.';
    private final String MSG_DRAW = "Draw, sorry...";
    private final String MSG_HUMAN_WON = "PLAYER 1 WHO PLAYED RED 'X' WON!";
    private final String MSG_HUMAN2_WON = "PLAYER 2 WHO PLAYED GOLDEN STAR '*' WON!";
    private final String MSG_AI_WON = "AI WON!";
    private char[][] field;
    private String gameOverMsg;
	
	ImageIcon []imageIcon = { new ImageIcon("img\\1.png"), new ImageIcon("img\\2.png"), new ImageIcon("img\\3.png") };
	ImageIcon []imageIconCell = { new ImageIcon("img\\1.png"), new ImageIcon("img\\2.png"), new ImageIcon("img\\3.png") };
	
    Field(int field_size, int cell_size, int block_size) {
        FIELD_SIZE = field_size;
        BLOCK_SIZE = block_size;
        CELL_SIZE = cell_size;
		Image image, newimg;
        field = new char[FIELD_SIZE][FIELD_SIZE];
		
		for (int i=0; i<3; i++) { // change icon size
			image = imageIcon[i].getImage();
			newimg = image.getScaledInstance(CELL_SIZE, CELL_SIZE,  java.awt.Image.SCALE_SMOOTH);
			imageIconCell[i] = new ImageIcon(newimg);
		}
		
        init();
    }

    void init() {
        for (int i = 0; i < FIELD_SIZE; i++)
            for (int j = 0; j < FIELD_SIZE; j++)
                field[i][j] = EMPTY_DOT;
        gameOverMsg = null;
    }

    int getSize() { return FIELD_SIZE; }

    char getHumanDot() { return HUMAN_DOT; }
	
    char getHuman2Dot() { return HUMAN2_DOT; }

    char getAIDot() { return AI_DOT; }

    boolean isGameOver() { return gameOverMsg != null; }

    String getGameOverMsg() { return gameOverMsg; }

    void setDot(int x, int y, char dot) { // set dot and check fill and win
        field[x][y] = dot;
        if (isFull())
            gameOverMsg = MSG_DRAW;
        if (isWin(HUMAN_DOT))
            gameOverMsg = MSG_HUMAN_WON;
        if (isWin(HUMAN2_DOT))
            gameOverMsg = MSG_HUMAN2_WON;
        if (isWin(AI_DOT))
            gameOverMsg = MSG_AI_WON;
    }

    boolean isCellEmpty(int x, int y) {
        if (x < 0 || y < 0 || x > FIELD_SIZE - 1 || y > FIELD_SIZE - 1) return false;
        if (field[x][y] == EMPTY_DOT) return true;
        return false;
    }

    boolean isFull() {
        for (int i = 0; i < FIELD_SIZE; i++)
            for (int j = 0; j < FIELD_SIZE; j++)
                if (field[i][j] == EMPTY_DOT) return false;
        return true;
    }

	/*
    boolean isWin(char ch) {
        // checking horizontals / verticals
        for (int i = 0; i < FIELD_SIZE; i++)
            if ((field[i][0] == ch && field[i][1] == ch && field[i][2] == ch) ||
                (field[0][i] == ch && field[1][i] == ch && field[2][i] == ch))
                return true;
        // checking diagonals
        if ((field[0][0] == ch && field[1][1] == ch && field[2][2] == ch) ||
            (field[2][0] == ch && field[1][1] == ch && field[0][2] == ch))
            return true;
        return false;
    }
	*/
	
	/** Логика победы изменина для работы с любым размером поля. */
	boolean isWin(char symb) { 
		for (int col=0; col<FIELD_SIZE-BLOCK_SIZE+1; col++) {
			for (int row=0; row<FIELD_SIZE-BLOCK_SIZE+1; row++) {
				if (checkDiagonal(symb, col, row) || checkLanes(symb, col, row)) return true;
			}	
		}
		return false;
	}

	/** Проверяем диагонали */
	boolean checkDiagonal(char symb, int offsetX, int offsetY) { 
		boolean toright, toleft;
		toright = true;
		toleft = true;
		for (int i=0; i<BLOCK_SIZE; i++) {
			toright &= (field[i+offsetX][i+offsetY] == symb);
			toleft &= (field[BLOCK_SIZE-i-1+offsetX][i+offsetY] == symb);
		}
		
		return (toright || toleft);
	} 

	/** Проверяем горизонтальные и вертикальные линии */
	boolean checkLanes(char symb, int offsetX, int offsetY) { 
		boolean cols, rows;
		for (int col=offsetX; col<BLOCK_SIZE+offsetX; col++) {
			cols = true;
			rows = true;
			for (int row=offsetY; row<BLOCK_SIZE+offsetY; row++) {
				cols &= (field[col][row] == symb);
				rows &= (field[row][col] == symb);
			}
			
			if (cols || rows) return true;
		}
		
		return false; 
	} 
	
    public void paint(Graphics g) {
        g.setColor(Color.lightGray);
        for (int i = 1; i < FIELD_SIZE; i++) {
            g.drawLine(0, i*CELL_SIZE, FIELD_SIZE*CELL_SIZE, i*CELL_SIZE);
            g.drawLine(i*CELL_SIZE, 0, i*CELL_SIZE, FIELD_SIZE*CELL_SIZE);
        }
		
        Graphics2D g2 = (Graphics2D) g; // use Graphics2D
        // g2.setStroke(new BasicStroke(5));
		
        for (int y = 0; y < FIELD_SIZE; y++) {
            for (int x = 0; x < FIELD_SIZE; x++) {
                if (field[x][y] == HUMAN_DOT) {
					imageIconCell[0].paintIcon(null, g2, x*CELL_SIZE, y*CELL_SIZE);
					new Audio("sound\\iron2.wav", 0.2).play();
                }
                if (field[x][y] == AI_DOT) {
					imageIconCell[1].paintIcon(null, g2, x*CELL_SIZE, y*CELL_SIZE);
                }
                if (field[x][y] == HUMAN2_DOT) {
					imageIconCell[2].paintIcon(null, g2, x*CELL_SIZE, y*CELL_SIZE);
					new Audio("sound\\iron2.wav", 0.2).play();
                }
				
				
            }
        }
    }
}