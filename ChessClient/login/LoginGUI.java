package login;

import chess.BoardAndMessenger;
import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
















public class LoginGUI
  extends JPanel
{
  private JButton loginButton;
  private JLabel user;
  private JLabel pass;
  private JLabel ip;
  private JLabel host;
  private JLabel title2;
  private JTextField username;
  private JTextField password;
  private JTextField ipText;
  private JTextField hostText;
  private JButton signUp;
  
  public LoginGUI()
  {
    setLayout(null);
    setBackground(new Color(83, 96, 120));
    

    username = new JTextField(15);
    password = new JPasswordField(15);
    ipText = new JTextField(15);
    hostText = new JTextField(15);
    loginButton = new JButton("Log in");
    signUp = new JButton("Register");
    user = new JLabel("Username: ");
    pass = new JLabel("Password: ");
    ip = new JLabel("Server IP");
    host = new JLabel("Host");
    title2 = new JLabel("SeattleChess");
    ImageIcon icon = new ImageIcon("src/titleimage.jpg");
    JLabel imageLabel = new JLabel(icon);
    



    username.setBounds(480, 400, 180, 30);
    password.setBounds(480, 440, 180, 30);
    loginButton.setBounds(480, 480, 180, 40);
    signUp.setBounds(480, 530, 180, 40);
    user.setBounds(320, 400, 200, 30);
    ip.setBounds(360, 370, 200, 30);
    host.setBounds(360, 410, 200, 30);
    pass.setBounds(326, 442, 200, 30);
    title2.setBounds(305, 250, 800, 50);
    imageLabel.setBounds(450, 30, 400, 300);
    

    ipText.setBounds(520, 370, 180, 30);
    hostText.setBounds(520, 410, 180, 30);
    
    Font font1 = new Font("HelveticaNeue", 3, 50);
    Font font2 = new Font("Times New Roman", 1, 25);
    Font font3 = new Font("Times New Roman", 1, 20);
    Font font4 = new Font("Times New Roman", 1, 20);
    
    user.setFont(font2);
    pass.setFont(font2);
    ip.setFont(font2);
    host.setFont(font2);
    title2.setFont(font1);
    username.setFont(font3);
    password.setFont(font3);
    ipText.setFont(font3);
    hostText.setFont(font3);
    loginButton.setFont(font4);
    signUp.setFont(font4);
    
    user.setForeground(Color.white);
    pass.setForeground(Color.white);
    ip.setForeground(Color.white);
    host.setForeground(Color.white);
    title2.setForeground(Color.white);
    

    add(username);
    add(password);
    add(loginButton);
    add(signUp);
    add(user);
    add(pass);
    add(title2);
    add(imageLabel);
    








    loginButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        framec.start();
        
        String inputName = username.getText();
        String inputPass = password.getText();
        if ((inputName.length() == 0) || (inputPass.length() == 0)) {
          JOptionPane.showMessageDialog(Login.frame, "You must enter a username and password", "SeattleChess", 
            2);
          framec.stop();
          return;
        }
        try
        {
          String message = framec.signIn(inputName, inputPass);
          if (message.equals("success"))
          {

            JFrame frame = new JFrame("SeattleChess");
            BoardAndMessenger bm = new BoardAndMessenger();
            frame.setSize(1220, 950);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(3);
            frame.setBackground(Color.LIGHT_GRAY);
            frame.add(bm);
            return;
          }
          
          JOptionPane.showMessageDialog(Login.frame, "Login failed, \n" + message, "SeattleChess", 
            2);
        }
        catch (HeadlessException e1) {
          e1.printStackTrace();
        }
        catch (ClassNotFoundException e1) {
          e1.printStackTrace();
        }
        framec.stop();

      }
      


    });
    signUp.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Login.frame.setContentPane(frameregisterGUI);
        
        Login.frame.setTitle("Register");
      }
    });
  }
}
