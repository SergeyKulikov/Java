package barrier;

import animals.*;
/**
 * Write a description of class Water here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Water {
    private int length;

    public Water(int length) {
        this.length = length;
    }

    public boolean doIt(Animal animal) {
        if (animal instanceof Swimable)
            return ((Swimable) animal).swim(length);
        else
            return false;
    }
	
	
    @Override
    public String toString() {
        return this.getClass().getName();
    }    
	
}