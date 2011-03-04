// The Nature of Code
// <http://www.shiffman.net/teaching/nature>
// Spring 2010
// PBox2D example

// An uneven surface boundary

package pbox2d.examples.bumpysurface;

import java.util.ArrayList;

import org.jbox2d.collision.shapes.EdgeChainDef;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;

import util.ProcessingObject;

public class Surface extends ProcessingObject {
	// We'll keep track of all of the surface points
	ArrayList surface;
	
	
	Surface() {
		surface = new ArrayList();
		
		// This is what box2d uses to put the surface in its world
		EdgeChainDef edges = new EdgeChainDef();
    	
		// Perlin noise argument
    	float xoff = 0.0f;
    	
    	// This has to go backwards so that the objects  bounce off the top of the surface
    	// This "edgechain" will only work in one direction!
    	for (float x = p.width+10; x > -10; x -= 5) {
    		
    		// Doing some stuff with perlin noise to calculate a surface that points down on one side
    		// and up on the other
    		float y;
    		if (x > p.width/2) {
    			y = 100 + (p.width - x)*1.1f + p.map(p.noise(xoff),0,1,-80,80);
    		} else {
    			y = 100 + x*1.1f + p.map(p.noise(xoff),0,1,-80,80);
    		}
    		
    		// The edge point in our window
    		Vec2 screenEdge = new Vec2(x,y);
    		// We store it for rendering
    		surface.add(screenEdge);
    		
    		// Convert it to the box2d world and add it to our EdgeChainDef
    		Vec2 edge = BumpySurface.box2d.coordPixelsToWorld(screenEdge);
    		edges.addVertex(edge);
    		
    		// Move through perlin noise
    		xoff += 0.1;

    	}
    	edges.setIsLoop(false);   // We could make the edge a full loop
    	edges.friction = 2.0f;    // How much friction
    	edges.restitution = 0.3f; // How bouncy
    	
    	// The edge chain is now a body!
		BodyDef bd = new BodyDef();
		bd.position.set(0.0f,0.0f);
		Body body = BumpySurface.box2d.world.createBody(bd);
		body.createShape(edges);

	}
	
	// A simple function to just draw the edge chain as a series of vertex points
	void display() {
		p.strokeWeight(2);
		p.stroke(0);
		p.noFill();
		p.beginShape();
		for (int i = 0; i < surface.size(); i++) {
			Vec2 v = (Vec2) surface.get(i);
			p.vertex(v.x,v.y);
		}
		p.endShape();
	}

}
