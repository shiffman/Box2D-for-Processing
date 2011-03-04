// The Nature of Code
// <http://www.shiffman.net/teaching/nature>
// Spring 2010
// PBox2D example

// Basic example of controlling an object with the mouse (by attaching a spring)

package pbox2d.examples.mouse;

import java.util.ArrayList;

import pbox2d.PBox2D;
import processing.core.PApplet;
import util.ProcessingObject;

public class TossSpring extends PApplet {

	// A reference to our box2d world
	static PBox2D box2d;

	// A list we'll use to track fixed objects
	ArrayList boundaries;
	
	// Just a single box this time
	Box box;
	
	// The Spring that will attach to the box from the mouse
	Spring spring;

	public void setup() {
		size(400,300);
		smooth();

		ProcessingObject.setPApplet(this);

		// Initialize box2d physics and create the world
		box2d = new PBox2D(this);
		box2d.createWorld();

		// Make the box
		box = new Box(width/2,height/2);
		
		// Make the spring (it doesn't really get initialized until the mouse is clicked)
		spring = new Spring();

		// Add a bunch of fixed boundaries
		boundaries = new ArrayList();
		boundaries.add(new Boundary(width/2,height-5,width,10));
		boundaries.add(new Boundary(width/2,5,width,10));
		boundaries.add(new Boundary(width-5,height/2,10,height));
		boundaries.add(new Boundary(5,height/2,10,height));
	}

	// When the mouse is released we're done with the spring
	public void mouseReleased() {
		spring.destroy();
	}

	// When the mouse is pressed we. . .
	public void mousePressed() {
		// Check to see if the mouse was clicked on the box
		if (box.contains(mouseX, mouseY)) {
			// And if so, bind the mouse location to the box with a spring
			spring.bind(mouseX,mouseY,box);
		}
	}

	public void draw() {
		background(255);
		
		// We must always step through time!
		box2d.step();

		// Always alert the spring to the new mouse location
		spring.update(mouseX,mouseY);

		// Draw the boundaries
		for (int i = 0; i < boundaries.size(); i++) {
			Boundary wall = (Boundary) boundaries.get(i);
			wall.display();
		}

		// Draw the box
		box.display();
		// Draw the spring (it only appears when active)
		spring.display();
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
