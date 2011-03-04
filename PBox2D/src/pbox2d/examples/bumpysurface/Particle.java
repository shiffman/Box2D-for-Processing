// The Nature of Code
// <http://www.shiffman.net/teaching/nature>
// Spring 2010
// PBox2D example

// A circular particle

package pbox2d.examples.bumpysurface;

import org.jbox2d.collision.shapes.CircleDef;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;

import util.ProcessingObject;

public class Particle extends ProcessingObject {

	// We need to keep track of a Body and a radius
	Body body;
	float r;

	public Particle(float x, float y, float r_) {
		r = r_;
		// This function puts the particle in the Box2d world
		makeBody(x,y,r);
	}
	
	// This function removes the particle from the box2d world
	public void killBody() {
		BumpySurface.box2d.destroyBody(body);
	}

	// Is the particle ready for deletion?
	public boolean done() {
		// Let's find the screen position of the particle
		Vec2 pos = BumpySurface.box2d.getScreenPos(body);
		// Is it off the bottom of the screen?
		if (pos.y > p.height+r*2) {
			killBody();
			return true;
		}
		return false;
	}

	// 
	public void display() {
		// We look at each body and get its screen position
		Vec2 pos = BumpySurface.box2d.getScreenPos(body);
		// Get its angle of rotation
		float a = body.getAngle();
		p.pushMatrix();
		p.translate(pos.x,pos.y);
		p.rotate(-a);
		p.fill(175);
		p.stroke(0);
		p.strokeWeight(1);
		p.ellipse(0,0,r*2,r*2);
		// Let's add a line so we can see the rotation
		p.line(0,0,r,0);
		p.popMatrix();
	}
	
	 // Here's our function that adds the particle to the Box2D world
     void makeBody(float x, float y, float r) {
    	 // Define a body
    	BodyDef bd = new BodyDef();
    	// Set its position
    	bd.position = BumpySurface.box2d.coordPixelsToWorld(x,y);
    	body = BumpySurface.box2d.world.createBody(bd);
    	
    	// Make the body's shape a circle
    	CircleDef cd = new CircleDef();
    	cd.radius = BumpySurface.box2d.scalarPixelsToWorld(r);
    	cd.density = 1.0f;
    	cd.friction = 0.01f;
    	cd.restitution = 0.3f; // Restitution is bounciness
    	body.createShape(cd);
    	
    	// Always do this at the end
    	body.setMassFromShapes();
    	
    	// Give it a random initial velocity (and angular velocity)
    	body.setLinearVelocity(new Vec2(p.random(-10f,10f),p.random(5f,10f)));
    	body.setAngularVelocity(p.random(-10,10));
    }

	




}
