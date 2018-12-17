package me.blok601.nightshadeuhc.entity.object;


public enum GameState {

	WAITING, STARTING, INGAME, MEETUP, ENDGAME;
	
	private static GameState state;
	
	public static void setState(GameState s){
		state = s;
	}
	
	public static GameState getState(){
		return state;
	}

	public static  boolean gameHasStarted(){
		return (state != WAITING && state != STARTING);
	}
	
}
