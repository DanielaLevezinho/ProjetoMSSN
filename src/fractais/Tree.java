package fractais;

import javax.lang.model.element.NestingKind;

import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class Tree {

	private LSystem lsys;
	private Turtle turtle;
	private PVector position;

	private float length;
	private float growthRate;

	private int numberOfSeasonsToGrow;
	private float scalingFactor;
	private float intervalBetweenSeasons;
	private float now;
	private float nextSeasonTime;
	private PApplet p;
	private boolean dead;

	public Tree(String axiom, Rule[] rules, PVector position, float referenceLength, float angle, int niter,
			float scalingFactor, float intervalBetweenSeasons, int id, PApplet p) {
		lsys = new LSystem(axiom, rules, id);

		length = 0;
		growthRate = referenceLength / intervalBetweenSeasons;
		turtle = new Turtle(0, angle);

		this.position = position;
		numberOfSeasonsToGrow = niter;
		this.scalingFactor = scalingFactor;

		this.intervalBetweenSeasons = intervalBetweenSeasons;
		now = p.millis() / 1000f; // numero de segundos passados
		nextSeasonTime = now + intervalBetweenSeasons;
		this.p = p;
	}

	public void grow(float dt) {
		now += dt;
		if (now < nextSeasonTime) {
			length += growthRate * dt;
			turtle.setLength(length);

		}

		else if (now > nextSeasonTime && lsys.getGeneration() < numberOfSeasonsToGrow) {
			lsys.nextGeneration();
			length *= scalingFactor;
			growthRate *= scalingFactor;
			turtle.setLength(length);
			nextSeasonTime = now + intervalBetweenSeasons;
		}

		else {

			this.dead = true;
		}
	}

	public boolean getDead() {
		return this.dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public void display(PApplet p, SubPlot plt) {
		p.pushMatrix();
		turtle.setPose(position, (float) Math.PI / 2, p, plt);
		turtle.render(lsys, p, plt);
		p.popMatrix();
	}
}
