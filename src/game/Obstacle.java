package game;

import boids.Boid;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class Obstacle extends Boid {

	
	protected Obstacle(PVector pos, float mass, float radius, int color, PApplet p, SubPlot plt) {
		super(pos, mass, radius, color, p, plt);
	}

	@Override
	public void display(PApplet p, SubPlot plt) {
		p.pushStyle();
		float[] pp = plt.getPixelCoord(pos.x, pos.y);
		float[] r  = plt.getVectorCoord(radius, radius);
		p.noStroke();
		p.fill(p.color(255,0,0));
		p.rect(pp[0], pp[1], p.width/10, p.height/90);
		p.popStyle();
	}

}
