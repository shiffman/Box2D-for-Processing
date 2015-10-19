import processing.core.*; 

import shiffman.box2d.*;

import org.jbox2d.collision.shapes.*; 
import org.jbox2d.common.*; 
import org.jbox2d.dynamics.*; 
import org.jbox2d.dynamics.joints.DistanceJointDef;

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class QuickTest extends PApplet {

	// The Nature of Code
	// <http://www.shiffman.net/teaching/nature>
	// Spring 2011
	// PBox2D example

	// Basic example of falling rectangles


	// A reference to our box2d world
	Box2DProcessing box2d;

	// A list we'll use to track fixed objects
	ArrayList<Boundary> boundaries;
	// A list for all of our rectangles
	ArrayList<Box> boxes;
	
	public void settings() {
		size(400,300);

	}

	public void setup() {
		randomSeed(5);

		// Initialize box2d physics and create the world
		box2d = new Box2DProcessing(this,10);
		box2d.createWorld();
		// We are setting a custom gravity
		box2d.setGravity(0, -20);

		// Create ArrayLists	
		boxes = new ArrayList<Box>();
		boundaries = new ArrayList<Boundary>();

		// Add a bunch of fixed boundaries
		boundaries.add(new Boundary(width/4,height-5,width/2-50,10));
		boundaries.add(new Boundary(3*width/4,height-50,width/2-50,10));
	}

	public void draw() {
		background(255);
		
		// We must always step through time!
		box2d.step(1.0f/60,10,10);

		// Boxes fall from the top every so often
		if (random(1) < 1) {
			Box p = new Box(width/2,30);
			boxes.add(p);
		}

		// Display all the boundaries
		for (Boundary wall: boundaries) {
			wall.display();
		}

		// Display all the boxes
		for (Box b: boxes) {
			b.display();
		}

		// Boxes that leave the screen, we delete them
		// (note they have to be deleted from both the box2d world and our list
		for (int i = boxes.size()-1; i >= 0; i--) {
			Box b = boxes.get(i);
			if (b.done()) {
				boxes.remove(i);
			}
		}
		
	    if (frameCount >= 908) {
	    	System.exit(0);
	    }
	    
	

	}




	// The Nature of Code
	// <http://www.shiffman.net/teaching/nature>
	// Spring 2010
	// PBox2D example

	// A fixed boundary class

	class Boundary {

		// A boundary is a simple rectangle with x,y,width,and height
		float x;
		float y;
		float w;
		float h;

		// But we also have to make a body for box2d to know about it
		Body b;

		Boundary(float x_,float y_, float w_, float h_) {
			x = x_;
			y = y_;
			w = w_;
			h = h_;

			// Create the body
			BodyDef bd = new BodyDef();
			bd.position.set(box2d.coordPixelsToWorld(x,y));
			b = box2d.createBody(bd);

			// Figure out the box2d coordinates
			float box2dW = box2d.scalarPixelsToWorld(w/2);
			float box2dH = box2d.scalarPixelsToWorld(h/2);

			// Define the polygon
			PolygonShape sd = new PolygonShape();
			sd.setAsBox(box2dW, box2dH);

			FixtureDef fd = new FixtureDef();
			fd.shape = sd;
			fd.density = 0;
			fd.friction = 0.3f;
			fd.restitution = 0.5f;
			

			b.createFixture(fd);
			
			ChainShape cs;
			//cs.create
			
			
		}

		// Draw the boundary, if it were at an angle we'd have to do something fancier
		public void display() {
			fill(0);
			stroke(0);
			rectMode(CENTER);
			rect(x,y,w,h);
		}

	}


	// The Nature of Code
	// <http://www.shiffman.net/teaching/nature>
	// Spring 2010
	// PBox2D example

	// A rectangular box
	class Box {

		// We need to keep track of a Body and a width and height
		Body body;
		float w;
		float h;

		// Constructor
		Box(float x, float y) {
			w = random(4, 16);
			h = random(4, 16);
			// Add the box to the box2d world
			makeBody(new Vec2(x, y), w, h);
		}

		// This function removes the particle from the box2d world
		public void killBody() {
			Fixture f = body.getFixtureList();
			//println(f);

			box2d.destroyBody(body);
		}

		// Is the particle ready for deletion?
		public boolean done() {
			// Let's find the screen position of the particle
			Vec2 pos = box2d.getBodyPixelCoord(body);
			// Is it off the bottom of the screen?
			if (pos.y > height+w*h) {
				killBody();
				return true;
			}
			return false;
		}

		// Drawing the box
		public void display() {
			// We look at each body and get its screen position
			Vec2 pos = box2d.getBodyPixelCoord(body);
			// Get its angle of rotation
			float a = body.getAngle();

			rectMode(CENTER);
			pushMatrix();
			translate(pos.x, pos.y);
			rotate(-a);
			fill(175);
			stroke(0);
			rect(0, 0, w, h);
			popMatrix();
		}

		// This function adds the rectangle to the box2d world
		public void makeBody(Vec2 center, float w_, float h_) {

			// Define a polygon (this is what we use for a rectangle)
			PolygonShape sd = new PolygonShape();
			float box2dW = box2d.scalarPixelsToWorld(w_/2);
			float box2dH = box2d.scalarPixelsToWorld(h_/2);
			sd.setAsBox(box2dW, box2dH);

			// Define a fixture
			FixtureDef fd = new FixtureDef();
			fd.shape = sd;
			// Parameters that affect physics
			fd.density = 1;
			fd.friction = 0.3f;
			fd.restitution = 0.5f;

			// Define the body and make it from the shape
			BodyDef bd = new BodyDef();
			bd.type = BodyType.DYNAMIC;
			bd.position.set(box2d.coordPixelsToWorld(center));
			
			CircleShape cs = new CircleShape();
			   			
			DistanceJointDef djd;
			
			
			

			body = box2d.createBody(bd);
			body.createFixture(fd);
			//body.setMassFromShapes();

			// Give it some initial random velocity
			body.setLinearVelocity(new Vec2(random(-5, 5), random(2, 5)));
			body.setAngularVelocity(random(-5, 5));
		}
	}


	static public void main(String args[]) {
		PApplet.main(new String[] { "QuickTest" });
	}
}
