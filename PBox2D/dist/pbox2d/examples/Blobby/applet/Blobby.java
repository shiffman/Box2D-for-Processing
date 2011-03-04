import processing.core.*; 
import processing.xml.*; 

import pbox2d.*; 
import org.jbox2d.collision.shapes.*; 
import org.jbox2d.common.*; 
import org.jbox2d.dynamics.*; 
import org.jbox2d.dynamics.joints.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class Blobby extends PApplet {

// The Nature of Code
// <http://www.shiffman.net/teaching/nature>
// Spring 2010
// PBox2D example

// A blob skeleton
// Could be used to create blobbly characters a la Nokia Friends
// http://postspectacular.com/work/nokia/friends/start








// A reference to our box2d world
PBox2D box2d;

// A list we'll use to track fixed objects
ArrayList<Boundary> boundaries;

// Our "blob" object
Blob blob;

 public void setup() {
  size(400,300);
  smooth();

  // Initialize box2d physics and create the world
  box2d = new PBox2D(this);
  box2d.createWorld();

  // Add some boundaries
  boundaries = new ArrayList<Boundary>();
  boundaries.add(new Boundary(width/2,height-5,width,10));
  boundaries.add(new Boundary(width/2,5,width,10));
  boundaries.add(new Boundary(width-5,height/2,10,height));
  boundaries.add(new Boundary(5,height/2,10,height));

  // Make a new blob
  blob = new Blob();
}

 public void draw() {
  background(255);

  // We must always step through time!
  box2d.step();

  // Show the blob!
  blob.display();

  // Show the boundaries!
  for (Boundary wall: boundaries) {
    wall.display();
  }

  // Here we create a dynamic gravity vector based on the location of our mouse
  PVector g = new PVector(mouseX-width/2,mouseY-height/2);
  g.normalize();
  g.mult(10);
  box2d.setGravity(g.x, -g.y);
}




// The Nature of Code
// <http://www.shiffman.net/teaching/nature>
// Spring 2010
// PBox2D example

// A blob skeleton
// Could be used to create blobbly characters a la Nokia Friends
// http://postspectacular.com/work/nokia/friends/start

class Blob {

  // A list to keep track of all the points in our blob
  ArrayList<Body> skeleton;

  float bodyRadius;  // The radius of each body that makes up the skeleton
  float radius;      // The radius of the entire blob
  float totalPoints; // How many points make up the blob


  // We should modify this constructor to receive arguments
  // So that we can make many different types of blobs
  Blob() {

    // Create the empty 
    skeleton = new ArrayList<Body>();

    // Let's make a volume of joints!
    ConstantVolumeJointDef cvjd = new ConstantVolumeJointDef();

    // Where and how big is the blob
    Vec2 center = new Vec2(width/2,height/2);
    radius = 100;
    totalPoints = 20;
    bodyRadius = 12;


    // Initialize all the points
    for (int i = 0; i < totalPoints; i++) {
      // Look polar to cartesian coordinate transformation!
      float theta = PApplet.map(i, 0, totalPoints, 0, TWO_PI);
      float x = center.x + radius * sin(theta);
      float y = center.y + radius * cos(theta);

      // Make each individual body
      BodyDef bd = new BodyDef();
      bd.fixedRotation = true; // no rotation!
      bd.position.set(box2d.screenToWorld(x,y));
      Body body = box2d.createBody(bd);

      // The body is a circle
      CircleDef cd = new CircleDef();
      cd.radius = box2d.scaleScreenToWorld(bodyRadius);
      cd.density = 1.0f;
      // For filtering out collisions
      cd.filter.groupIndex = -2;

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
    box2d.world.createJoint(cvjd);

  }


  // Time to draw the blob!
  // Can you make it a cute character, a la http://postspectacular.com/work/nokia/friends/start
  public void display() {

    // Draw the outline
    beginShape();
    noFill();
    stroke(0);
    strokeWeight(1);
    for (Body b: skeleton) {
      Vec2 pos = box2d.getScreenPos(b);
      vertex(pos.x,pos.y);
    }
    endShape(CLOSE);

    // Draw the individual circles
    for (Body b: skeleton) {
      // We look at each body and get its screen position
      Vec2 pos = box2d.getScreenPos(b);
      // Get its angle of rotation
      float a = b.getAngle();
      pushMatrix();
      translate(pos.x,pos.y);
      rotate(a);
      fill(175);
      stroke(0);
      strokeWeight(1);
      ellipse(0,0,bodyRadius*2,bodyRadius*2);
      popMatrix();
    }

  }






}



// The Nature of Code
// <http://www.shiffman.net/teaching/nature>
// Spring 2010
// PBox2D example

// A fixed boundary class

class Boundary {

  // A boundary is a simple rectangle with x,y,width,and height
  float x;
  float y;
  float w;
  float h;
  // But we also have to make a body for box2d to know about it
  Body b;

  Boundary(float x_,float y_, float w_, float h_) {
    x = x_;
    y = y_;
    w = w_;
    h = h_;

    // Figure out the box2d coordinates
    float box2dW = box2d.scaleScreenToWorld(w/2);
    float box2dH = box2d.scaleScreenToWorld(h/2);
    Vec2 center = new Vec2(x,y);

    // Define the polygon
    PolygonDef sd = new PolygonDef();
    sd.setAsBox(box2dW, box2dH);
    sd.density = 0;    // No density means it won't move!
    sd.friction = 0.3f;

    // Create the body
    BodyDef bd = new BodyDef();
    bd.position.set(box2d.screenToWorld(center));
    b = box2d.createBody(bd);
    b.createShape(sd);
  }

  // Draw the boundary, if it were at an angle we'd have to do something fancier
  public void display() {
    fill(0);
    stroke(0);
    rectMode(CENTER);
    rect(x,y,w,h);
  }

}


  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#FFFFFF", "Blobby" });
  }
}
