import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import processing.core.PApplet;

 public class Test2 extends Frame {

     public Test2() {
         super("Embedded PApplet");

         setLayout(new BorderLayout());
         final PApplet embed = new Embedded();
         add(embed, BorderLayout.CENTER);
         embed.init();
         embed.setSize(400,400);
         embed.setMinimumSize(new Dimension(400,400));
         pack();
         addWindowListener(new WindowAdapter(){
          	  public void windowClosing(WindowEvent we){
          		embed.destroy();
          	    dispose();
          	  }
          	});
     }
     
     public static void main(String args[])
     {
    	 final Test2 t = new Test2();
    	 t.setVisible(true);
    	    	 
     }
 }
