import java.util.Random; 
import java.util.Scanner; 


/*
1 Полностью разобраться с кодом, попробовать переписать с нуля, стараясь не подглядывать в методичку;
2 Переделать проверку победы, чтобы она не была реализована просто набором условий, например, с использованием циклов.
3 * Попробовать переписать логику проверки победы, чтобы она работала для поля 5х5 и количества фишек 4. 
    Очень желательно не делать это просто набором условий для каждой из возможных ситуаций;

*/

public class HomeworkJava1_4 { 
	public static final char DOT_EMPTY = '.'; 
	public static final char DOT_X = 'X'; 
	public static final char DOT_O = 'O'; 
	public static char[][] map; 
	public static int size;  // Размер поля
	public static int block; // Сколько должно быть одинаковых значение подряд для победы
	public static Scanner sc = new Scanner(System.in); 
	public static Random rand = new Random(); 
	
	public static void main(String[] args) { 
		new HomeworkJava1_4().go();
	} 

	public void go() {
		customizeGame();
		initMap(); 
		printMap(); 
		while (true) { 
			humanTurn(); 
			printMap(); 
			if (checkWin(DOT_X)) { 
				System.out.println("Human won!"); 
				break; 
			} 
			if (isMapFull()) { 
				System.out.println("Draw!"); 
				break; 
			} 
			aiTurn(); 
			printMap(); 
			if (checkWin(DOT_O)) { 
				System.out.println("AI won!"); 
				break; 
			} 
			if (isMapFull()) { 
				System.out.println("Draw"); 
				break; 
			} 
		} 
		System.out.println("Gave over"); 
	}	

	/** Запрашиваем размер поля и условия победы */
	void customizeGame() {
		do {
			System.out.print("\nInput map size [3-10]: "); 
			size = sc.nextInt(); 
		} while(size<3 || size>10);
		
		do {
			System.out.print("How many times to repeat blocks to win? [3-"+size+"]: "); 
			block = sc.nextInt(); 
		} while (block<3 || block>size);

	}
	
	/** Логика победы изменина для работы с любым размером поля. */
	boolean checkWin(char symb) { 
		for (int col=0; col<size-block+1; col++) {
			for (int row=0; row<size-block+1; row++) {
				if (checkDiagonal(symb, col, row) || checkLanes(symb, col, row)) return true;
			}	
		}
		return false;
	}

	/** Проверяем диагонали */
	boolean checkDiagonal(char symb, int offsetX, int offsetY) { 
		boolean torign, toleft;
		torign = true;
		toleft = true;
		for (int i=0; i<block; i++) {
			torign &= (map[i+offsetX][i+offsetY] == symb);
			toleft &= (map[block-i-1+offsetX][i+offsetY] == symb);
		}
		
		if (torign || toleft) return true;
		
		return false; 
	} 

	/** Проверяем горизонтальные и вертикальные линии */
	boolean checkLanes(char symb, int offsetX, int offsetY) { 
		boolean cols, rows;
		for (int col=offsetX; col<block+offsetX; col++) {
			cols = true;
			rows = true;
			for (int row=offsetY; row<block+offsetY; row++) {
				cols &= (map[col][row] == symb);
				rows &= (map[row][col] == symb);
			}
			
			if (cols || rows) return true;
		}
		
		return false; 
	} 
	
	boolean isMapFull() { 
		for (int i = 0; i < size; i++) { 
			for (int j = 0; j < size; j++) { 
				if (map[i][j] == DOT_EMPTY) return false; 
			} 
		} 
		return true; 
	}
	
	void aiTurn() { 
		int x, y; 
		do { 
			x = rand.nextInt(size); 
			y = rand.nextInt(size); 
		} while (!isCellValid(x, y)); 
		
		System.out.println("AI made turn" + (x + 1) + " " + (y + 1)); 
		
		map[y][x] = DOT_O; 
	} 
	
	void humanTurn() { 
		int x, y; 
		do { 
			System.out.println("Input coordinate using X Y"); 
			x = sc.nextInt() - 1; 
			y = sc.nextInt() - 1; 
		} while (!isCellValid(x, y)); 
		
		map[y][x] = DOT_X; 
	} 
	
	boolean isCellValid(int x, int y) { 
		if (x < 0 || x >= size || y < 0 || y >= size) return false; 
		if (map[y][x] == DOT_EMPTY) return true; 
		return false; 
	} 
	
	void initMap() { 
		map = new char[size][size]; 
		for (int i = 0; i < size; i++) { 
			for (int j = 0; j < size; j++) { 
				map[i][j] = DOT_EMPTY; 
			} 
		} 
	} 
	
	void printMap() { 
		for (int i = 0; i <= size; i++) { 
			System.out.print(i + " "); 
		} 
		System.out.println(); 
		for (int i = 0; i < size; i++) { 
			System.out.print((i + 1) + " "); 
			for (int j = 0; j < size; j++) { 
				System.out.print(map[i][j] + " "); 
			} 
			System.out.println(); 
		} 
		System.out.println(); 
	} 
}
