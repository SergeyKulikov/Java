package team;

import animals.*;
// import barrier.*;

//ObstacleCourse

/**
 * Write a description of class Hippo here.
 *
 * @author (Sergey Kulikov)
 * @version (07.06.2017)
 */

public class Team {
	public Animal []team_members;
	public boolean results[][];
	public int barrier_count;
	
	public Team(Animal []team_members, int barrier_count) {
        this.team_members = team_members;
		this.barrier_count = barrier_count;
		this.results = new boolean[team_members.length][barrier_count];
		
		clearResults();
    }
	
	public void clearResults() {
		for (int i=0; i<team_members.length; i++) {
			for (int j=0; j<barrier_count; j++) {
				this.results[i][j] = false;
			}
		}
	}
		
	public String showResults(boolean winnersOnly) {
		String rez = "";
		for (int i=0; i<team_members.length; i++) {
			rez += ("\n" + team_members[i].toString());
			for (int j=0; j<barrier_count; j++) {
				rez += ("\n | " + j + ": " + this.results[i][j]);
			}
		}
		return rez;
	}	
	
	public String showWinnersResults() {
		return showResults(true);
	}	
	
}