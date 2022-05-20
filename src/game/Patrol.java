package game;

import boids.Behavior;
import boids.Boid;
import physics.Body;
import processing.core.PVector;

public class Patrol extends Behavior {

	private Body initTarget;
	private Body finalTarget;
	private int targetPosX = 0;
	private int targetPosY = 0;
	
	
	public Patrol(float weight, int targetPosX, int targetPosY) {
		super(weight);	
		this.targetPosX = targetPosX;
		this.targetPosY = targetPosY;
	}
	

	@Override
	public PVector getDesiredVelocity(Boid me) {
		
		initTarget = new Body(new PVector(-targetPosX, targetPosY), new PVector(), 1, 1, 100);
		finalTarget = new Body(new PVector(targetPosX, targetPosY), new PVector(), 1, 1, 100);
			
		
		PVector vd = PVector.sub(me.getEye().getTarget().getPos(), me.getPos());
		float distTarget = vd.mag();

		if (me.getEye().getTarget().getPos().x == -targetPosX && distTarget < 2) {
			me.getEye().setTarget(finalTarget);
		}
		else if(me.getEye().getTarget().getPos().x == targetPosX && distTarget < 2) {
			me.getEye().setTarget(initTarget);
		}
		
		return vd;
	}

	
}
