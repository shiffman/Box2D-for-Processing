// The Nature of Code
// <http://www.shiffman.net/teaching/nature>
// Spring 2010
// PBox2D example

// A fixed boundary class

package pbox2d.examples.falling;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;

import processing.core.PConstants;

import util.ProcessingObject;

public class Boundary extends ProcessingObject {
	
	// A boundary is a simple rectangle with x,y,width,and height
	float x;
	float y;
	float w;
	float h;
	// But we also have to make a body for box2d to know about it
	Body body;
	
	Boundary(float x_,float y_, float w_, float h_) {
		x = x_;
		y = y_;
		w = w_;
		h = h_;
		
		// Figure out the box2d coordinates
		float box2dW = Boxes.box2d.scalarPixelsToWorld(w/2);
		float box2dH = Boxes.box2d.scalarPixelsToWorld(h/2);
		Vec2 center = new Vec2(x,y);

		// Define the polygon
		PolygonShape sd = new PolygonShape();
		sd.setAsBox(box2dW, box2dH);
		
		// Create the body
		BodyDef bd = new BodyDef();
		bd.position.set(Boxes.box2d.coordPixelsToWorld(center));
		bd.type = BodyType.STATIC; // This is the default but putting it here anyway
		
		body = Boxes.box2d.createBody(bd);
		
		// Attach that shape to our body with a fixture
		// This quick function creates a body with a density
		// If we need to set more parameters, we need a FixtureDef
		body.createFixture(sd,1.0f);
	}

	// Draw the boundary, if it were at an angle we'd have to do something fancier
	void display() {
		p.fill(0);
		p.stroke(0);
		p.rectMode(PConstants.CENTER);
		p.rect(x,y,w,h);
	}

}
