package src.oot.game;


public class Player {
	
	/**
	 *@author Nico Gensheimer
	 *
	 */

	private String color;
	private String name;
	private int highscore;
	
	Player(String name, String color){
		this.name = name;
		this.color = color;		
	}
	
	public int getHighscore() {
		return highscore;
	}
	public void setHighscore(int highscore) {
		this.highscore = highscore;
	}
	
	public String getColor() {
		return color;
	}


	public String getName() {
		return name;
	}

	
}
