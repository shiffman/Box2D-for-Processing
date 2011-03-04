// The Nature of Code
// <http://www.shiffman.net/teaching/nature>
// Spring 2010
// PBox2D example

// Box2D particle system example

package pbox2d.examples.liquid;


import java.util.ArrayList;


import pbox2d.PBox2D;
import processing.core.PApplet;
import processing.core.PVector;
import util.ProcessingObject;

public class Liquid extends PApplet {

	// A reference to our box2d world
	static PBox2D box2d;

	// A list we'll use to track fixed objects
	ArrayList boundaries;
	
	// A list for all particle systems
	ArrayList systems;

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
		systems = new ArrayList();
		boundaries = new ArrayList();

		// Add a bunch of fixed boundaries
		boundaries.add(new Boundary(50,100,300,5,-0.3f));
		boundaries.add(new Boundary(250,175,300,5,0.5f));
		
	}

	public void draw() {
		background(255);
		
		// We must always step through time!
		box2d.step();
		
		// Run all the particle systems
		for (int i = 0; i < systems.size(); i++) {
			ParticleSystem system = (ParticleSystem) systems.get(i);
			system.run();
			
			int n = (int) random(0,2);
			system.addParticles(n);
		}

		// Display all the boundaries
		for (int i = 0; i < boundaries.size(); i++) {
			Boundary wall = (Boundary) boundaries.get(i);
			wall.display();
		}
	}
	
	
	public void mousePressed() {
		// Add a new Particle System whenever the mouse is clicked
		systems.add(new ParticleSystem(0, new PVector(mouseX,mouseY)));
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
