// The Nature of Code
// <http://www.shiffman.net/teaching/nature>
// Spring 2010
// PBox2D example

// A blob skeleton
// Could be used to create blobbly characters a la Nokia Friends
// http://postspectacular.com/work/nokia/friends/start

package pbox2d.examples.blob;

import java.util.ArrayList;

import org.jbox2d.collision.shapes.CircleDef;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.joints.ConstantVolumeJointDef;

import processing.core.PApplet;
import util.ProcessingObject;

public class Blob extends ProcessingObject {
	
	// A list to keep track of all the points in our blob
	ArrayList skeleton;
	
	float bodyRadius;  // The radius of each body that makes up the skeleton
	float radius;      // The radius of the entire blob
	float totalPoints; // How many points make up the blob


	// We should modify this constructor to receive arguments
	// So that we can make many different types of blobs
	Blob() {
		
		// Create the empty 
		skeleton = new ArrayList();

		// Let's make a volume of joints!
		ConstantVolumeJointDef cvjd = new ConstantVolumeJointDef();

		// Where and how big is the blob
		Vec2 center = new Vec2(p.width/2,p.height/2);
		radius = 100;
		totalPoints = 20;
		bodyRadius = 12;


		// Initialize all the points
		for (int i = 0; i < totalPoints; i++) {
			// Look polar to cartesian coordinate transformation!
			float theta = PApplet.map(i, 0, totalPoints, 0, p.TWO_PI);
			float x = center.x + radius * p.sin(theta);
			float y = center.y + radius * p.cos(theta);

			// Make each individual body
			BodyDef bd = new BodyDef();
			bd.fixedRotation = true; // no rotation!
			bd.position.set(Blobby.box2d.coordPixelsToWorld(x,y));
			Body body = Blobby.box2d.createBody(bd);

			// The body is a circle
			CircleDef cd = new CircleDef();
			cd.radius = Blobby.box2d.scalarPixelsToWorld(bodyRadius);
			cd.density = 1.0f;
			cd.filter.groupIndex = -2; // For filtering out collisions

			// Finalize the body
			body.createShape(cd);
			// Add it to the volume
			cvjd.addBody(body);
			// We always do this at the end
			body.setMassFromShapes();
			
			// Store our own copy for later rendering
			skeleton.add(body);
		}

		// These parameters control how stiff vs. jiggly the blob is
		cvjd.frequencyHz = 10.0f;
		cvjd.dampingRatio = 1.0f;

		// Put the joint thing in our world!
		Blobby.box2d.world.createJoint(cvjd);

	}
	

	// Time to draw the blob!
	// Can you make it a cute character, a la http://postspectacular.com/work/nokia/friends/start
	void display() {

		// Draw the outline
		p.beginShape();
		p.noFill();
		p.stroke(0);
		p.strokeWeight(1);
		for (int i = 0; i < skeleton.size(); i++) {
			// We look at each body and get its screen position
			Body b = (Body) skeleton.get(i);
			Vec2 pos = Blobby.box2d.getBodyPixelCoord(b);
			p.vertex(pos.x,pos.y);
		}
		p.endShape(p.CLOSE);

		// Draw the individual circles
		for (int i = 0; i < skeleton.size(); i++) {
			Body b = (Body) skeleton.get(i);
			// We look at each body and get its screen position
			Vec2 pos = Blobby.box2d.getBodyPixelCoord(b);
			// Get its angle of rotation
			float a = b.getAngle();
			p.pushMatrix();
			p.translate(pos.x,pos.y);
			p.rotate(a);
			p.fill(175);
			p.stroke(0);
			p.strokeWeight(1);
			p.ellipse(0,0,bodyRadius*2,bodyRadius*2);
			p.popMatrix();
		}

	}






}
