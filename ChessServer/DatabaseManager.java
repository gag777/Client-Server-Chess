import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;










public class DatabaseManager
{
  static final String JDBC_DRIVER = "org.postgresql.Driver";
  static final String DB_URL = "jdbc:postgresql://mod-msc-sw1.cs.bham.ac.uk:5432/seattle";
  static final String USER = "seattle";
  static final String PASS = "hzcfjw93tf";
  private Connection conn = null;
  private ResultSet resultSetting = null;
  private String emailaddress;
  private String username;
  private String password;
  private String checkUsername;
  
  public DatabaseManager(String emailaddress, String username, String password) {
    this.emailaddress = emailaddress;
    this.username = username;
    this.password = password;
  }
  
  public DatabaseManager(String username, String password)
  {
    this.username = username;
    this.password = password;
  }
  





  public DatabaseManager() {}
  





  public void connectDatabase()
  {
    try
    {
      Class.forName("org.postgresql.Driver");
      
      conn = DriverManager.getConnection("jdbc:postgresql://mod-msc-sw1.cs.bham.ac.uk:5432/seattle", "seattle", "hzcfjw93tf");
      
      System.out.println("Connected to database successfully.");

    }
    catch (SQLException se)
    {
      se.printStackTrace();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  







  public void register(String username, String password, String emailaddress)
  {
    try
    {
      String populateSeattleGameChess = "INSERT INTO seattle_chessboard_user_information VALUES(?,?,?) ;";
      String stats = "INSERT INTO stats (wins, lossess, draws, username) VALUES (0,0,0,?) ;";
      
      PreparedStatement ps1 = conn.prepareStatement(populateSeattleGameChess);
      ps1.setString(1, username);
      ps1.setString(2, password);
      ps1.setString(3, emailaddress);
      ps1.executeUpdate();
      

      PreparedStatement ps3 = conn.prepareStatement(stats);
      ps3.setString(1, username);
      ps3.executeUpdate();
    }
    catch (SQLException se)
    {
      se.printStackTrace();
    }
    catch (Exception a) {
      a.printStackTrace();
    }
  }
  
  public boolean checkUsername(String username)
  {
    boolean check = false;
    
    try
    {
      String query = "SELECT username FROM seattle_chessboard_user_information WHERE username = ?;";
      
      PreparedStatement ps = conn.prepareStatement(query);
      ps.setString(1, username);
      ResultSet rs = ps.executeQuery();
      
      if (rs.next()) {
        if (rs.getString(1).equals(username)) {
          check = true;
          System.out.println("User exists.");
        }
      } else {
        System.out.println("User does not exist.");
      }
    } catch (SQLException err) {
      System.out.println("ERROR: " + err);
    }
    return check;
  }
  
  public boolean checkPassword(String username, String password)
  {
    boolean login = false;
    
    try
    {
      String query = "SELECT * FROM Seattle_chessboard_user_information WHERE username = ? AND password = ?;";
      
      PreparedStatement ps = conn.prepareStatement(query);
      ps.setString(1, username);
      ps.setString(2, password);
      ResultSet rs = ps.executeQuery();
      
      if (rs.next()) {
        if ((rs.getString(1).equals(username)) && (rs.getString(2).equals(password))) {
          login = true;
          System.out.println("Password correct.");
        } else {
          System.out.println("Password incorrect.");
        }
      } else {
        System.out.println("Incorrect information.");
      }
    }
    catch (SQLException err) {
      System.out.println("ERROR: " + err);
    }
    return login;
  }
  

  public ArrayList<String> viewTop10()
    throws SQLException
  {
    ArrayList<String> topTen = new ArrayList();
    
    try
    {
      String query = "SELECT username, wins, lossess FROM Stats ORDER BY wins DESC LIMIT 10 ;";
      
      Statement stmt = conn.createStatement();
      
      ResultSet rs = stmt.executeQuery(query);
      while (rs.next()) {
        String usernameColumn = rs.getString("username");
        topTen.add(usernameColumn);
        int wins = rs.getInt("wins");
        int i = rs.getInt("lossess");
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return topTen;
  }
  

  public void updateWinsAndLosses(String winner, String loser, String draw)
    throws SQLException
  {
    String wins = "SELECT wins FROM STATS WHERE username = ?";
    String losses = "SELECT lossess FROM STATS WHERE username = ?";
    String draws = "SELECT draws FROM STATS WHERE username = ?";
    try {
      PreparedStatement ps = conn.prepareStatement(wins);
      
      int winnerWins = 0;
      ps.setString(1, winner);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        winnerWins = rs.getInt("wins");
      }
      
      int loserLosses = 0;
      ps = conn.prepareStatement(losses);
      ps.setString(1, loser);
      rs = ps.executeQuery();
      if (rs.next()) {
        loserLosses = rs.getInt("lossess");
      }
      
      int usersDraw = 0;
      ps = conn.prepareStatement(draws);
      ps.setString(1, draw);
      rs = ps.executeQuery();
      if (rs.next()) {
        usersDraw = rs.getInt("draws");
      }
      
      String query1 = "UPDATE STATS SET wins = " + (winnerWins + 1) + " WHERE username = ? ;";
      String query2 = "UPDATE STATS SET lossess = " + (loserLosses + 1) + " WHERE username = ? ;";
      String query3 = "UPDATE STATS SET draws  = " + (usersDraw + 1) + " WHERE username = ? ;";
      
      ps = conn.prepareStatement(query1);
      ps.setString(1, winner);
      ps.executeUpdate();
      ps = conn.prepareStatement(query2);
      ps.setString(1, loser);
      ps.executeUpdate();
      ps = conn.prepareStatement(query3);
      ps.setString(1, draw);
      ps.executeUpdate();
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  public void updateWins(String winner) throws SQLException {
    String wins = "SELECT wins FROM STATS WHERE username = ?";
    PreparedStatement ps = conn.prepareStatement(wins);
    
    int winnerWins = 0;
    ps.setString(1, winner);
    ResultSet rs = ps.executeQuery();
    if (rs.next()) {
      winnerWins = rs.getInt("wins");
    }
    String query1 = "UPDATE STATS SET wins = " + (winnerWins + 1) + " WHERE username = ? ;";
    ps = conn.prepareStatement(query1);
    ps.setString(1, winner);
    ps.executeUpdate();
  }
  
  public void updateLosses(String loser) throws SQLException {
    String losses = "SELECT lossess FROM STATS WHERE username = ?";
    PreparedStatement ps = conn.prepareStatement(losses);
    
    int loserLosses = 0;
    ps.setString(1, loser);
    ResultSet rs = ps.executeQuery();
    if (rs.next()) {
      loserLosses = rs.getInt("lossess");
    }
    String query2 = "UPDATE STATS SET lossess = " + (loserLosses + 1) + " WHERE username = ? ;";
    ps = conn.prepareStatement(query2);
    ps.setString(1, loser);
    ps.executeUpdate();
  }
  
  public void updateDraws(String drawer) throws SQLException {
    String draws = "SELECT draws FROM STATS WHERE username = ?";
    PreparedStatement ps = conn.prepareStatement(draws);
    
    int usersDraw = 0;
    ps.setString(1, drawer);
    ResultSet rs = ps.executeQuery();
    if (rs.next()) {
      usersDraw = rs.getInt("draws");
    }
    String query3 = "UPDATE STATS SET draws = " + (usersDraw + 1) + " WHERE username = ? ;";
    ps = conn.prepareStatement(query3);
    ps.setString(1, drawer);
    ps.executeUpdate();
  }
  
  public boolean isConnected()
  {
    if (conn == null)
      return false;
    try {
      if (conn.isClosed())
        return false;
    } catch (SQLException e) {
      return false;
    }
    return true;
  }
  


  private void TerminateConnection()
  {
    try
    {
      if (conn != null) {
        conn.close();
      }
    } catch (SQLException e) {
      System.out.println("Unable to close connection to database");
      e.printStackTrace();
    }
  }
}
