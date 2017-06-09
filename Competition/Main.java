import animals.*;
import barrier.*;
import team.*;
import course.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Lessoning example of animal competition
 *
 * @author (Sergey Kulikov)
 * @version (09.06.2017)
 */
public class Main extends JFrame{
	//private Course barriers;


    public static void main(String[] args) {
		new Main().start();
	}
	
	public void start() {
		Course barriers = new Course(
								new Object[] {
									new Track(80), 	// first
									new Wall(3), 	// second
									new Water(10), 	// third
									new Track(120) 	
								}
							);  // obstacle course
		
		Team white = new Team(
						new Animal[] { 
							new Cat("Murzik"), new Hen("Izzy"), 
							new Hippo("Hippopo"), new Hen("Mother Zoja")
						},
						barriers.barriers.length // they have to be ready for length of obstacle course
					);  // First team
					
		Team black = new Team(
						new Animal[] { 
							new Hippo("Doddo"), new Cat("Stinky"), 
							new Hen("Red feathers"), new Cat("Pirate")
						},
						barriers.barriers.length // they have to be ready for length of obstacle course
					);  // Second team
		
		barriers.doIt(white);
		barriers.doIt(black);
		
        setTitle("Animal's Competition");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(30, 30, 800, 600);
		setLayout(new BorderLayout()); // for main window
		
		JTextArea tField = new JTextArea("\nThe obstacle course consists of : "+barriers.toString() + "\n\n" +
							"\nThe white team results: "+white.showResults(true) +
							"\nThe black team results:"+black.showResults(true));
							
		JScrollPane pnl = new JScrollPane(tField);
		pnl.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		add(pnl);					
        setVisible(true);

		
		/*
		System.out.println("\nThe obstacle course consists of : "+barriers.toString());
		System.out.println("\nThe white team results: "+white.showResults(true));
		System.out.println("\nThe black team results:"+black.showResults(true));
		*/
		
    }
}