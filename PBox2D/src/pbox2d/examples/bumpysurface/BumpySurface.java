// The Nature of Code
// <http://www.shiffman.net/teaching/nature>
// Spring 2010
// PBox2D example

// An uneven surface

package pbox2d.examples.bumpysurface;

import java.util.ArrayList;

import pbox2d.PBox2D;
import processing.core.PApplet;
import processing.core.PFont;
import util.ProcessingObject;

public class BumpySurface extends PApplet {

	// A reference to our box2d world
	static PBox2D box2d;

	// An ArrayList of particles that will fall on the surface
	ArrayList particles;
	
	// An object to store information about the uneven surface
	Surface surface;
	
	PFont f;

	public void setup() {
		size(500,300);
		frameRate(60);
		smooth();
		
		f = createFont("Arial",12,true);
		ProcessingObject.setPApplet(this);
		
		// Initialize box2d physics and create the world
		box2d = new PBox2D(this);
		box2d.createWorld();
		// We are setting a custom gravity
		box2d.setGravity(0, -20);
		
		// Create the empty list
		particles = new ArrayList();
		// Create the surface
		surface = new Surface();
	}

	public void draw() {
		// If the mouse is pressed, we make new particles
		if (mousePressed) {
			float sz = random(2,6);
			particles.add(new Particle(mouseX,mouseY,sz));
		}

		// We must always step through time!
		box2d.step();

		background(255);

		// Draw the surface
		surface.display();

		// Draw all particles
		for (int i = 0; i < particles.size(); i++) {
			Particle p = (Particle) particles.get(i);
			p.display();
		}

		// Particles that leave the screen, we delete them
		// (note they have to be deleted from both the box2d world and our list
		for (int i = particles.size()-1; i >= 0; i--) {
			Particle p = (Particle) particles.get(i);
			if (p.done()) {
				particles.remove(i);
			}
		}
		
		// Just drawing the framerate to see how many particles it can handle
		textFont(f);
		fill(0);
		text("framerate: " + (int)frameRate,12,16);
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
