package pbox2d.examples.mouse;

// The Nature of Code
// <http://www.shiffman.net/teaching/nature>
// Spring 2010
// PBox2D example

// A rectangular box


import org.jbox2d.collision.shapes.PolygonDef;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;

import processing.core.PConstants;
import util.ProcessingObject;

public class Box extends ProcessingObject {

	// We need to keep track of a Body and a width and height
	Body body;
	float w;
	float h;

	// Constructor
	public Box(float x_, float y_) {
		float x = x_;
		float y = y_;
		w = 24;
		h = 24;
		// Add the box to the box2d world
		makeBody(new Vec2(x,y),w,h);
	}
	
	// This function removes the particle from the box2d world
	public void killBody() {
		TossSpring.box2d.destroyBody(body);
	}

	public boolean contains(float x, float y) {
		Vec2 worldPoint = TossSpring.box2d.coordPixelsToWorld(x, y);
		Shape s = body.getShapeList();
		boolean inside = s.testPoint(body.getMemberXForm(),worldPoint);
		return inside;
	}

	// Drawing the box
	public void display() {
		// We look at each body and get its screen position
		Vec2 pos = TossSpring.box2d.getBodyPixelCoord(body);
		// Get its angle of rotation
		float a = body.getAngle();

		p.rectMode(PConstants.CENTER);
		p.pushMatrix();
		p.translate(pos.x,pos.y);
		p.rotate(-a);
		p.fill(175);
		p.stroke(0);
		p.rect(0,0,w,h);
		p.popMatrix();
	}

	// This function adds the rectangle to the box2d world
	void makeBody(Vec2 center, float w_, float h_) {
		// Define and create the body
		BodyDef bd = new BodyDef();
		bd.position.set(TossSpring.box2d.coordPixelsToWorld(center));
		body = TossSpring.box2d.createBody(bd);
		
		// Define the shape -- a polygon (this is what we use for a rectangle)
		PolygonDef sd = new PolygonDef();
		float box2dW = TossSpring.box2d.scalarPixelsToWorld(w_/2);
		float box2dH = TossSpring.box2d.scalarPixelsToWorld(h_/2);
		sd.setAsBox(box2dW, box2dH);
		// Parameters that affect physics
		sd.density = 1.0f;
		sd.friction = 0.3f;
		sd.restitution = 0.5f;
		
		// Attach that shape to our body!
		body.createShape(sd);
		body.setMassFromShapes();
		
		// Give it some initial random velocity
		body.setLinearVelocity(new Vec2(p.random(-5,5),p.random(2,5)));
		body.setAngularVelocity(p.random(-5,5));
	}

}

