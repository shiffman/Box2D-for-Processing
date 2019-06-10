package shiffman.box2d;

import java.lang.reflect.Method;


import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

import processing.core.PApplet;

public class Box2DContactListener implements ContactListener {
	PApplet parent;
	
	Method beginMethod;
	Method endMethod;
	Method postMethod;
	Method preMethod;
	
	Box2DContactListener(PApplet p){
		parent = p;
		
		try {
			beginMethod = parent.getClass().getMethod("beginContact", new Class[] { Contact.class });
        } catch (Exception e) {
            System.out.println("You are missing the beginContact() method. " + e);
        }

		try {
			endMethod = parent.getClass().getMethod("endContact", new Class[] { Contact.class });
        } catch (Exception e) {
            System.out.println("You are missing the endContact() method. " + e);
        }
		try {
			postMethod = parent.getClass().getMethod("postSolve", new Class[] { Contact.class, ContactImpulse.class });
        } catch (Exception e) {
            //System.out.println("You are missing the postSolve() method. " + e);
        }
		try {
			preMethod = parent.getClass().getMethod("preSolve", new Class[] { Contact.class, Manifold.class });
        } catch (Exception e) {
            //System.out.println("You are missing the preSolve() method. " + e);
        }
	}





	public void beginContact(Contact c) {
        if (beginMethod != null) {
            try {
            	beginMethod.invoke(parent, new Object[] { c });
            } catch (Exception e) {
                System.out.println("Could not invoke the \"beginContact()\" method for some reason.");
                e.printStackTrace();
                beginMethod = null;
            }
        }
		
	}

	public void endContact(Contact c) {
        if (endMethod != null) {
            try {
            	endMethod.invoke(parent, new Object[] { c });
            } catch (Exception e) {
                System.out.println("Could not invoke the \"removeContact()\" method for some reason.");
                e.printStackTrace();
                endMethod = null;
            }
        }
		
	}

	public void postSolve(Contact c, ContactImpulse ci) {
        if (postMethod != null) {
            try {
            	postMethod.invoke(parent, new Object[] { c,ci});
            } catch (Exception e) {
                System.out.println("Could not invoke the \"postSolve()\" method for some reason.");
                e.printStackTrace();
                postMethod = null;
            }
        }
	}
	

	public void preSolve(Contact c, Manifold m) {
        if (preMethod != null) {
            try {
            	preMethod.invoke(parent, new Object[] { c,m});
            } catch (Exception e) {
                System.out.println("Could not invoke the \"preSolve()\" method for some reason.");
                e.printStackTrace();
                preMethod = null;
            }
        }		
	}

}
