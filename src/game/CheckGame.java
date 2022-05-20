package game;

import processing.core.PApplet;

public class CheckGame {
	 	private boolean gameOver;
	    private boolean start;
	    private boolean playerWon;
	    


	    public void gameOver(PApplet p, GameApp game, Player player) {

	        if (game.listAttack.size() == 0 && game.listPatrol.size() == 0 && game.listParado.size() == 0) {
	            playerWon = true;
	            gameOver = true;
	            start = false;
	        }
	        
	        if (player.getHP() == 0) {
	            playerWon = false;
	            gameOver = true;
	            start = false;
	        }

		}
	    
	    public boolean getGameOver() {
	        return this.gameOver;
	    }
	    
	    
	    public boolean getPlayerWon() {
	        return this.playerWon;
	    }
	    
	    public boolean setPlayerWon() {
	        return this.playerWon;
	    }
	    
	    public boolean getStart() {
	        return this.start;
	    }
	    
	    public void setGameOver(boolean gameOver) {
	        this.gameOver = gameOver;
	    }
	    
	    public void setStart(boolean start) {
	        this.start = start;
	    }
}
