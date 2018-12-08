package chat;

import chess.BoardAndMessenger;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import login.Client;







public class TextEntry
  extends JPanel
{
  private static JTextArea entryArea = new JTextArea(2, 16);
  
  public static String getEntry() { return entryArea.getText(); }
  
  public TextEntry()
  {
    entryArea.setLineWrap(true);
    
    add(new JScrollPane(entryArea), "West");
    JButton sendButton = new JButton("Send");
    sendButton.addActionListener(new Send());
    add(sendButton, "East");
    
    new Thread()
    {
      public void run() {
        try {
          String entry;
          while ((entry = TextEntry.getEntry()) != null) { String entry;
            Thread.sleep(10L);
            if (entry.contains("\n")) {
              TextEntry.sendMessage();
            }
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }.start();
  }
  
  public static void sendMessage() {
    String entry = getEntry();
    if (!entry.equals(""))
    {

      if (entry.contains("\n")) {
        entry = entry.replace('\n', ' ');
      }
      BoardAndMessenger.sendToServer(new String[] { "message", Client.getUser() + ": " + entry });
      entryArea.setText("");
    }
  }
}
