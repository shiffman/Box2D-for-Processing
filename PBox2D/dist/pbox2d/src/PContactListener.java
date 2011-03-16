package pbox2d;

import java.lang.reflect.Method;


import org.jbox2d.dynamics.ContactListener;
import org.jbox2d.dynamics.contacts.ContactPoint;
import org.jbox2d.dynamics.contacts.ContactResult;

import processing.core.PApplet;

public class PContactListener implements ContactListener {
	PApplet parent;
	
	Method addMethod;
	Method persistMethod;
	Method removeMethod;
	Method resultMethod;
	
	PContactListener(PApplet p){
		parent = p;
		
		try {
			addMethod = parent.getClass().getMethod("addContact", new Class[] { ContactPoint.class });
        } catch (Exception e) {
            System.out.println("You are missing the addContact() method. " + e);
        }

		try {
			persistMethod = parent.getClass().getMethod("persistContact", new Class[] { ContactPoint.class });
        } catch (Exception e) {
            System.out.println("You are missing the persistContact() method. " + e);
        }
		try {
			removeMethod = parent.getClass().getMethod("removeContact", new Class[] { ContactPoint.class });
        } catch (Exception e) {
            System.out.println("You are missing the removeContact() method. " + e);
        }
		try {
			resultMethod = parent.getClass().getMethod("resultContact", new Class[] { ContactResult.class });
        } catch (Exception e) {
            System.out.println("You are missing the resultContact() method. " + e);
        }

        

	}

	// This function is called when a new collision occurs
	public void add(ContactPoint cp) {
        if (addMethod != null) {
            try {
            	addMethod.invoke(parent, new Object[] { cp });
            } catch (Exception e) {
                System.out.println("Could not invoke the \"addContact()\" method for some reason.");
                e.printStackTrace();
                addMethod = null;
            }
        }
	}


	 // Contacts continue to collide - i.e. resting on each other
	public void persist(ContactPoint cp)  {
        if (persistMethod != null) {
            try {
            	persistMethod.invoke(parent, new Object[] { cp });
            } catch (Exception e) {
                System.out.println("Could not invoke the \"persistContact()\" method for some reason.");
                e.printStackTrace();
                persistMethod = null;
            }
        }
	}

	// Objects stop touching each other
	public void remove(ContactPoint cp)  {
        if (removeMethod != null) {
            try {
            	removeMethod.invoke(parent, new Object[] { cp });
            } catch (Exception e) {
                System.out.println("Could not invoke the \"removeContact()\" method for some reason.");
                e.printStackTrace();
                removeMethod = null;
            }
        }
	}

	// Contact point is resolved into an add, persist etc
	public void result(ContactResult cr) {
        if (resultMethod != null) {
            try {
            	resultMethod.invoke(parent, new Object[] { cr });
            } catch (Exception e) {
                System.out.println("Could not invoke the \"resultContact()\" method for some reason.");
                e.printStackTrace();
                resultMethod = null;
            }
        }

	}


}
