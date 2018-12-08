package chess;





public class Rules
{
  public Rules() {}
  




  private static boolean locked = true;
  
  public static boolean isLocked() { return locked; }
  
  public static void unlock() {
    locked = false;
  }
  
  public static void lock() { locked = true; }
  

  private static int turn = 0;
  


  public static int getTurn()
  {
    return turn;
  }
  

  public static void incrementTurn()
  {
    turn += 1;
  }
  





  public static boolean whitePiece(int i, int j, boolean real)
  {
    return (Math.abs(Board.getMatValue(i, j, real)) > 1) && (Math.abs(Board.getMatValue(i, j, real)) < 10);
  }
  





  public static boolean blackPiece(int i, int j, boolean real)
  {
    return Math.abs(Board.getMatValue(i, j, real)) > 11;
  }
  








  public static boolean inCheck(int turn, boolean real)
  {
    int king = 7;
    if (turn % 2 == 1) {
      king = 17;
    }
    



    int[] kingSquare = new int[2];
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (Math.abs(Board.getMatValue(i, j, real)) == king) {
          kingSquare[0] = i;
          kingSquare[1] = j;
        }
      }
    }
    
    int[] enemyPiece = new int[2];
    




    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (((king == 7) && (blackPiece(i, j, real))) || ((king == 17) && (whitePiece(i, j, real)))) {
          enemyPiece[0] = i;
          enemyPiece[1] = j;
          if (validMove(enemyPiece, kingSquare, false, real)) {
            return true;
          }
        }
      }
    }
    
    return false;
  }
  
  public static boolean selfCheck(int[] startSquare, int[] endSquare)
  {
    Board.setMatValue(endSquare[0], endSquare[1], Board.getMatValue(startSquare[0], startSquare[1], true), false);
    Board.setMatValue(startSquare[0], startSquare[1], 1, false);
    
    boolean selfCheck = inCheck(turn, false);
    Board.updateCheckMat();
    return selfCheck;
  }
  
  public static boolean mate()
  {
    if (locked) { return false;
    }
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (Math.abs(Board.getMatValue(i, j, true)) != 1) {
          int[] startSquare = { i, j };
          for (int k = 0; k < 8; k++) {
            for (int l = 0; l < 8; l++) {
              int[] endSquare = { k, l };
              if ((validMove(startSquare, endSquare, true, true)) && 
                (!selfCheck(startSquare, endSquare))) {
                return false;
              }
            }
          }
        }
      }
    }
    
    return true;
  }
  










  public static boolean validMove(int[] startSquare, int[] endSquare, boolean obeyTurn, boolean real)
  {
    if ((obeyTurn) && (
      ((turn % 2 == 0) && (blackPiece(startSquare[0], startSquare[1], real))) || ((turn % 2 == 1) && (whitePiece(startSquare[0], startSquare[1], real))))) {
      return false;
    }
    




    if (Math.abs(Board.getMatValue(startSquare[0], startSquare[1], real)) % 10 == 2)
      return pawn(startSquare, endSquare, real);
    if (Math.abs(Board.getMatValue(startSquare[0], startSquare[1], real)) % 10 == 3)
      return knight(startSquare, endSquare, real);
    if (Math.abs(Board.getMatValue(startSquare[0], startSquare[1], real)) % 10 == 4)
      return bishop(startSquare, endSquare, real);
    if (Math.abs(Board.getMatValue(startSquare[0], startSquare[1], real)) % 10 == 5)
      return rook(startSquare, endSquare, real);
    if (Math.abs(Board.getMatValue(startSquare[0], startSquare[1], real)) % 10 == 6)
      return queen(startSquare, endSquare, real);
    if (Math.abs(Board.getMatValue(startSquare[0], startSquare[1], real)) % 10 == 7)
      return king(startSquare, endSquare, real);
    return false;
  }
  









  public static void testSquare(int[] startSquare, boolean white, int[][] validSquares, int index, int i, int j, boolean real)
  {
    try
    {
      if (((!white) || (!whitePiece(startSquare[0] + i, startSquare[1] + j, real))) && ((white) || (!blackPiece(startSquare[0] + i, startSquare[1] + j, real)))) {
        validSquares[index][0] = (startSquare[0] + i);
        validSquares[index][1] = (startSquare[1] + j);
      }
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {}
  }
  





















  public static boolean pawn(int[] startSquare, int[] endSquare, boolean real)
  {
    int i = -1;
    if (blackPiece(startSquare[0], startSquare[1], real)) {
      i = 1;
    }
    
    int[][] validSquares = new int[4][2];
    for (int[] square : validSquares) {
      square[0] = -1;
      square[1] = -1;
    }
    


    try
    {
      if (Math.abs(Board.getMatValue(startSquare[0] + i, startSquare[1], real)) == 1) {
        validSquares[0][0] = (startSquare[0] + i);
        validSquares[0][1] = startSquare[1];
        if (((i == -1) && (startSquare[0] == 6) && (Math.abs(Board.getMatValue(4, startSquare[1], true)) == 1)) || (
          (i == 1) && (startSquare[0] == 1) && (Math.abs(Board.getMatValue(3, startSquare[1], true)) == 1))) {
          validSquares[1][0] = (startSquare[0] + i * 2);
          validSquares[1][1] = startSquare[1];
        }
      }
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {}
    


    try
    {
      if (((i == -1) && (blackPiece(startSquare[0] + i, startSquare[1] - 1, real))) || (
        (i == 1) && (whitePiece(startSquare[0] + i, startSquare[1] - 1, real)) && (whitePiece(startSquare[0] + i, startSquare[1] - 1, real)))) {
        validSquares[2][0] = (startSquare[0] + i);
        validSquares[2][1] = (startSquare[1] - 1);
      }
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException1) {}
    


    try
    {
      if (((i == -1) && (blackPiece(startSquare[0] + i, startSquare[1] + 1, real))) || (
        (i == 1) && (whitePiece(startSquare[0] + i, startSquare[1] + 1, real)) && (whitePiece(startSquare[0] + i, startSquare[1] + 1, real)))) {
        validSquares[3][0] = (startSquare[0] + i);
        validSquares[3][1] = (startSquare[1] + 1);
      }
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException2) {}
    
    for (int[] square : validSquares) {
      if ((square[0] == endSquare[0]) && (square[1] == endSquare[1])) {
        return true;
      }
    }
    return false;
  }
  






  public static boolean knight(int[] startSquare, int[] endSquare, boolean real)
  {
    boolean white = true;
    if (blackPiece(startSquare[0], startSquare[1], real)) {
      white = false;
    }
    
    int[][] validSquares = new int[8][2];
    for (int[] square : validSquares) {
      square[0] = -1;
      square[1] = -1;
    }
    



    testSquare(startSquare, white, validSquares, 0, -2, -1, real);
    testSquare(startSquare, white, validSquares, 1, -2, 1, real);
    testSquare(startSquare, white, validSquares, 2, -1, -2, real);
    testSquare(startSquare, white, validSquares, 3, -1, 2, real);
    testSquare(startSquare, white, validSquares, 4, 1, -2, real);
    testSquare(startSquare, white, validSquares, 5, 1, 2, real);
    testSquare(startSquare, white, validSquares, 6, 2, -1, real);
    testSquare(startSquare, white, validSquares, 7, 2, 1, real);
    
    for (int[] square : validSquares) {
      if ((square[0] == endSquare[0]) && (square[1] == endSquare[1])) {
        return true;
      }
    }
    return false;
  }
  






  public static boolean bishop(int[] startSquare, int[] endSquare, boolean real)
  {
    boolean white = true;
    if (blackPiece(startSquare[0], startSquare[1], real)) {
      white = false;
    }
    
    int[][] validSquares = new int[30][2];
    for (int[] square : validSquares) {
      square[0] = -1;
      square[1] = -1;
    }
    



    int index = 0;
    int i = startSquare[0] - 1; for (int j = startSquare[1] + 1; (i >= 0) && (j < 8); j++) {
      if (Math.abs(Board.getMatValue(i, j, real)) == 1) {
        validSquares[index][0] = i;
        validSquares[index][1] = j;
        index++;
      } else { if (((!white) || (!blackPiece(i, j, real))) && ((white) || (!whitePiece(i, j, real)))) break;
        validSquares[index][0] = i;
        validSquares[index][1] = j;
        index++;
        break;
      }
      i--;
    }
    













    int i = startSquare[0] + 1; for (int j = startSquare[1] + 1; (i < 8) && (j < 8); j++) {
      if (Math.abs(Board.getMatValue(i, j, real)) == 1) {
        validSquares[index][0] = i;
        validSquares[index][1] = j;
        index++;
      } else { if (((!white) || (!blackPiece(i, j, real))) && ((white) || (!whitePiece(i, j, real)))) break;
        validSquares[index][0] = i;
        validSquares[index][1] = j;
        index++;
        break;
      }
      i++;
    }
    













    int i = startSquare[0] + 1; for (int j = startSquare[1] - 1; (i < 8) && (j >= 0); j--) {
      if (Math.abs(Board.getMatValue(i, j, real)) == 1) {
        validSquares[index][0] = i;
        validSquares[index][1] = j;
        index++;
      } else { if (((!white) || (!blackPiece(i, j, real))) && ((white) || (!whitePiece(i, j, real)))) break;
        validSquares[index][0] = i;
        validSquares[index][1] = j;
        index++;
        break;
      }
      i++;
    }
    













    int i = startSquare[0] - 1; for (int j = startSquare[1] - 1; (i >= 0) && (j >= 0); j--) {
      if (Math.abs(Board.getMatValue(i, j, real)) == 1) {
        validSquares[index][0] = i;
        validSquares[index][1] = j;
        index++;
      } else { if (((!white) || (!blackPiece(i, j, real))) && ((white) || (!whitePiece(i, j, real)))) break;
        validSquares[index][0] = i;
        validSquares[index][1] = j;
        index++;
        break;
      }
      i--;
    }
    










    for (int[] square : validSquares) {
      if ((square[0] == endSquare[0]) && (square[1] == endSquare[1])) {
        return true;
      }
    }
    return false;
  }
  






  public static boolean rook(int[] startSquare, int[] endSquare, boolean real)
  {
    boolean white = true;
    if (blackPiece(startSquare[0], startSquare[1], real)) {
      white = false;
    }
    
    int[][] validSquares = new int[30][2];
    for (int[] square : validSquares) {
      square[0] = -1;
      square[1] = -1;
    }
    



    int index = 0;
    for (int i = startSquare[0] - 1; i >= 0; i--) {
      if (Math.abs(Board.getMatValue(i, startSquare[1], real)) == 1) {
        validSquares[index][0] = i;
        validSquares[index][1] = startSquare[1];
        index++;
      } else { if (((!white) || (!blackPiece(i, startSquare[1], real))) && ((white) || (!whitePiece(i, startSquare[1], real)))) break;
        validSquares[index][0] = i;
        validSquares[index][1] = startSquare[1];
        index++;
        break;
      }
    }
    



    for (int i = startSquare[0] + 1; i < 8; i++) {
      if (Math.abs(Board.getMatValue(i, startSquare[1], real)) == 1) {
        validSquares[index][0] = i;
        validSquares[index][1] = startSquare[1];
        index++;
      } else { if (((!white) || (!blackPiece(i, startSquare[1], real))) && ((white) || (!whitePiece(i, startSquare[1], real)))) break;
        validSquares[index][0] = i;
        validSquares[index][1] = startSquare[1];
        index++;
        break;
      }
    }
    



    for (int j = startSquare[1] - 1; j >= 0; j--) {
      if (Math.abs(Board.getMatValue(startSquare[0], j, real)) == 1) {
        validSquares[index][0] = startSquare[0];
        validSquares[index][1] = j;
        index++;
      } else { if (((!white) || (!blackPiece(startSquare[0], j, real))) && ((white) || (!whitePiece(startSquare[0], j, real)))) break;
        validSquares[index][0] = startSquare[0];
        validSquares[index][1] = j;
        index++;
        break;
      }
    }
    



    for (int j = startSquare[1] + 1; j < 8; j++) {
      if (Math.abs(Board.getMatValue(startSquare[0], j, real)) == 1) {
        validSquares[index][0] = startSquare[0];
        validSquares[index][1] = j;
        index++;
      } else { if (((!white) || (!blackPiece(startSquare[0], j, real))) && ((white) || (!whitePiece(startSquare[0], j, real)))) break;
        validSquares[index][0] = startSquare[0];
        validSquares[index][1] = j;
        index++;
        break;
      }
    }
    
    for (int[] square : validSquares) {
      if ((square[0] == endSquare[0]) && (square[1] == endSquare[1])) {
        return true;
      }
    }
    return false;
  }
  





  public static boolean queen(int[] startSquare, int[] endSquare, boolean real)
  {
    return (bishop(startSquare, endSquare, real)) || (rook(startSquare, endSquare, real));
  }
  






  public static boolean king(int[] startSquare, int[] endSquare, boolean real)
  {
    boolean white = true;
    if (blackPiece(startSquare[0], startSquare[1], real)) {
      white = false;
    }
    
    int[][] validSquares = new int[8][2];
    for (int[] square : validSquares) {
      square[0] = -1;
      square[1] = -1;
    }
    testSquare(startSquare, white, validSquares, 0, -1, -1, real);
    testSquare(startSquare, white, validSquares, 1, -1, 0, real);
    testSquare(startSquare, white, validSquares, 2, -1, 1, real);
    testSquare(startSquare, white, validSquares, 3, 0, -1, real);
    testSquare(startSquare, white, validSquares, 4, 0, 1, real);
    testSquare(startSquare, white, validSquares, 5, 1, -1, real);
    testSquare(startSquare, white, validSquares, 6, 1, 0, real);
    testSquare(startSquare, white, validSquares, 7, 1, 1, real);
    
    for (int[] square : validSquares) {
      if ((square[0] == endSquare[0]) && (square[1] == endSquare[1])) {
        return true;
      }
    }
    return false;
  }
  



  private static boolean[] whiteCastle = { true, true };
  private static boolean[] blackCastle = { true, true };
  




  public static void blockCastle(boolean white, int side)
  {
    if (side == 2) {
      if (white) {
        whiteCastle[0] = false;
        whiteCastle[1] = false;
      } else {
        blackCastle[0] = false;
        blackCastle[1] = false;
      }
    } else if (white) {
      whiteCastle[side] = false;
    } else {
      blackCastle[side] = false;
    }
  }
  









  public static boolean canCastle(boolean white, boolean rightSide)
  {
    if ((white) && (turn % 2 == 1))
      return false;
    if ((!white) && (turn % 2 == 0)) {
      return false;
    }
    



    if (white) {
      if ((!rightSide) && (whiteCastle[0] == 0))
        return false;
      if ((rightSide) && (whiteCastle[1] == 0)) {
        return false;
      }
    } else {
      if ((!rightSide) && (blackCastle[0] == 0))
        return false;
      if ((rightSide) && (blackCastle[1] == 0)) {
        return false;
      }
    }
    



    if ((white) && (rightSide) && ((Math.abs(Board.getMatValue(7, 5, true)) != 1) || (Math.abs(Board.getMatValue(7, 6, true)) != 1)))
      return false;
    if ((white) && (!rightSide) && ((Math.abs(Board.getMatValue(7, 1, true)) != 1) || (Math.abs(Board.getMatValue(7, 2, true)) != 1) || (Math.abs(Board.getMatValue(7, 3, true)) != 1)))
      return false;
    if ((!white) && (rightSide) && ((Math.abs(Board.getMatValue(0, 5, true)) != 1) || (Math.abs(Board.getMatValue(0, 6, true)) != 1)))
      return false;
    if ((!white) && (!rightSide) && ((Math.abs(Board.getMatValue(0, 1, true)) != 1) || (Math.abs(Board.getMatValue(0, 2, true)) != 1) || (Math.abs(Board.getMatValue(0, 3, true)) != 1))) {
      return false;
    }
    



    int[][] whiteLeft = { { 7, 2 }, { 7, 3 } };
    int[][] whiteRight = { { 7, 5 }, { 7, 6 } };
    int[][] blackLeft = { { 0, 2 }, { 0, 3 } };
    int[][] blackRight = { { 0, 5 }, { 0, 6 } };
    




    int[] enemyPiece = new int[2];
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if ((white) && (rightSide)) {
          if (blackPiece(i, j, true)) {
            enemyPiece[0] = i;
            enemyPiece[1] = j;
            if ((validMove(enemyPiece, whiteRight[0], false, true)) || (validMove(enemyPiece, whiteRight[1], false, true))) {
              return false;
            }
          }
        } else if (white) {
          if (blackPiece(i, j, true)) {
            enemyPiece[0] = i;
            enemyPiece[1] = j;
            if ((validMove(enemyPiece, whiteLeft[0], false, true)) || (validMove(enemyPiece, whiteLeft[1], false, true))) {
              return false;
            }
          }
        } else if ((!white) && (rightSide)) {
          if (whitePiece(i, j, true)) {
            enemyPiece[0] = i;
            enemyPiece[1] = j;
            if ((validMove(enemyPiece, blackRight[0], false, true)) || (validMove(enemyPiece, blackRight[1], false, true))) {
              return false;
            }
          }
        }
        else if (whitePiece(i, j, true)) {
          enemyPiece[0] = i;
          enemyPiece[1] = j;
          if ((validMove(enemyPiece, blackLeft[0], false, true)) || (validMove(enemyPiece, blackLeft[1], false, true))) {
            return false;
          }
        }
      }
    }
    




    if ((white) && (turn % 2 == 0) && (inCheck(turn, true)))
      return false;
    if ((!white) && (turn % 2 == 1) && (inCheck(turn, true))) {
      return false;
    }
    
    return true;
  }
  
  public static void removeEnPassant() {
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (Board.getMatValue(i, j, true) % 10 == 9) {
          Board.setMatValue(i, j, Board.getMatValue(i, j, true) - 7, true);
        } else if (Board.getMatValue(i, j, true) % 10 == -9) {
          Board.setMatValue(i, j, Board.getMatValue(i, j, true) + 7, true);
        }
      }
    }
  }
}
