package fractais;

import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class Turtle {

	private float len;
	private float angle;

	public Turtle(float len, float angle) {
		this.len = len;
		this.angle = angle;
	}

	public void setPose(PVector position, float orientation, PApplet p, SubPlot plt) {
		float[] pp = plt.getPixelCoord(position.x, position.y);
		p.translate(pp[0], pp[1]);
		p.rotate(-orientation);
	}

	public void scaling(float s) {
		len *= s;
	}

	public void setLength(float length) {
		len = length;
	}

	public float getLength() {
		return len;
	}

	public void render(LSystem lsys, PApplet p, SubPlot plt) {
		int id = lsys.getId();

		if (id == 0) {
			p.stroke(p.random(0, 127), p.random(128, 255), p.random(255));
		}

		float[] lenPix = plt.getVectorCoord(len, len);

		for (int i = 0; i < lsys.getSequence().length(); i++) {
			char c = lsys.getSequence().charAt(i);
			if (c == 'F' || c == 'G') {
				p.line(0, 0, lenPix[0], 0);
				p.translate(lenPix[0], 0);

			} else if (c == 'f') {
				p.translate(lenPix[0], 0);

			} else if (c == '+') {
				p.rotate(angle);

			} else if (c == '-') {
				p.rotate(-angle);

			} else if (c == '[') {
				p.pushMatrix(); // guarda posiçao da turtle
			} else if (c == ']') {
				p.popMatrix();
			}
		}

	}
//
//	private void getColor(LSystem lsys, PApplet p, int i) {
//		String sequence = lsys.getSequence();
//		if (i < sequence.length() - 1 && lsys.getId() == 1) {
//			if (sequence.charAt(i + 1) == ']') {
//				p.stroke(255, 0, 0);
//			} else {
//				p.stroke(139, 69, 19);
//			}
//		}
//
//	}
}
