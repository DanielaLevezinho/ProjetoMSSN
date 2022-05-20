package fractais;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

public class LSystemApp implements IProcessingApp {

	private LSystem lsys;
	private double[] window = { -10, 10, -10, 10 };
	private float[] viewport = { 0.1f, 0.1f, 0.8f, 0.8f };
	private PVector startingPos = new PVector();
	private SubPlot plt;
	private Turtle turtle;
	private ArrayList<LSystem> lSystems = new ArrayList<LSystem>();

	@Override
	public void setup(PApplet p) {
		plt = new SubPlot(window, viewport, p.width, p.height);
		snowFlake();

	}

	private void snowFlake() {
		Rule[] rules = new Rule[1];
		rules[0] = new Rule('F', "F+F--F+F");

		lsys = new LSystem("F--F--F", rules, 0);
		turtle = new Turtle(6, PApplet.radians(60f));
		lSystems.add(lsys);

	}

	private void arvore() {
		Rule[] rules = new Rule[5];
		rules[0] = new Rule('F', "F[+F]F[-F]F");
		rules[1] = new Rule('[', "[");
		rules[2] = new Rule(']', "]");
		rules[3] = new Rule('+', "+");
		rules[4] = new Rule('-', "-");

		lsys = new LSystem("F", rules, 1);
		turtle = new Turtle(3, PApplet.radians(25.7f));
	}

	@Override
	public void draw(PApplet p, float dt) {
		p.background(255);
		turtle.setPose(startingPos, PApplet.radians(90), p, plt);
		turtle.render(lsys, p, plt);
		turtle.scaling(0.25f);
		turtle.setLength(turtle.getLength());
		if (lsys.getGeneration() == 7) {
			turtle.setLength(turtle.getLength());
		} else {
			lsys.nextGeneration();
		}

	}

	@Override
	public void mousePressed(PApplet p) {
		System.out.println(lsys.getSequence());

	}

	@Override
	public void keyPressed(PApplet p) {
		// TODO Auto-generated method stub

	}

}
