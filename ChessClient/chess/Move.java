package chess;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;









public class Move
  implements ActionListener
{
  private int i;
  private int j;
  private int squareColour;
  private Board board;
  
  public Move(int i, int j, Board board)
  {
    this.i = i;
    this.j = j;
    if ((i + j) % 2 == 0) {
      squareColour = 1;
    } else {
      squareColour = -1;
    }
    this.board = board;
  }
  
  public static void sendMove() {
    BoardAndMessenger.sendToServer(Board.getMat());
  }
  



  private static boolean start = true;
  


  private static int piece;
  


  private static int replaced;
  

  private static int[] startSquare = new int[3];
  private static int[] endSquare = new int[2];
  
  public static boolean pieceMoved = false;
  



  public void movePiece()
  {
    Rules.removeEnPassant();
    Board.setMatValue(startSquare[0], startSquare[1], startSquare[2], true);
    if (((squareColour == 1) && (piece < 0)) || ((squareColour == -1) && (piece > 0))) {
      piece = -piece;
    }
    if ((Math.abs(piece) % 10 == 2) && (Math.abs(endSquare[0] - startSquare[0]) == 2)) {
      if (piece > 0) piece += 7; else
        piece -= 7;
    }
    Board.setMatValue(i, j, piece, true);
    pieceMoved = true;
  }
  

  public void actionPerformed(ActionEvent a)
  {
    
    if ((Rules.getTurn() % 2 != Board.getMyTurn()) || (Rules.isLocked())) {
      return;
    }
    



    if (start)
    {




      if (((Rules.getTurn() % 2 == 0) && (Rules.whitePiece(i, this.j, true))) || ((Rules.getTurn() % 2 == 1) && (Rules.blackPiece(i, this.j, true))))
      {



        Square.setBlueSquare(i, this.j);
        startSquare[0] = i;
        startSquare[1] = this.j;
        startSquare[2] = squareColour;
        piece = Board.getMatValue(i, this.j, true);
        
        start = false;
      }
      

    }
    else
    {

      Square.setBlueSquare(-1, -1);
      endSquare[0] = i;
      endSquare[1] = this.j;
      replaced = Board.getMatValue(i, this.j, true);
      



      if ((Rules.validMove(startSquare, endSquare, true, true)) && (!Rules.selfCheck(startSquare, endSquare)))
      {
        movePiece();
        



        for (int j = 0; j < 8; j++) {
          if ((Math.abs(Board.getMatValue(0, j, true)) == 2) || (Math.abs(Board.getMatValue(7, j, true)) == 12)) {
            promotePawn();
          }
        }
        
        if (Rules.getTurn() % 2 == 0) {
          if ((startSquare[0] == 7) && (startSquare[1] == 0)) {
            Rules.blockCastle(true, 0);
          } else if ((startSquare[0] == 7) && (startSquare[1] == 7)) {
            Rules.blockCastle(true, 1);
          } else if ((Math.abs(piece) == 7) && (!Rules.inCheck(Rules.getTurn(), true))) {
            Rules.blockCastle(true, 2);
          }
        }
        else if ((startSquare[0] == 0) && (startSquare[1] == 0)) {
          Rules.blockCastle(false, 0);
        } else if ((startSquare[0] == 0) && (startSquare[1] == 7)) {
          Rules.blockCastle(false, 1);
        } else if ((Math.abs(piece) == 17) && (!Rules.inCheck(Rules.getTurn(), true))) {
          Rules.blockCastle(false, 2);
        }
      }
      




      if (Math.abs(piece) == 2) {
        if ((startSquare[0] == 3) && (i == 2) && 
          (Math.abs(startSquare[1] - this.j) == 1) && (Math.abs(Board.getMatValue(i + 1, this.j, true)) == 19)) {
          movePiece();
          Board.setMatValue(i + 1, this.j, -squareColour, true);
        }
      }
      else if ((Math.abs(piece) == 12) && 
        (startSquare[0] == 4) && (i == 5) && 
        (Math.abs(startSquare[1] - this.j) == 1) && (Math.abs(Board.getMatValue(i - 1, this.j, true)) == 9)) {
        movePiece();
        Board.setMatValue(i - 1, this.j, -squareColour, true);
      }
      





      if ((Board.getMatValue(startSquare[0], startSquare[1], true) == -7) && (i == 7) && (this.j == 0) && (Rules.canCastle(true, false))) {
        castle(true, false);
      } else if ((Board.getMatValue(startSquare[0], startSquare[1], true) == -7) && (i == 7) && (this.j == 7) && (Rules.canCastle(true, true))) {
        castle(true, true);
      } else if ((Board.getMatValue(startSquare[0], startSquare[1], true) == 17) && (i == 0) && (this.j == 0) && (Rules.canCastle(false, false))) {
        castle(false, false);
      } else if ((Board.getMatValue(startSquare[0], startSquare[1], true) == 17) && (i == 0) && (this.j == 7) && (Rules.canCastle(false, true))) {
        castle(false, true);
      }
      
      if (pieceMoved) sendMove();
      pieceMoved = false;
      start = true;
    }
    
    Board.updateCheckMat();
    board.repaint();
  }
  





  public static void castle(boolean white, boolean rightSide)
  {
    if (white) {
      if (rightSide) {
        Board.setMatValue(7, 6, -7, true);
        Board.setMatValue(7, 5, 5, true);
        Board.setMatValue(7, 4, -1, true);
        Board.setMatValue(7, 7, 1, true);
      } else {
        Board.setMatValue(7, 2, -7, true);
        Board.setMatValue(7, 3, 5, true);
        Board.setMatValue(7, 4, -1, true);
        Board.setMatValue(7, 0, -1, true);
      }
    }
    else if (rightSide) {
      Board.setMatValue(0, 6, 17, true);
      Board.setMatValue(0, 5, -15, true);
      Board.setMatValue(0, 4, 1, true);
      Board.setMatValue(0, 7, -1, true);
    } else {
      Board.setMatValue(0, 2, 17, true);
      Board.setMatValue(0, 3, -15, true);
      Board.setMatValue(0, 4, 1, true);
      Board.setMatValue(0, 0, 1, true);
    }
    
    sendMove();
  }
  
  public void promotePawn() {
    Rules.lock();
    pieceMoved = false;
    JFrame frame = new JFrame();
    frame.setSize(250, 135);
    frame.add(new PromotionPanel(this, frame));
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    frame.setLocation(width / 3 - getSizewidth / 3, height / 3 - getSizeheight / 3);
    frame.setVisible(true);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(0);
  }
  
  class PromotionPanel extends JPanel
  {
    private Move move;
    private JFrame frame;
    
    public PromotionPanel(Move move, JFrame frame)
    {
      this.move = move;
      this.frame = frame;
      setSize(250, 135);
      add(new JLabel("Select a piece to promote pawn to:"));
      JPanel buttonPanel = new JPanel();
      buttonPanel.setSize(200, 80);
      buttonPanel.setLayout(new GridLayout(2, 2));
      buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
      JButton knight = new JButton("Knight");
      knight.addActionListener(new Move.Promotion(Move.this, 3, move, frame));
      knight.setFocusPainted(false);
      buttonPanel.add(knight);
      JButton bishop = new JButton("Bishop");
      bishop.setFocusPainted(false);
      bishop.addActionListener(new Move.Promotion(Move.this, 4, move, frame));
      buttonPanel.add(bishop);
      JButton rook = new JButton("Rook");
      rook.setFocusPainted(false);
      rook.addActionListener(new Move.Promotion(Move.this, 5, move, frame));
      buttonPanel.add(rook);
      JButton queen = new JButton("Queen");
      queen.setFocusPainted(false);
      queen.addActionListener(new Move.Promotion(Move.this, 6, move, frame));
      buttonPanel.add(queen);
      add(buttonPanel);
    }
  }
  
  class Promotion implements ActionListener
  {
    private int piece;
    private Move move;
    private JFrame frame;
    
    public Promotion(int piece, Move move, JFrame frame)
    {
      this.piece = piece;
      this.move = move;
      this.frame = frame;
    }
    
    public void actionPerformed(ActionEvent e) {
      if (move.i == 0) {
        Board.setMatValue(move.i, move.j, piece * move.squareColour, true);
      } else {
        Board.setMatValue(move.i, move.j, (piece + 10) * move.squareColour, true);
      }
      Move.sendMove();
      Rules.unlock();
      frame.setVisible(false);
      frame.dispose();
    }
  }
}
