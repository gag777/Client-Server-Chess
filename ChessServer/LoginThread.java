import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class LoginThread
  extends Thread
{
  private static ObjectOutputStream outData;
  private static ObjectInputStream inData;
  private Socket socket;
  public String user;
  
  public LoginThread(Socket socket)
  {
    this.socket = socket;
  }
  
  public void run()
  {
    try
    {
      inData = new ObjectInputStream(socket.getInputStream());
      outData = new ObjectOutputStream(socket.getOutputStream());
      Object obj = inData.readObject();
      
      String[] input = (String[])obj;
      
      if (input[0].equals("sign-up")) {
        registerUser(input);
      }
      

      if (input[0].equals("sign-in")) {
        signInUser(input);
      }
      
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
  }
  





  public void registerUser(String[] input)
    throws IOException
  {
    DatabaseManager db = new DatabaseManager();
    db.connectDatabase();
    
    if (!db.checkUsername(input[2])) {
      db.register(input[2], input[3], input[1]);
      outData.writeObject(new String[] { "success" });
      outData.flush();
      socket.close();
      System.out.println("Sign-up successful. Socket closed.");
    }
    else {
      outData.writeObject(new String[] { "fail", "Username is taken" });
      outData.flush();
    }
  }
  






  public void signInUser(String[] input)
    throws IOException
  {
    DatabaseManager db = new DatabaseManager();
    db.connectDatabase();
    
    if (db.checkUsername(input[1]))
    {
      if (db.checkPassword(input[1], input[2]))
      {
        outData.writeObject(new String[] { "success" });
        System.out.println(input[1] + " logged in.");
        outData.flush();
        Server.getMatchmaker().addConnection(new StreamPair(inData, outData));
        System.out.println("Connection queued.");
      } else {
        outData.writeObject(new String[] { "fail", "Password Incorrect" });
      }
    }
    else {
      outData.writeObject(new String[] { "fail", "User doesnt exist" });
      System.out.println("Sign-in failed. Socket closed.");
    }
  }
}
