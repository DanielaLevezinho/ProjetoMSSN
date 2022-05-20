package physics;

import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class Body extends Mover {
	protected int color;
	protected float radius;

	public Body(PVector pos, PVector vel, float mass, float radius, int color) {
		super(pos, vel, mass, radius);
		this.color  = color;
		this.radius = radius;
	}

	public void display(PApplet p, SubPlot plt) {
		p.pushStyle();
		float[] pp = plt.getPixelCoord(pos.x, pos.y);
		float[] r  = plt.getVectorCoord(radius, radius);
		p.noStroke();
		p.fill(255,p.random(165), 0);
		p.rect(pp[0],  pp[1], p.width/200, p.height/26);
//		p.circle(pp[0], pp[1], 2*r[0]);
		p.popStyle();
	}
}
