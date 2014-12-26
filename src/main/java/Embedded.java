import processing.core.PApplet;

 public class Embedded extends PApplet {

     public void setup() {
         // original setup code here ...
         size(800, 800);

         // prevent thread from starving everything else
         noLoop();
     }

     public void draw() {
 		stroke(255);
 		if (mousePressed) {
 			line(mouseX, mouseY, pmouseX, pmouseY);
 		}
     }

     public void mousePressed() {
         // do something based on mouse movement

         // update the screen (run draw once)
         redraw();
     }
 }