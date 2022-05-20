package game;


import boids.Boid;
import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class Player extends Boid {

	private int hp;

	protected Player(PVector pos, float mass, float radius, int color, PApplet p, SubPlot plt) {
		super(pos, mass, radius, color, p, plt);
		this.hp = 3;
	}

	public Body fire() {
		Body shot = new Body(this.getPos(), new PVector(0, 9), 0.3f, 0.2f, 0);
		return shot;
	}
	
	public int getHP() {
		return this.hp;
	}
	
	public void setHP(int hp) {
		this.hp = hp;
	}

	

}
