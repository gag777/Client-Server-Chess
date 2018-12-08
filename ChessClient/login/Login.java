package login;

import java.awt.EventQueue;
import javax.swing.JFrame;





public class Login
  extends JFrame
{
  public Client c = new Client();
  public LoginGUI loginGUI = new LoginGUI();
  public RegisterGUI registerGUI = new RegisterGUI();
  public static Login frame = null;
  
  public Login() {
    setTitle("Welcome");
    setResizable(false);
    setDefaultCloseOperation(3);
    setSize(1000, 750);
    add(loginGUI);
    add(registerGUI);
    setContentPane(loginGUI);
  }
  
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          Login.frame = new Login();
          Login.frame.setVisible(true);
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }
}
