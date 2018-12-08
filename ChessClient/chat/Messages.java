package chat;

import java.awt.Color;
import javax.swing.JTextArea;


public class Messages
  extends JTextArea
{
  public Messages()
  {
    super(20, 22);
    setLineWrap(true);
    setEnabled(false);
    setDisabledTextColor(Color.BLACK);
    setText("Waiting for opponent...");
  }
}
