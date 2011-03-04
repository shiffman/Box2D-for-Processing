// The Nature of Code
// <http://www.shiffman.net/teaching/nature>
// Spring 2010
// PBox2D example

// A rectangular box

package pbox2d.examples.falling;

import org.jbox2d.collision.shapes.PolygonDef;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.common.XForm;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;

import pbox2d.examples.mouse.TossSpring;
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
		w = p.random(4,16);
		h = p.random(4,16);
		// Add the box to the box2d world
		makeBody(new Vec2(x,y),w,h);
	}
	
	// This function removes the particle from the box2d world
	public void killBody() {
		Boxes.box2d.destroyBody(body);
	}

	// Is the particle ready for deletion?
	public boolean done() {
		// Let's find the screen position of the particle
		Vec2 pos = Boxes.box2d.getScreenPos(body);
		// Is it off the bottom of the screen?
		if (pos.y > p.height+w*h) {
			killBody();
			return true;
		}
		return false;
	}

	// Drawing the box
	public void display() {
		// We look at each body and get its screen position
		Vec2 pos = Boxes.box2d.getScreenPos(body);
		// Get its angle of rotation
		float a = body.getAngle();

//		p.rectMode(PConstants.CENTER);
//		p.pushMatrix();
//		p.translate(pos.x,pos.y);
//		p.rotate(-a);
//		p.fill(175);
//		p.stroke(0);
//		p.rect(0,0,w,h);
//		p.popMatrix();
		
		
		XForm xf = body.getMemberXForm();
		PolygonShape poly = (PolygonShape) body.getShapeList();
		int vertexCount = poly.getVertexCount();
		Vec2[] localVertices = poly.getVertices();
		Vec2[] vertices = new Vec2[vertexCount];
		for (int i = 0; i < vertexCount; ++i) {
		       vertices[i] = Boxes.box2d.coordWorldToPixels(XForm.mul(xf, localVertices[i]));
		}
		
		p.beginShape();
		for (int i = 0; i < vertexCount; ++i) {
		     p.vertex(vertices[i].x,vertices[i].y);
		}
		p.endShape();
		
	}

	// This function adds the rectangle to the box2d world
	void makeBody(Vec2 center, float w_, float h_) {
		// Define and create the body
		BodyDef bd = new BodyDef();
		//bd.position.set(Boxes.box2d.screenToWorld(center));
		body = Boxes.box2d.createBody(bd);
		
		// Give it some initial random velocity
		body.setLinearVelocity(new Vec2(p.random(-5,5),p.random(2,5)));
		body.setAngularVelocity(p.random(-5,5));
		
		// Define the shape -- a polygon (this is what we use for a rectangle)
		PolygonDef sd = new PolygonDef();
		//float box2dW = Boxes.box2d.scaleScreenToWorld(w_/2);
		//float box2dH = Boxes.box2d.scaleScreenToWorld(h_/2);
		//sd.setAsBox(box2dW, box2dH);
		
		
		Vec2 v1 = Boxes.box2d.coordPixelsToWorld(center.x,center.y-w_/2);
		Vec2 v2 = Boxes.box2d.coordPixelsToWorld(center.x-w_/2,center.y+w_/2);
		Vec2 v3 = Boxes.box2d.coordPixelsToWorld(center.x+w_/2,center.y+w_/2);
		
		
		sd.addVertex(v1);
		sd.addVertex(v2);
		sd.addVertex(v3);
		
		
		// Parameters that affect physics
		sd.density = 1.0f;
		sd.friction = 0.3f;
		sd.restitution = 0.5f;
		
		// Attach that shape to our body!
		body.createShape(sd);
		body.setMassFromShapes();
		

	}

}
