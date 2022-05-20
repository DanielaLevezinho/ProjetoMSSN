package fractais;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

public class ForestApp implements IProcessingApp {

	private double[] window = { -10, 10, -10, 10 };
	private float[] viewport = { 0, 0, 1, 1 };
	private SubPlot plt;
	Tree tree;

	@Override
	public void setup(PApplet p) {
		plt = new SubPlot(window, viewport, p.width, p.height);
		grow(p);
	}

	@Override
	public void draw(PApplet p, float dt) {
		p.background(255);

		if (!tree.getDead()) {
			tree.grow(dt);
			tree.display(p, plt);
		}

	}

	public void grow(PApplet p) {
		PVector pos = new PVector();

		Rule[] rules = new Rule[1];
		rules[0] = new Rule('F', "F+F--F+F");
		float rand = p.random(0.25f, 0.35f);
		tree = new Tree("F--F--F", rules, pos, .4f, PApplet.radians(60f), 3, rand, 1f, 0, p);
	}

	@Override
	public void keyPressed(PApplet p) {

	}


	@Override
	public void mousePressed(PApplet p) {
		// TODO Auto-generated method stub

	}

}
