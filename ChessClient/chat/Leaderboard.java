package chat;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JTextArea;




public class Leaderboard
  extends JTextArea
{
  JTextArea textArea = new JTextArea();
  
  public void update(String[] players) { String ranking = "LEADERBOARD\n\n";
    for (int i = 0; i < 10; i++) {
      ranking = ranking + (i + 1) + ":\t" + players[i] + "\n";
    }
    setText(ranking);
    setFont(new Font("Helvetica", 0, 16));
  }
  
  public Leaderboard()
  {
    setBackground(new Color(238, 238, 238));
    setPreferredSize(new Dimension(300, 300));
  }
}
