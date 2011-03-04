// The Nature of Code
// <http://www.shiffman.net/teaching/nature>
// Spring 2010
// PBox2D example

// Class to describe the spring joint (displayed as a line)

package pbox2d.examples.mouse;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.joints.MouseJoint;
import org.jbox2d.dynamics.joints.MouseJointDef;

import util.ProcessingObject;

public class Spring extends ProcessingObject {
	
	// This is the box2d object we need to create
	MouseJoint mouseJoint;

	Spring() {
		// At first it doesn't exist
		mouseJoint = null;
	}

	// If it exists we set its target to the mouse location 
	void update(float x, float y) {
		if (mouseJoint != null) {
			// Always convert to world coordinates!
			Vec2 mouseWorld = TossSpring.box2d.coordPixelsToWorld(x,y);
			mouseJoint.setTarget(mouseWorld);
		}
	}

	void display() {
		if (mouseJoint != null) {
			// We can get the two anchor points
			Vec2 v1 = mouseJoint.getAnchor1();
			Vec2 v2 = mouseJoint.getAnchor2();
			// Convert them to screen coordinates
			v1 = TossSpring.box2d.coordWorldToPixels(v1);
			v2 = TossSpring.box2d.coordWorldToPixels(v2);
			// And just draw a line
			p.stroke(0);
			p.strokeWeight(1);
			p.line(v1.x,v1.y,v2.x,v2.y);
		}
	}


	// This is the key function where
	// we attach the spring to an x,y location
	// and the Box object's location
	void bind(float x, float y, Box box) {
		// Define the joint
		MouseJointDef md = new MouseJointDef();
		// Body 1 is just a fake ground body for simplicity (there isn't anything at the mouse)
		md.body1 = TossSpring.box2d.world.getGroundBody();
		// Body 2 is the box's boxy
		md.body2 = box.body;
		// Get the mouse location in world coordinates
		Vec2 mp = TossSpring.box2d.coordPixelsToWorld(x,y);
		// And that's the target
		md.target.set(mp);
		// Some stuff about how strong and bouncy the spring should be
		md.maxForce = 1000.0f * box.body.m_mass;
		md.frequencyHz = 5.0f;
		md.dampingRatio = 0.9f;
		
		// Wake up body!
		box.body.wakeUp();
		
		// Make the joint!
		mouseJoint = (MouseJoint) TossSpring.box2d.world.createJoint(md);
	}

	void destroy() {
		// We can get rid of the joint when the mouse is released
		if (mouseJoint != null) {
			TossSpring.box2d.world.destroyJoint(mouseJoint);
			mouseJoint = null;
		}
	}

}
