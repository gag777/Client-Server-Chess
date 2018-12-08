import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

public class GameThread extends Thread
{
  private StreamPair white;
  private StreamPair black;
  private Object sendObject;
  
  public GameThread(StreamPair white, StreamPair black)
  {
    this.white = white;
    this.black = black;
  }
  

  private volatile boolean sendFlag = false;
  
  public void setSendObject(Object sendObject) { this.sendObject = sendObject;
    sendFlag = true;
  }
  
  private int[][] mat = {
    { 15, -13, 14, -16, 17, -14, 13, -15 }, 
    { -12, 12, -12, 12, -12, 12, -12, 12 }, 
    { 1, -1, 1, -1, 1, -1, 1, -1 }, 
    { -1, 1, -1, 1, -1, 1, -1, 1 }, 
    { 1, -1, 1, -1, 1, -1, 1, -1 }, 
    { -1, 1, -1, 1, -1, 1, -1, 1 }, 
    { 2, -2, 2, -2, 2, -2, 2, -2 }, 
    { -5, 3, -4, 6, -7, 4, -3, 5 } };
  
  public int[][] getMat() {
    return mat;
  }
  
  public void setMat(int[][] newMat) { mat = newMat; }
  

  private StringBuffer messages = new StringBuffer();
  
  public StringBuffer getMessages() { return messages; }
  
  public void addMessage(String message) {
    messages.append(message + "\n");
  }
  

  public void run()
  {
    try
    {
      new PlayerThread(this, white).start();
      new PlayerThread(this, black).start();
      
      ObjectOutputStream whiteOut = white.getOutputStream();
      ObjectOutputStream blackOut = black.getOutputStream();
      
      whiteOut.writeObject(new String[] { "colour", "0" });
      whiteOut.flush();
      blackOut.writeObject(new String[] { "colour", "1" });
      blackOut.flush();
      whiteOut.writeObject(new String[] { "message", "You are now connected to your opponent.\nYou are White." });
      whiteOut.flush();
      blackOut.writeObject(new String[] { "message", "You are now connected to your opponent.\nYou are Black." });
      blackOut.flush();
      
      DatabaseManager db = new DatabaseManager();
      db.connectDatabase();
      ArrayList<String> leaderboard = null;
      try {
        leaderboard = db.viewTop10();
      } catch (SQLException e) {
        System.out.println("Couldn't connect to database.");
      }
      String[] leaderboardMessage = new String[11];
      leaderboardMessage[0] = "leaderboard";
      if (leaderboard != null) {
        for (int i = 0; i < leaderboard.size(); i++) {
          leaderboardMessage[(i + 1)] = ((String)leaderboard.get(i));
        }
      }
      whiteOut.writeObject(leaderboardMessage);
      whiteOut.flush();
      blackOut.writeObject(leaderboardMessage);
      blackOut.flush();
      
      for (;;)
      {
        if (sendFlag)
        {
          whiteOut.writeObject(sendObject);
          whiteOut.flush();
          blackOut.writeObject(sendObject);
          blackOut.flush();
          sendFlag = false;
        }
      }
    } catch (java.io.IOException e) {
      System.out.println("Game shut down.");
    }
  }
}
