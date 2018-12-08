import java.io.PrintStream;
import java.sql.SQLException;

public class PlayerThread extends Thread
{
  private GameThread gameThread;
  private StreamPair connection;
  
  public PlayerThread(GameThread gameThread, StreamPair connection)
  {
    this.gameThread = gameThread;
    this.connection = connection;
  }
  

  public void run()
  {
    try
    {
      java.io.ObjectInputStream in = connection.getInputStream();
      
      DatabaseManager db = new DatabaseManager();
      db.connectDatabase();
      Object obj;
      while ((obj = in.readObject()) != null) {
        Object obj;
        if ((obj instanceof int[][])) {
          int[][] newMat = (int[][])obj;
          boolean changed = false;
          for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
              if (gameThread.getMat()[i][j] != newMat[i][j]) {
                changed = true;
                break;
              }
            }
            if (changed) break;
          }
          if (changed) {
            gameThread.setMat(newMat);
            gameThread.setSendObject(gameThread.getMat());
          }
        } else if ((obj instanceof String[])) {
          String[] arr = (String[])obj;
          if (arr[0].equals("message")) {
            gameThread.addMessage(arr[1]);
            gameThread.setSendObject(new String[] { "message", gameThread.getMessages().toString() });
          } else if (arr[0].equals("check")) {
            if (arr[1].equals("0")) {
              gameThread.addMessage("White is in check.");
            } else if (arr[1].equals("1")) {
              gameThread.addMessage("Black is in check.");
            }
            gameThread.setSendObject(new String[] { "message", gameThread.getMessages().toString() });
          } else if (arr[0].equals("win")) {
            db.updateWins(arr[1]);
            System.out.println("Database wins updated: " + arr[1]);
          } else if (arr[0].equals("loss")) {
            db.updateLosses(arr[1]);
            System.out.println("Database losses updated: " + arr[1]);
          } else if (arr[0].equals("draw")) {
            db.updateDraws(arr[1]);
            System.out.println("Database draws updated: " + arr[1]);
          }
        }
      }
    }
    catch (java.io.IOException e) {
      gameThread.setSendObject(new String[] { "disconnect" });
    } catch (ClassNotFoundException e) {
      throw new Error("Unknown object recieved from client.");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
