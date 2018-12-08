import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;




public class Server
{
  private static ServerSocket ss;
  private static Matchmaker matchmaker;
  
  public Server() {}
  
  public static Matchmaker getMatchmaker()
  {
    return matchmaker;
  }
  
  public static void start()
  {
    try
    {
      ss = new ServerSocket(60010);
      matchmaker = new Matchmaker();
      matchmaker.start();
      
      createServerFrame();
      
      for (;;)
      {
        Socket socket = ss.accept();
        System.out.println("Connection found: " + socket.toString());
        
        new LoginThread(socket).start();
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public static void createServerFrame() {
    JFrame frame = new JFrame("Server");
    frame.setSize(180, 120);
    frame.setDefaultCloseOperation(3);
    frame.add(new ServerPanel());
    frame.setVisible(true);
    frame.setResizable(false);
  }
}
