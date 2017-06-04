/*
 * Java 1. Lesson 8. Game Tic Tac Toe
 * Class: Main-Class
 *
 * @author Sergey Iryupin
 * @version 0.3 dated May 30, 2017
 *
 * Modified by Sergey Kulikov on 03.06.2017
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.*;
import java.io.*; 

class TicTacToe extends JFrame {

    final String TITLE_OF_PROGRAM = "Tic Tac Toe";
    final int START_POSITIONX = 300;
    final int START_POSITIONY = 200;
    final int WINDOW_SIZE = 300;
    final int WINDOW_DX = 20; // These numbers we need to get from frames layers, because they are different for others windows' themes.
    final int WINDOW_DY = 90;
    final String BTN_INIT = "New game";
    final String BTN_EXIT = "Exit";
	final String[] players = { "Human & AI", "2 Humans & AI", "2 Humans" };
	final String[] fieldsize = { "3x3", "4x4", "5x5", "6x6", "9x9", "12x12" };
	String[] blocksize = { "3", "4", "5" };
	boolean ingame[] = new boolean[3];
	int playerNumber;

    int FIELD_SIZE = 6;
    int BLOCK_SIZE = 4;
    int CELL_SIZE = WINDOW_SIZE / FIELD_SIZE;
    Canvas canvas = new Canvas();
    Field field = new Field(FIELD_SIZE, CELL_SIZE, BLOCK_SIZE);  
    Human human = new Human(field.getHumanDot());
    Human human2 = new Human(field.getHuman2Dot());
    AI ai = new AI(field.getAIDot());
	
	JComboBox cbFieldsize; // = new JComboBox<String> (fieldsize);
	JComboBox cbBlocksize; // = new JComboBox<String> (blocksize);
	JComboBox cbPlayers; // = new JComboBox<String> (players);

    public static void main(String args[]) {
        new TicTacToe();
    }
	
	void presetOptions (int fieldsize, int blocksize) {
		FIELD_SIZE = fieldsize;
		BLOCK_SIZE = blocksize;
		CELL_SIZE = WINDOW_SIZE / FIELD_SIZE;
		field = new Field(FIELD_SIZE, CELL_SIZE, BLOCK_SIZE); 
		canvas.repaint();
	}
	
    TicTacToe() {
        setTitle(TITLE_OF_PROGRAM);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(START_POSITIONX, START_POSITIONY, WINDOW_SIZE + WINDOW_DX, WINDOW_SIZE + WINDOW_DY);
		// JLayeredPane lp = getLayeredPane();
		// System.out.println("JLayeredPane.getWidth:" + canvas.getWidth());
		playerNumber = 1;
		
		// Добавление панели инструментов
		cbFieldsize = new JComboBox<String> (fieldsize);
		cbBlocksize = new JComboBox<String> (blocksize);
		cbPlayers = new JComboBox<String> (players);
		
		cbFieldsize.setSelectedIndex(3);
		cbBlocksize.setSelectedIndex(1);
		
        JPanel toolPanel = new JPanel();
		
        JToolBar toolBar = new JToolBar();
        toolBar.add(cbFieldsize);
        toolBar.add(cbBlocksize);
        toolBar.add(cbPlayers);
		
        canvas.setBackground(Color.white);
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
				
				cbFieldsize.setEnabled(false);  // to disable modify ability
				cbBlocksize.setEnabled(false);
				cbPlayers.setEnabled(false);
				
				// TODO: Make conveyor. This variant is awfull!
				if (ingame[0] && playerNumber == 1) { // if player #1 in the game
					human.turn(e.getX()/CELL_SIZE, e.getY()/CELL_SIZE, field, ai); // TODO: to delete AI from Human.java
					canvas.repaint();
					if (ingame[2]) { playerNumber = 2; } // next move for player #2
					//System.out.println("Player 1");
					
					if (ingame[1]) { // if AI in the game
						// pause(1000);
						if (!field.isGameOver()) ai.turn(field);
						canvas.repaint();
						// System.out.println("Player AI");
					}
					
					if (field.isGameOver()) {
						JOptionPane.showMessageDialog(TicTacToe.this, field.getGameOverMsg());
					}
					return;
				}
				
				if (ingame[2] && playerNumber == 2) { // if player #2 in the game
					human2.turn(e.getX()/CELL_SIZE, e.getY()/CELL_SIZE, field, ai); // TODO: delete AI from Human.java
					canvas.repaint();
					playerNumber = 1;
					//System.out.println("Player 2");
					
					if (field.isGameOver()) {
						JOptionPane.showMessageDialog(TicTacToe.this, field.getGameOverMsg());
					}
					return;
				}
            }
        });
		
		
        JButton init = new JButton(BTN_INIT);
        init.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                field.init();
                canvas.repaint();
				
				cbFieldsize.setEnabled(true); // open modify ability
				cbBlocksize.setEnabled(true);
				cbPlayers.setEnabled(true);
            }
        });
        JButton exit = new JButton(BTN_EXIT);
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JPanel bp = new JPanel();
        bp.setLayout(new GridLayout()); // for button panel
        bp.add(init);
        bp.add(exit);
		
		
		// ------- at start ------------ 1
		presetOptions(FIELD_SIZE, BLOCK_SIZE); // 3x3 in begin
		ingame[0] = true;  // Player 1
		ingame[1] = true;  // AI
		ingame[2] = false; // Player 2
		// ------- at start ------------ 0
		
		
		cbFieldsize.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				JComboBox box = (JComboBox)e.getSource();
				
				// didn't verify block size for changing big field to smaller than block
				// TODO: do nothing this time
				switch (box.getSelectedIndex()) {
					case 0: // 3x3
						presetOptions(3, 3);
						cbBlocksize.setSelectedIndex(BLOCK_SIZE-3);
						break;
					case 1: // 4x4
						BLOCK_SIZE = (BLOCK_SIZE>4) ? 4 : BLOCK_SIZE; 
						presetOptions(4, BLOCK_SIZE);
						cbBlocksize.setSelectedIndex(BLOCK_SIZE-3);
						break;
					case 2: // 5x5
						BLOCK_SIZE = (BLOCK_SIZE>5) ? 5 : BLOCK_SIZE;
						presetOptions(5, BLOCK_SIZE);
						cbBlocksize.setSelectedIndex(BLOCK_SIZE-3);
						break;
					case 3: // 6x6
						presetOptions(6, BLOCK_SIZE);
						break;
					case 4: // 9x9
						presetOptions(9, BLOCK_SIZE);
						break;
					case 5: // 12x12
						presetOptions(12, BLOCK_SIZE);
						break;
				}
				// System.out.println("1 - BLOCK_SIZE: "+BLOCK_SIZE);
				playerNumber = 1;
            }
        });

		cbBlocksize.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				JComboBox box = (JComboBox)e.getSource();

				switch (box.getSelectedIndex()) {
					case 0: // 3
						presetOptions(FIELD_SIZE, 3);
						break;
					case 1: // 4
						if (FIELD_SIZE<4) { 
							box.setSelectedIndex(0); 
							JOptionPane.showMessageDialog(TicTacToe.this, "Field's size is too small for block size 4");
							break; }
						presetOptions(FIELD_SIZE, 4);
						break;
					case 2: // 5
						if (FIELD_SIZE<5) { 
							box.setSelectedIndex(0); 
							JOptionPane.showMessageDialog(TicTacToe.this, "Field's size is too small for block size 5");
							break; 
						}
						presetOptions(FIELD_SIZE, 5);
						break;
				}
				playerNumber = 1;
            }
        });
		
		cbPlayers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				JComboBox box = (JComboBox)e.getSource();
				// System.out.println("3 - BLOCK_SIZE: "+BLOCK_SIZE);
				switch (box.getSelectedIndex()) {
					case 0: //  "Human & AI"
						ingame[0] = true;  // Player 1
						ingame[1] = true;  // AI
						ingame[2] = false; // Player 2
						break;
					case 1: // "2 Humans & AI"
						ingame[0] = true;  // Player 1
						ingame[1] = true;  // AI
						ingame[2] = true; // Player 2
						break;
					case 2: // "2 Humans"
						ingame[0] = true;  // Player 1
						ingame[1] = false;  // AI
						ingame[2] = true;  // Player 2
						break;
				}
				playerNumber = 1;
            }
        });
		
        setLayout(new BorderLayout()); // for main window
        add(toolBar, BorderLayout.NORTH);
        add(bp, BorderLayout.SOUTH);
        add(canvas, BorderLayout.CENTER);
        setVisible(true);
    }

    class Canvas extends JPanel { // for painting
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            field.paint(g);
        }
    }
	

	public void pause (int msec) {
		try {
			Thread.sleep(msec);
			// any action
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}