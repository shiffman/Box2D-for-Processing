// The Nature of Code
// <http://www.shiffman.net/teaching/nature>
// Spring 2010
// PBox2D example

// Basic example of falling rectangles

package pbox2d.examples.falling;


import java.util.ArrayList;


import pbox2d.PBox2D;
import processing.core.PApplet;
import util.ProcessingObject;

public class Boxes extends PApplet {

	// A reference to our box2d world
	static PBox2D box2d;

	// A list we'll use to track fixed objects
	ArrayList boundaries;
	// A list for all of our rectangles
	ArrayList boxes;

	public void setup() {
		size(400,300);
		smooth();

		ProcessingObject.setPApplet(this);

		// Initialize box2d physics and create the world
		box2d = new PBox2D(this);
		box2d.createWorld();
		// We are setting a custom gravity
		box2d.setGravity(0, -20);
		
		// Create ArrayLists	
		boxes = new ArrayList();
		boundaries = new ArrayList();
		
		// Add a bunch of fixed boundaries
		boundaries.add(new Boundary(width/4,height-5,width/2-50,10));
		boundaries.add(new Boundary(3*width/4,height-5,width/2-50,10));
		boundaries.add(new Boundary(width-5,height/2,10,height));
		boundaries.add(new Boundary(5,height/2,10,height));
	}

	public void draw() {
		background(255);
		
		// We must always step through time!
		box2d.step();
		
		// When the mouse is clicked, add a new Box object
		if (mousePressed) {
			Box p = new Box(mouseX,mouseY);
			boxes.add(p);
		}
		
		// Display all the boundaries
		for (int i = 0; i < boundaries.size(); i++) {
			Boundary wall = (Boundary) boundaries.get(i);
			wall.display();
		}

		// Display all the boxes
		for (int i = 0; i < boxes.size(); i++) {
			Box p = (Box) boxes.get(i);
			p.display();
		}
		
		// Boxes that leave the screen, we delete them
		// (note they have to be deleted from both the box2d world and our list
		for (int i = boxes.size()-1; i >= 0; i--) {
			Box p = (Box) boxes.get(i);
			if (p.done()) {
				boxes.remove(i);
			}
		}
	}
	
	/**** Standard Run ****/

	static public void main(String _args[]) {
		PApplet.main(new String[] { myClassName });
	}

	protected static String myClassName = getQualifiedClassName();

	public static String getQualifiedClassName() {
		return new Exception().getStackTrace()[1].getClassName();
	}


}
