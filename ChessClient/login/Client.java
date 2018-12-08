package login;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client
{
  private static ObjectOutputStream toServer;
  private static ObjectInputStream fromServer;
  private Socket socket;
  private static String user;
  
  public Client() {}
  
  public static ObjectOutputStream getToServer()
  {
    return toServer;
  }
  
  public static ObjectInputStream getFromServer() {
    return fromServer;
  }
  




  public void start()
  {
    try
    {
      socket = new Socket("2001:630:1d2:1100:724d:7bff:fe84:7a74", 60010);
      
      toServer = new ObjectOutputStream(socket.getOutputStream());
      fromServer = new ObjectInputStream(socket.getInputStream());
      
      System.out.println("Connected to server.");
    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (IOException e) {
      javax.swing.JOptionPane.showMessageDialog(new javax.swing.JFrame(), "Could not connect to server", "SeattleChess", 
        0);
      System.exit(-1);
    }
  }
  


  public void stop()
  {
    try
    {
      System.out.println("Socket closed.");
      socket.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
  








  public String signUp(String email, String username, String password)
    throws ClassNotFoundException
  {
    String[] in = null;
    try
    {
      sendToServer(new String[] { "sign-up", email, username, password });
      
      in = (String[])fromServer.readObject();
      if (in[0].equals("success")) {
        return "success";
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    
    return in[1];
  }
  

  public static String getUser()
  {
    return user;
  }
  





  public String signIn(String username, String password)
    throws ClassNotFoundException
  {
    String[] in = null;
    try
    {
      sendToServer(new String[] { "sign-in", username, password });
      toServer.flush();
      
      in = (String[])fromServer.readObject();
      if (in[0].equals("success")) {
        user = username;
        Login.frame.dispose();
        return "success";
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    
    return in[1];
  }
  
  public static void sendToServer(Object obj) {
    try {
      toServer.writeObject(obj);
      toServer.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
