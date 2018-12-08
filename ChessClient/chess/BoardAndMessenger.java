package chess;

import chat.Leaderboard;
import chat.Messenger;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import login.Client;

public class BoardAndMessenger extends JPanel
{
  private static java.net.Socket s;
  private static ObjectOutputStream out;
  private static java.io.ObjectInputStream in;
  
  public static void sendToServer(Object obj)
  {
    try
    {
      out.writeObject(obj);
      out.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public BoardAndMessenger()
  {
    out = Client.getToServer();
    setLayout(null);
    final Board board = new Board();
    Messenger messenger = new Messenger();
    final Leaderboard leaderboard = new Leaderboard();
    setPreferredSize(new Dimension(1100, 900));
    add(board);
    add(messenger);
    Insets insets = getInsets();
    Dimension size = board.getPreferredSize();
    board.setBounds(left, top, width, height);
    size = messenger.getPreferredSize();
    messenger.setBounds(900 + left, 50 + top, width, height);
    size = leaderboard.getPreferredSize();
    leaderboard.setBounds(900 + left, 480 + top, width, height);
    add(leaderboard);
    insets = getInsets();
    size = leaderboard.getPreferredSize();
    leaderboard.setBounds(900 + left, 480 + top, width, height);
    
    new Thread()
    {
      public void run() {
        try {
          BoardAndMessenger.in = Client.getFromServer();
          Object obj;
          while ((obj = BoardAndMessenger.in.readObject()) != null) { Object obj;
            if ((obj instanceof int[][])) {
              Board.setMat((int[][])obj);
              BoardAndMessenger.mateAlert();
              Rules.incrementTurn();
              BoardAndMessenger.mateAlert();
              if ((Rules.inCheck(Rules.getTurn(), true)) && (Rules.getTurn() % 2 == Board.getMyTurn())) {
                BoardAndMessenger.sendToServer(new String[] { "check", Integer.toString(Rules.getTurn() % 2) });
              }
            } else if ((obj instanceof String[])) {
              String[] arr = (String[])obj;
              if (arr[0].equals("colour")) {
                Board.setMyTurn(Integer.parseInt(arr[1]));
                Rules.unlock();
              } else if (arr[0].equals("message")) {
                Messenger.getMessages().setText(arr[1]);
                Thread.sleep(10L);
                JScrollBar scrollBar = Messenger.getScrollPane().getVerticalScrollBar();
                scrollBar.setValue(scrollBar.getMaximum());
              } else if (arr[0].equals("disconnect")) {
                BoardAndMessenger.exit("Your opponent has disconnected.\nGame will exit in five seconds.");
              }
              else if (arr[0].equals("leaderboard")) {
                String[] players = new String[10];
                for (int i = 0; i < 10; i++) {
                  players[i] = arr[(i + 1)];
                }
                leaderboard.update(players);
              }
            }
            board.repaint();
          }
        } catch (IOException e) {
          BoardAndMessenger.exit("Lost connection to server.\nGame will shut down in five seconds.");
        }
        catch (ClassNotFoundException e) {
          e.printStackTrace();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }.start();
  }
  
  public static void exit(String message)
  {
    Messenger.getMessages().setText(message);
    try {
      Thread.sleep(5000L);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.exit(-1);
  }
  
  public static void mateAlert() {
    if (Rules.mate()) {
      JFrame frame = new JFrame();
      frame.setLayout(new java.awt.BorderLayout());
      frame.setSize(200, 130);
      Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
      frame.setLocation(width / 3 - getSizewidth / 3, height / 3 - getSizeheight / 3);
      JPanel panel = new JPanel();
      panel.setSize(200, 130);
      JButton exitButton = new JButton("OK");
      exitButton.addActionListener(new Exit());
      exitButton.setFocusPainted(false);
      panel.add(exitButton);
      panel.setBorder(new javax.swing.border.EmptyBorder(0, 10, 10, 10));
      frame.add(panel, "South");
      frame.setDefaultCloseOperation(0);
      JLabel label;
      if (Rules.inCheck(Rules.getTurn(), true)) {
        if (Rules.getTurn() % 2 == 0) {
          JLabel label = new JLabel("Checkmate. Black wins.", 0);
          if (Board.getMyTurn() == 0) {
            sendToServer(new String[] { "loss", Client.getUser() });
          } else {
            sendToServer(new String[] { "win", Client.getUser() });
          }
          sendToServer(new String[] { "win", "black" });
        } else {
          JLabel label = new JLabel("Checkmate. White wins.", 0);
          if (Board.getMyTurn() == 0) {
            sendToServer(new String[] { "win", Client.getUser() });
          } else
            sendToServer(new String[] { "loss", Client.getUser() });
        }
      } else {
        label = new JLabel("Stalemate.", 0);
        sendToServer(new String[] { "draw", Client.getUser() });
      }
      label.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 10, 0, 10));
      frame.add(label, "North");
      frame.setVisible(true);
      Rules.lock();
    }
  }
}
