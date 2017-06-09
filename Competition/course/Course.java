package course;

import barrier.*;
import animals.*;
import team.*;

/**
 * Obstacle course class
 *
 * @author (Sergey Kulikov)
 * @version (07.06.2017)
 */

public class Course {
	public Object []barriers;
	
	public Course(Object []barriers) {
		this.barriers = barriers;
	}
	
	public void doIt(Team team) {
		for (int an=0; an<team.team_members.length; an++) { // get the animal by index they were added to array
			for (int br=0; br<barriers.length; br++) { // get barrier at obstacle course by index they were added
				if (barriers[br] instanceof Wall) {
					team.results[an][br] = ((Wall)barriers[br]).doIt(team.team_members[an]);
				}
				if (barriers[br] instanceof Water) {
					team.results[an][br] = ((Water)barriers[br]).doIt(team.team_members[an]);
				}
				if (barriers[br] instanceof Track) {
					team.results[an][br] = ((Track)barriers[br]).doIt(team.team_members[an]);
				}
			}
        } 
	}
	
	
    @Override
    public String toString() {
		String rez = "";
		for (int b=0; b<barriers.length; b++) {
			//if (barriers[b] instanceof Water) 
			//	rez += (b+". "+((Water)this.barriers[b]).toString());
				
			//if (barriers[b] instanceof Wall) 
			//	rez += (b+". "+((Wall)this.barriers[b]).toString());
			
			//if (barriers[b] instanceof Track) 
			//	rez += (b+". "+((Track)this.barriers[b]).toString());
			rez += ("\n"+b+". "+this.barriers[b].toString());
		
		}
		return rez;
	}
	

}
