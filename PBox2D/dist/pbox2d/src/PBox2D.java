/**
 * PBox2d
 * This is a simple little wrapper to help integrate JBox2d with Processing
 * It doesn't do much right now and, in some ways, limits the user
 * It's an open question as to whether this should really be a library
 * or a set of examples. Right now, it's a little bit of both
 * Daniel Shiffman <http://www.shiffman.net>
 */

package pbox2d;

import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.common.XForm;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

import processing.core.PApplet;

public class PBox2D {

	PApplet parent;

	// The Box2D world
	public World world;

	// Variables to keep track of translating between world and screen coordinates
	public float transX;// = 320.0f;
	public float transY;// = 240.0f;
	public float scaleFactor;// = 10.0f;
	public float yFlip;// = -1.0f; //flip y coordinate

	// Construct with a default scaleFactor of 10
	public PBox2D(PApplet p) {
		this(p,10);
	}

	public PBox2D(PApplet p, float sf) {
		parent = p;
		transX = parent.width/2;
		transY = parent.height/2;
		scaleFactor = sf;
		yFlip = -1;
	}

	// Change the scaleFactor
	public void setScaleFactor(float scale) {
		scaleFactor = scale;
	}

	// This is the all important physics "step" function
	// Says to move ahead one unit in time
	// Default
	public void step() {
		float timeStep = 1.0f / 60f;
		this.step(timeStep,10);
	}
	
	// Custom
	public void step(float timeStep, int iterationCount) {
		this.step(true,true,true,timeStep, iterationCount);
	}
	
	// More custom
	public void step(boolean starting, boolean correction, boolean continuous, float timeStep, int iterationCount) {
		world.setWarmStarting(starting);
		world.setPositionCorrection(correction);
		world.setContinuousPhysics(continuous);
		world.step(timeStep, iterationCount);
	}
	
	

	// Create a default world
	public void createWorld() {
		createWorld(-100,-100,100,100);
	}

	// Slightly more custom world
	// These values define how much we are looking at?
	public void createWorld(float lx,float ly, float ux, float uy) {
		AABB worldAABB = new AABB();
		worldAABB.lowerBound.set(lx, ly);
		worldAABB.upperBound.set(ux, ux);
		Vec2 gravity = new Vec2(0.0f, -10.0f);
		boolean doSleep = true;
		world = new World(worldAABB, gravity, doSleep);
	}

	// Set the gravity (this can change in real-time)
	public void setGravity(float x, float y) {
		world.setGravity(new Vec2(x,y));
	}
	
	// These functions are very important
	// Box2d has its own coordinate system and we have to move back and forth between them
	// convert from Box2d world to screen world
	public Vec2 worldToScreen(Vec2 world) {
		float x = PApplet.map(world.x, 0f, 1f, transX, transX+scaleFactor);
		float y = PApplet.map(world.y, 0f, 1f, transY, transY+scaleFactor);
		if (yFlip == -1.0f) y = PApplet.map(y,0f,parent.height, parent.height,0f);
		return new Vec2(x, y);
	}
	
	public Vec2 worldToScreen(float x, float y) {
		return worldToScreen(new Vec2(x,y));
	}

	// convert from screen world to box2d world
	public Vec2 screenToWorld(Vec2 screen) {
		float x = PApplet.map(screen.x, transX, transX+scaleFactor, 0f, 1f);
		float y = screen.y;
		if (yFlip == -1.0f) y = PApplet.map(y,parent.height,0f,0f,parent.height);
		y = PApplet.map(y, transY, transY+scaleFactor, 0f, 1f);
		return new Vec2(x,y);
	}

	public Vec2 screenToWorld(float x, float y) {
		return screenToWorld(new Vec2(x,y));
	}

	// Scale between worlds
	public float scaleScreenToWorld(float val) {
		return val / scaleFactor;
	}

	public float scaleWorldToScreen(float val) {
		return val * scaleFactor;
	}

	// A common task we have to do a lot
	public Body createBody(BodyDef bd) {
		return world.createBody(bd);
	}
	
	// Another common task, find the position of a body
	// so that we can draw it
	public Vec2 getScreenPos(Body b) {
		XForm xf = b.getXForm();
		return worldToScreen(xf.position); 
	}

	public void destroyBody(Body b) {
		world.destroyBody(b);
	}



}




