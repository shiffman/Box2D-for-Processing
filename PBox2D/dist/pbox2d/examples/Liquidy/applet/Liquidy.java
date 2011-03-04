import processing.core.*; 
import processing.xml.*; 

import pbox2d.*; 
import org.jbox2d.collision.shapes.*; 
import org.jbox2d.common.*; 
import org.jbox2d.dynamics.*; 

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

public class Liquidy extends PApplet {

// The Nature of Code
// <http://www.shiffman.net/teaching/nature>
// Spring 2010
// PBox2D example

// Box2D particle system example








// A reference to our box2d world
PBox2D box2d;

// A list we'll use to track fixed objects
ArrayList<Boundary> boundaries;

// A list for all particle systems
ArrayList<ParticleSystem> systems;

public void setup() {
  size(400,300);
  smooth();

  // Initialize box2d physics and create the world
  box2d = new PBox2D(this);
  box2d.createWorld();

  // We are setting a custom gravity
  box2d.setGravity(0, -20);

  // Create ArrayLists	
  systems = new ArrayList<ParticleSystem>();
  boundaries = new ArrayList<Boundary>();

  // Add a bunch of fixed boundaries
  boundaries.add(new Boundary(50,100,300,5,-0.3f));
  boundaries.add(new Boundary(250,175,300,5,0.5f));

}

public void draw() {
  background(255);

  // We must always step through time!
  box2d.step();

  // Run all the particle systems
  for (ParticleSystem system: systems) {
    system.run();

    int n = (int) random(0,2);
    system.addParticles(n);
  }

  // Display all the boundaries
  for (Boundary wall: boundaries) {
    wall.display();
  }
}


public void mousePressed() {
  // Add a new Particle System whenever the mouse is clicked
  systems.add(new ParticleSystem(0, new PVector(mouseX,mouseY)));
}





// The Nature of Code
// <http://www.shiffman.net/teaching/nature>
// Spring 2010
// PBox2D example

// A fixed boundary class (now incorporates angle)

class Boundary {

  // A boundary is a simple rectangle with x,y,width,and height
  float x;
  float y;
  float w;
  float h;
  // But we also have to make a body for box2d to know about it
  Body b;

  Boundary(float x_,float y_, float w_, float h_, float angle) {
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
    bd.angle = angle;
    b = box2d.createBody(bd);
    b.createShape(sd);
  }

  // Draw the boundary, if it were at an angle we'd have to do something fancier
  public void display() {
    noFill();
    stroke(0);
    strokeWeight(1);
    rectMode(CENTER);

    float a = b.getAngle();

    pushMatrix();
    translate(x,y);
    rotate(-a);
    rect(0,0,w,h);
    popMatrix();
  }

}


// The Nature of Code
// <http://www.shiffman.net/teaching/nature>
// Spring 2010
// PBox2D example

// A Particle

class Particle {

  // We need to keep track of a Body
  Body body;

  PVector[] trail;

  // Constructor
  Particle(float x_, float y_) {
    float x = x_;
    float y = y_;
    trail = new PVector[6];
    for (int i = 0; i < trail.length; i++) {
      trail[i] = new PVector(x,y);
    }

    // Add the box to the box2d world
    // Here's a little trick, let's make a tiny tiny radius
    // This way we have collisions, but they don't overwhelm the system
    makeBody(new Vec2(x,y),0.2f);
  }

  // This function removes the particle from the box2d world
  public void killBody() {
    box2d.destroyBody(body);
  }

  // Is the particle ready for deletion?
  public boolean done() {
    // Let's find the screen position of the particle
    Vec2 pos = box2d.getScreenPos(body);
    // Is it off the bottom of the screen?
    if (pos.y > height+20) {
      killBody();
      return true;
    }
    return false;
  }

  // Drawing the box
  public void display() {
    // We look at each body and get its screen position
    Vec2 pos = box2d.getScreenPos(body);

    // Keep track of a history of screen positions in an array
    for (int i = 0; i < trail.length-1; i++) {
      trail[i] = trail[i+1];
    }
    trail[trail.length-1] = new PVector(pos.x,pos.y);

    // Draw particle as a trail
    beginShape();
    noFill();
    strokeWeight(2);
    stroke(0,150);
    for (int i = 0; i < trail.length; i++) {
      vertex(trail[i].x,trail[i].y);
    }
    endShape();
  }

  // This function adds the rectangle to the box2d world
  public void makeBody(Vec2 center, float r) {
    // Define and create the body
    BodyDef bd = new BodyDef();
    bd.position.set(box2d.screenToWorld(center));
    body = box2d.createBody(bd);

    // Give it some initial random velocity
    body.setLinearVelocity(new Vec2(random(-1,1),random(-1,1)));

    // We'll make the shape a circle I guess (though we could have done rectangle, maybe faster?)
    CircleDef cd = new CircleDef();
    r = box2d.scaleScreenToWorld(r);
    cd.radius = r;

    // Parameters that affect physics
    cd.density = 0.1f;
    cd.friction = 0.0f;    // Slippery when wet!
    cd.restitution = 0.5f;

    // We could use this if we want to turn collisions off
    //cd.filter.groupIndex = -10;

    // Attach that shape to our body!
    body.createShape(cd);
    body.setMassFromShapes();

  }

}


// Box2D Particle System
// <http://www.shiffman.net/teaching/nature>
// Spring 2010

// A class to describe a group of Particles
// An ArrayList is used to manage the list of Particles 

class ParticleSystem  {

  ArrayList<Particle> particles;    // An ArrayList for all the particles
  PVector origin;         // An origin point for where particles are birthed

  ParticleSystem(int num, PVector v) {
    particles = new ArrayList<Particle>();             // Initialize the ArrayList
    origin = v.get();                        // Store the origin point

      for (int i = 0; i < num; i++) {
      particles.add(new Particle(origin.x,origin.y));    // Add "num" amount of particles to the ArrayList
    }
  }

  public void run() {
    // Display all the particles
    for (Particle p: particles) {
      p.display();
    }

    // Particles that leave the screen, we delete them
    // (note they have to be deleted from both the box2d world and our list
    for (int i = particles.size()-1; i >= 0; i--) {
      Particle p = particles.get(i);
      if (p.done()) {
        particles.remove(i);
      }
    }
  }

  public void addParticles(int n) {
    for (int i = 0; i < n; i++) {
      particles.add(new Particle(origin.x,origin.y));
    }
  }

  // A method to test if the particle system still has particles
  public boolean dead() {
    if (particles.isEmpty()) {
      return true;
    } 
    else {
      return false;
    }
  }

}





  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#FFFFFF", "Liquidy" });
  }
}
