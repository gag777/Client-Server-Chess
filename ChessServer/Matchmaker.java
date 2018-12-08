import java.util.concurrent.LinkedBlockingQueue;

public class Matchmaker
{
  private LinkedBlockingQueue<StreamPair> queue;
  
  public Matchmaker() {}
  
  public void addConnection(StreamPair connection) {
    try {
      queue.put(connection);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  
  public void start()
  {
    queue = new LinkedBlockingQueue();
    new Thread()
    {
      public void run() {
        int counter = 0;
        try {
          for (;;) {
            if (counter % 3 == 0) System.out.println("Players in queue: " + queue.size());
            if ((queue.size() % 2 == 0) && (queue.size() > 0)) {
              System.out.println("Two connections in queue. Starting game...");
              new GameThread((StreamPair)queue.take(), (StreamPair)queue.take()).start();
            }
            Thread.sleep(1000L);
            counter++;
            if (counter == 1000) counter = 0;
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }.start();
  }
}
