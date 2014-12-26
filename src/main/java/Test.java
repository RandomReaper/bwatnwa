import processing.core.*;

public class Test extends PApplet {

	public void setup() {
		size(640, 360, P3D); 
		background(0);
		lights();

		noStroke();
		pushMatrix();
		translate(130, height/2, 0);
		rotateY((float) 1.25);
		rotateX((float) -0.4);
		box(100);
		popMatrix();

		noFill();
		stroke(255);
		pushMatrix();
		translate(500, (float) (height*0.35), -200);
		sphere(280);
		popMatrix();
	}

	public void draw() {
		stroke(255);
		if (mousePressed) {
			line(mouseX, mouseY, pmouseX, pmouseY);
		}
	}

	public static void main(String args[]) {
		PApplet.main(new String[] { /*"--present",*/ "Test" });
	}

}