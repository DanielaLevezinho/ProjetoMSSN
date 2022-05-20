package game;

import processing.core.PApplet;

public class GUILife {

	
	
	public void life(Player player, PApplet p) {

		if (player.getHP() == 3) {
			allLife(p);
		}
		if (player.getHP() == 2) {
			twoLife(p);
		}

		if (player.getHP() == 1) {
			oneLife(p);
		}

	}

	public void allLife(PApplet p) {
		p.smooth();
		p.noStroke();
		p.fill(255, 0, 0);
		p.beginShape();
		p.vertex(270, 12);
		p.bezierVertex(270, -5, 300, 5, 270, 30);
		p.vertex(270, 12);
		p.bezierVertex(270, -5, 239, 5, 270, 30);
		p.endShape();

		p.smooth();
		p.noStroke();
		p.fill(255, 0, 0);
		p.beginShape();
		p.vertex(300, 12);
		p.bezierVertex(300, -5, 330, 5, 300, 30);
		p.vertex(300, 12);
		p.bezierVertex(300, -5, 270, 5, 300, 30);
		p.endShape();

		p.smooth();
		p.noStroke();
		p.fill(255, 0, 0);
		p.beginShape();
		p.vertex(330, 12);
		p.bezierVertex(330, -5, 360, 5, 330, 30);
		p.vertex(330, 12);
		p.bezierVertex(330, -5, 299, 5, 330, 30);
		p.endShape();
	}

	public void twoLife(PApplet p) {
		p.smooth();
		p.noStroke();
		p.fill(255, 0, 0);
		p.beginShape();
		p.vertex(270, 12);
		p.bezierVertex(270, -5, 300, 5, 270, 30);
		p.vertex(270, 12);
		p.bezierVertex(270, -5, 239, 5, 270, 30);
		p.endShape();

		p.smooth();
		p.noStroke();
		p.fill(255, 0, 0);
		p.beginShape();
		p.vertex(300, 12);
		p.bezierVertex(300, -5, 330, 5, 300, 30);
		p.vertex(300, 12);
		p.bezierVertex(300, -5, 270, 5, 300, 30);
		p.endShape();

		p.smooth();
		p.stroke(255, 0, 0);
		p.fill(0);
		p.beginShape();
		p.vertex(330, 12);
		p.bezierVertex(330, -5, 360, 5, 330, 30);
		p.vertex(330, 12);
		p.bezierVertex(330, -5, 299, 5, 330, 30);
		p.endShape();
	}

	public void oneLife(PApplet p) {
		p.smooth();
		p.noStroke();
		p.fill(255, 0, 0);
		p.beginShape();
		p.vertex(270, 12);
		p.bezierVertex(270, -5, 300, 5, 270, 30);
		p.vertex(270, 12);
		p.bezierVertex(270, -5, 239, 5, 270, 30);
		p.endShape();

		p.smooth();
		p.stroke(255, 0, 0);
		p.fill(0);
		p.beginShape();
		p.vertex(300, 12);
		p.bezierVertex(300, -5, 330, 5, 300, 30);
		p.vertex(300, 12);
		p.bezierVertex(300, -5, 270, 5, 300, 30);
		p.endShape();

		p.smooth();
		p.stroke(255, 0, 0);
		p.fill(0);
		p.beginShape();
		p.vertex(330, 12);
		p.bezierVertex(330, -5, 360, 5, 330, 30);
		p.vertex(330, 12);
		p.bezierVertex(330, -5, 299, 5, 330, 30);
		p.endShape();
	}

}
