import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;















































class ServerPanel
  extends JPanel
{
  public ServerPanel()
  {
    setSize(180, 120);
    setLayout(null);
    final JLabel label = new JLabel("Running");
    add(label);
    Insets insets = getInsets();
    Dimension size = new Dimension(120, 15);
    label.setBounds(62 + left, 20 + top, width, height);
    new Thread()
    {
      public void run() {
        try {
          for (;;) {
            label.setText(label.getText() + ".");
            if (label.getText().contains("....")) {
              label.setText("Running");
            }
            Thread.sleep(500L);
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }.start();
    JButton stopButton = new JButton("Stop");
    stopButton.addActionListener(new StopAction());
    stopButton.setFocusPainted(false);
    add(stopButton);
    insets = getInsets();
    size = stopButton.getPreferredSize();
    stopButton.setBounds(60 + left, 45 + top, width, height);
    setBorder(new EmptyBorder(20, 20, 20, 20));
  }
}
