package game;

import boids.Boid;
import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class NPC extends Boid {
	private int atackerPoints;
	private int patrolPoints;
	private int staticPoints;

	protected NPC(PVector pos, float mass, float radius, int color, PApplet p, SubPlot plt) {
		super(pos, mass, radius, color, p, plt);
		this.atackerPoints = 10;
		this.patrolPoints = 5;
		this.staticPoints = 1;
	}

	public int getAtackerPoints() {
		return this.atackerPoints;
	}

	public void setAtackerPoints(int atackerPoints) {
		this.atackerPoints = atackerPoints;
	}

	public int getPatrolPoints() {
		return this.patrolPoints;
	}

	public void setPatrolPoints(int patrolPoints) {
		this.patrolPoints = patrolPoints;
	}

	public int getStaticPoints() {
		return this.staticPoints;
	}

	public void setStaticPoints(int staticPoints) {
		this.staticPoints = staticPoints;
	}

	public Body fire() {
		Body shot = new Body(this.getPos(), new PVector(0, -8), 0.3f, 0.2f, 0);
		return shot;
	}

}
