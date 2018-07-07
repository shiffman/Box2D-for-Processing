/**
 * Box2D for Processing
 * This is a simple little wrapper to help integrate JBox2d with Processing
 * It doesn't do much right now and, in some ways, limits the user
 * It's an open question as to whether this should really be a library
 * or a set of examples. Right now, it's a little bit of both
 * Daniel Shiffman <http://www.shiffman.net>
**/

package shiffman.box2d;

import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.JointDef;

import processing.core.PApplet;
import processing.core.PVector;

public class Box2DProcessing {

	// Processing environment
	PApplet parent;

	// The Box2D world
	public World world;

	// Variables to keep track of translating between world and screen coordinates
	public float transX;// = 320.0f;
	public float transY;// = 240.0f;
	public float scaleFactor;// = 10.0f;
	public float yFlip;// = -1.0f; //flip y coordinate

	// A variable to store an optional ground body
	Body groundBody;
	
	// Reference to a Contact Listener
	Box2DContactListener contactlistener;

	// Construct with a default scaleFactor of 10
	public Box2DProcessing(PApplet p) {
		this(p,10);
	}

	public Box2DProcessing(PApplet p, float sf) {
		parent = p;
		transX = parent.width/2;
		transY = parent.height/2;
		scaleFactor = sf;
		yFlip = -1;
		
	}
	
	public void listenForCollisions() {
		contactlistener = new Box2DContactListener(parent);
		world.setContactListener(contactlistener);
	}
	
	// Change the scaleFactor
	public void setScaleFactor(float scale) {
		scaleFactor = scale;
	}

	// This is the all important physics "step" function, it moves the world forward one step in time
	// The default delta time is 1/60th of a second
	public void step() {
		float timeStep = 1.0f / 60f;
		this.step(timeStep,10,8);
		world.clearForces();
	}
	
	// A custom step function
	public void step(float timeStep, int velocityIterations, int positionIterations) {
		world.step(timeStep, velocityIterations, positionIterations);
	}
	
	// Eases in forces to the world, eliminating odd behaviour at the start
	public void setWarmStarting(boolean b) {
		world.setWarmStarting(b);
	}
	
	// If true, does not skip any physics calculations
	public void setContinuousPhysics(boolean b) {
		world.setContinuousPhysics(b);
	}

	// Create a default world with default gravity of -9.81f (downwards)
	public void createWorld() {
		Vec2 gravity = new Vec2(0.0f, -9.81f);
		createWorld(gravity);
		setWarmStarting(true);
		setContinuousPhysics(true);
	}
	
	// Create a world with a custom gravity, can be 0
	public void createWorld(Vec2 gravity) {
		createWorld(gravity,true,true);
	}
	
	// Create a completely custom world
	public void createWorld(Vec2 gravity, boolean warmStarting, boolean continuous) {
		world = new World(gravity);
		setWarmStarting(warmStarting);
		setContinuousPhysics(continous);
		
	    BodyDef bodyDef = new BodyDef();
	    groundBody = world.createBody(bodyDef);
	}
	
	// Returns the ground body.
	public Body getGroundBody() {
		return groundBody;
	}	

	// Set the gravity (this can change in real-time)
	public void setGravity(float x, float y) {
		world.setGravity(new Vec2(x,y));
	}
	
	// These functions are very important
	// Box2d has its own coordinate system and we have to move back and forth between them
	
	
	// Convert from the Box2d world to pixel space
	
	public Vec2 coordWorldToPixels(Vec2 world) {
		return coordWorldToPixels(world.x,world.y);
	}
	
	public PVector coordWorldToPixelsPVector(Vec2 world) {
		Vec2 v = coordWorldToPixels(world.x,world.y);
		return new PVector(v.x,v.y);
	}
	
	public Vec2 coordWorldToPixels(float worldX, float worldY) {
		float pixelX = PApplet.map(worldX, 0f, 1f, transX, transX+scaleFactor);
		float pixelY = PApplet.map(worldY, 0f, 1f, transY, transY+scaleFactor);
		if (yFlip == -1.0f) pixelY = PApplet.map(pixelY,0f,parent.height, parent.height,0f);
		return new Vec2(pixelX, pixelY);
	}
	

	// Convert from pixel space to the Box2D world
	
	public Vec2 coordPixelsToWorld(Vec2 screen) {
		return coordPixelsToWorld(screen.x,screen.y);
	}
	
	public Vec2 coordPixelsToWorld(PVector screen) {
		return coordPixelsToWorld(screen.x,screen.y);
	}

	public Vec2 coordPixelsToWorld(float pixelX, float pixelY) {
		float worldX = PApplet.map(pixelX, transX, transX+scaleFactor, 0f, 1f);
		float worldY = pixelY;
		if (yFlip == -1.0f) worldY = PApplet.map(pixelY,parent.height,0f,0f,parent.height);
		worldY = PApplet.map(worldY, transY, transY+scaleFactor, 0f, 1f);
		return new Vec2(worldX,worldY);
	}

	
	// Scale scalar quantity between worlds
	
	public float scalarPixelsToWorld(float val) {
		return val / scaleFactor;
	}

	public float scalarWorldToPixels(float val) {
		return val * scaleFactor;
	}
	
	
	// Scale vector between worlds
	
	public Vec2 vectorPixelsToWorld(Vec2 v) {
		Vec2 u = new Vec2(v.x/scaleFactor,v.y/scaleFactor);
		u.y *=  yFlip;
		return u;
	}
	
	public Vec2 vectorPixelsToWorld(PVector v) {
		Vec2 u = new Vec2(v.x/scaleFactor,v.y/scaleFactor);
		u.y *=  yFlip;
		return u;
	}
	
	public Vec2 vectorPixelsToWorld(float x, float y) {
		Vec2 u = new Vec2(x/scaleFactor,y/scaleFactor);
		u.y *=  yFlip;
		return u;
	}

	public Vec2 vectorWorldToPixels(Vec2 v) {
		Vec2 u = new Vec2(v.x*scaleFactor,v.y*scaleFactor);
		u.y *=  yFlip;
		return u;
	}
	
	public PVector vectorWorldToPixelsPVector(Vec2 v) {
		PVector u = new PVector(v.x*scaleFactor,v.y*scaleFactor);
		u.y *=  yFlip;
		return u;
	}
	
	
	// Common tasks
	
	public Body createBody(BodyDef bd) {
		return world.createBody(bd);
	}
	
	public Joint createJoint(JointDef jd) {
		return world.createJoint(jd);
	}
	
	// Find the position of a body in the Box2D world so that it can be drawn in the pixel world
	
	public Vec2 getBodyPixelCoord(Body b) {
		Transform xf = b.getTransform();
		return coordWorldToPixels(xf.p); 
	}
	
	public PVector getBodyPixelCoordPVector(Body b) {
		Transform xf = b.getTransform();
		return coordWorldToPixelsPVector(xf.p); 
	}

	public void destroyBody(Body b) {
		world.destroyBody(b);
	}
}
