package chat;

import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JPanel;
import javax.swing.JScrollPane;




public class Messenger
  extends JPanel
{
  private static Messages messages = new Messages();
  
  public static Messages getMessages() { return messages; }
  

  public static JScrollPane scrollPane = new JScrollPane(messages);
  
  public static JScrollPane getScrollPane() { return scrollPane; }
  
  public Messenger()
  {
    TextEntry textEntryPane = new TextEntry();
    setPreferredSize(new Dimension(300, 420));
    setLayout(null);
    add(scrollPane);
    add(textEntryPane);
    Insets insets = getInsets();
    Dimension size = scrollPane.getPreferredSize();
    scrollPane.setBounds(5 + left, 0 + top, width, height);
    size = textEntryPane.getPreferredSize();
    textEntryPane.setBounds(0 + left, 322 + top, width, height);
  }
}
