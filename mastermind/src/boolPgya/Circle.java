package boolPgya;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Circle {
 private int x=0;
 private int y=0;
 private int diameter;
 private Color color;
	

 
 public Circle(int diameter, Color color) {
	 this.diameter = diameter;
     this.color=color;

 
 }

// פעולה לציור העיגול
 public void draw(Graphics g) {
	 Graphics2D g2d = (Graphics2D) g;  
	    g2d.setStroke(new BasicStroke(3));
	    g2d.setColor(Color.BLACK);
	    g2d.drawOval(x, y, diameter, diameter);
	    g2d.setColor(color);
	    g2d.fillOval(x, y, diameter, diameter); // מצייר את העיגול
 }
 // שינוי מקום העיגול	
 public void setPosition(int x, int y) {
     this.x = x;
     this.y = y;
 }
 
 
 // שינוי צבע
 public void setColor(Color newColor) {
     this.color = newColor;
     
 }
 
 // מתודה לשינוי קוטר העיגול שבעצם מדובר בגודל שלו כי בעיגול אורך ורוחב שווים והם שווים לקוטר
public void setSizeOfDiameter(int newDiameter) {
	this.diameter= newDiameter;
}

 
}





//public void updateCircleDiameter(int newDiameter) {
 //   circle.setDiameter(newDiameter); // מעדכנים את הקוטר של העיגול
 //   repaint(); // קוראים ל-repaint כדי לעדכן את הציור על המסך
//}






