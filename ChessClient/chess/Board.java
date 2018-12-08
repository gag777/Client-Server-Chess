package chess;

import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JPanel;









public class Board
  extends JPanel
{
  private static int myTurn;
  
  public static int getMyTurn()
  {
    return myTurn;
  }
  
  public static void setMyTurn(int turn) { myTurn = turn; }
  














  private static int[][] mat = {
    { 15, -13, 14, -16, 17, -14, 13, -15 }, 
    { -12, 12, -12, 12, -12, 12, -12, 12 }, 
    { 1, -1, 1, -1, 1, -1, 1, -1 }, 
    { -1, 1, -1, 1, -1, 1, -1, 1 }, 
    { 1, -1, 1, -1, 1, -1, 1, -1 }, 
    { -1, 1, -1, 1, -1, 1, -1, 1 }, 
    { 2, -2, 2, -2, 2, -2, 2, -2 }, 
    { -5, 3, -4, 6, -7, 4, -3, 5 } };
  

  private static int[][] checkMat = {
    { 15, -13, 14, -16, 17, -14, 13, -15 }, 
    { -12, 12, -12, 12, -12, 12, -12, 12 }, 
    { 1, -1, 1, -1, 1, -1, 1, -1 }, 
    { -1, 1, -1, 1, -1, 1, -1, 1 }, 
    { 1, -1, 1, -1, 1, -1, 1, -1 }, 
    { -1, 1, -1, 1, -1, 1, -1, 1 }, 
    { 2, -2, 2, -2, 2, -2, 2, -2 }, 
    { -5, 3, -4, 6, -7, 4, -3, 5 } };
  
  public static void updateCheckMat() {
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        checkMat[i][j] = mat[i][j];
      }
    }
  }
  





  public static int getMatValue(int i, int j, boolean real)
  {
    if (real) return mat[i][j];
    return checkMat[i][j];
  }
  





  public static void setMatValue(int i, int j, int value, boolean real)
  {
    if (real) mat[i][j] = value; else
      checkMat[i][j] = value;
  }
  
  public static int[][] getMat() {
    return mat;
  }
  
  public static void setMat(int[][] newMat) { mat = newMat; }
  





  public Board()
  {
    setSize(870, 940);
    setLayout(null);
    Insets insets = getInsets();
    


    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        Square square = new Square(i, j);
        square.addActionListener(new Move(i, j, this));
        add(square);Dimension size = square.getPreferredSize();
        square.setBounds(j * 100 + 50 + left, i * 100 + 50 + top, width, height);
      }
    }
  }
}
