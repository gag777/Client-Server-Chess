package login;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;









public class RegisterGUI
  extends JPanel
{
  static ActionEvent e;
  static RegisterGUI frame;
  static RegisterGUI frame1;
  JPanel panel = new JPanel();
  JLabel titleRegist = new JLabel("Register");
  JLabel username = new JLabel("Username");
  JLabel password = new JLabel("Password");
  JLabel email = new JLabel("Email");
  JTextField UserNameText = new JTextField();
  JTextField PassWordsText = new JPasswordField();
  JTextField emailText = new JTextField();
  ImageIcon icon = new ImageIcon("src/newUser.png");
  JLabel imageLabel = new JLabel(icon);
  
  JButton confirm = new JButton("Confirm");
  JButton back = new JButton("Back");
  
  Font font1 = new Font("HelveticaNeue", 3, 50);
  Font font2 = new Font("Times New Roman", 1, 20);
  
  public RegisterGUI()
  {
    setBounds(0, 0, 1000, 750);
    
    titleRegist.setFont(font1);
    username.setFont(font2);
    password.setFont(font2);
    email.setFont(font2);
    
    setLayout(null);
    add(username);
    add(titleRegist);
    add(password);
    add(emailText);
    add(UserNameText);
    add(PassWordsText);
    add(email);
    add(confirm);
    add(back);
    add(imageLabel);
    
    titleRegist.setBounds(405, 240, 800, 60);
    password.setBounds(300, 430, 150, 50);
    username.setBounds(300, 390, 150, 50);
    email.setBounds(300, 350, 150, 50);
    PassWordsText.setBounds(450, 445, 250, 25);
    UserNameText.setBounds(450, 405, 250, 25);
    emailText.setBounds(450, 365, 250, 25);
    confirm.setBounds(550, 530, 120, 55);
    back.setBounds(400, 530, 120, 55);
    imageLabel.setBounds(350, 200, 100, 100);
    

    confirm.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        framec.start();
        String inputEmail = emailText.getText();
        String inputName = UserNameText.getText();
        String inputPass = PassWordsText.getText();
        if ((inputEmail.length() == 0) || (inputName.length() == 0) || (inputPass.length() == 0)) {
          JOptionPane.showMessageDialog(Login.frame, "Please fill in all details", "Excuse Me", 
            2);
          framec.stop();
          return;
        }
        if ((inputPass.length() < 5) || (inputName.length() < 5)) {
          JOptionPane.showMessageDialog(Login.frame, "Password and username must be more than 5 characters", 
            "Excuse Me", 2);
          
          return;
        }
        
        String fromServer = null;
        try {
          fromServer = framec.signUp(inputEmail, inputName, inputPass);
        }
        catch (ClassNotFoundException e1)
        {
          e1.printStackTrace();
        }
        framec.stop();
        
        if (fromServer.equals("success")) {
          JOptionPane.showMessageDialog(Login.frame, "Account made", "Success", 
            1);
          Login.frame.setContentPane(frameloginGUI);
        }
        else {
          JOptionPane.showMessageDialog(Login.frame, "Account creation failed, \n" + fromServer, "Excuse Me", 
            2);
          framec.stop();
        }
        
      }
      

    });
    back.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Login.frame.setContentPane(frameloginGUI);
      }
    });
  }
}
