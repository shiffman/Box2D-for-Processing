package util;


import processing.core.PApplet;

public class ProcessingObject {

	protected static PApplet p;


	public static PApplet getPApplet() {
		return p;
	}

	public static void setPApplet(PApplet applet) {
		p = applet;
	}
	

}
