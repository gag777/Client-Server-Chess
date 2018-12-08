package chess;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JButton;






public class Square
  extends JButton
{
  private int i;
  private int j;
  
  public Square(int i, int j)
  {
    this.i = i;
    this.j = j;
    setPreferredSize(new Dimension(100, 100));
  }
  



  private static int[] blueSquare = { -1, -1 };
  




  public static void setBlueSquare(int i, int j)
  {
    blueSquare[0] = i;
    blueSquare[1] = j;
  }
  



  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    



    int value = Board.getMatValue(i, j, true);
    




    if ((i == blueSquare[0]) && (j == blueSquare[1])) {
      g.setColor(new Color(70, 102, 132));
    } else if (value < 0) {
      g.setColor(new Color(114, 62, 15));
    } else {
      g.setColor(new Color(208, 132, 66));
    }
    g.fillRect(0, 0, 100, 100);
    









    if ((Math.abs(value) % 10 == 2) || (Math.abs(value) % 10 == 9)) {
      if (Math.abs(value) < 10) {
        g.setColor(new Color(255, 255, 255));
      } else {
        g.setColor(new Color(0, 0, 0));
      }
      g.fillOval(38, 25, 24, 24);
      int[] xPoints = { 50, 35, 65 };
      int[] yPoints = { 25, 75, 75 };
      g.fillPolygon(xPoints, yPoints, 3);
    }
    



    if (Math.abs(value) % 10 == 3) {
      if (Math.abs(value) < 10) {
        g.setColor(new Color(255, 255, 255));
      } else {
        g.setColor(new Color(0, 0, 0));
      }
      int[] xPoints = { 39, 52, 72, 64, 49, 47, 64, 27, 31 };
      int[] yPoints = { 25, 25, 45, 55, 45, 47, 80, 80, 40 };
      g.fillPolygon(xPoints, yPoints, 9);
    }
    



    if (Math.abs(value) % 10 == 4) {
      if (Math.abs(value) < 10) {
        g.setColor(new Color(255, 255, 255));
      } else {
        g.setColor(new Color(0, 0, 0));
      }
      int[] xPoints1 = { 50, 61, 39 };
      int[] yPoints1 = { 18, 28, 28 };
      g.fillPolygon(xPoints1, yPoints1, 3);
      g.fillOval(35, 23, 30, 42);
      int[] xPoints2 = { 50, 70, 70, 50, 30, 30 };
      int[] yPoints2 = { 58, 70, 80, 68, 80, 70 };
      g.fillPolygon(xPoints2, yPoints2, 6);
    }
    



    if (Math.abs(value) % 10 == 5) {
      if (Math.abs(value) < 10) {
        g.setColor(new Color(255, 255, 255));
      } else {
        g.setColor(new Color(0, 0, 0));
      }
      g.fillRect(30, 24, 8, 8);
      g.fillRect(46, 24, 8, 8);
      g.fillRect(62, 24, 8, 8);
      g.fillRect(30, 32, 40, 8);
      g.fillRect(37, 37, 26, 40);
      g.fillRect(30, 68, 40, 12);
    }
    



    if (Math.abs(value) % 10 == 6) {
      if (Math.abs(value) < 10) {
        g.setColor(new Color(255, 255, 255));
      } else {
        g.setColor(new Color(0, 0, 0));
      }
      g.fillOval(18, 28, 12, 12);
      g.fillOval(44, 22, 12, 12);
      g.fillOval(70, 28, 12, 12);
      int[] xPoints1 = { 50, 60, 40 };
      int[] yPoints1 = { 30, 65, 65 };
      g.fillPolygon(xPoints1, yPoints1, 3);
      int[] xPoints2 = { 50, 76, 65, 35, 24 };
      int[] yPoints2 = { 65, 34, 80, 80, 34 };
      g.fillPolygon(xPoints2, yPoints2, 5);
    }
    



    if (Math.abs(value) % 10 == 7) {
      if (Math.abs(value) < 10) {
        g.setColor(new Color(255, 255, 255));
      } else {
        g.setColor(new Color(0, 0, 0));
      }
      g.fillRect(45, 15, 10, 34);
      g.fillRect(33, 27, 34, 10);
      int[] xPoints = { 28, 72, 62, 38 };
      int[] yPoints = { 49, 49, 80, 80 };
      g.fillPolygon(xPoints, yPoints, 4);
    }
  }
}
