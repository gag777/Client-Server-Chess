import java.io.ObjectOutputStream;

public class StreamPair {
  private java.io.ObjectInputStream in;
  private ObjectOutputStream out;
  
  public java.io.ObjectInputStream getInputStream() {
    return in;
  }
  
  public ObjectOutputStream getOutputStream() {
    return out;
  }
  
  public StreamPair(java.io.ObjectInputStream in, ObjectOutputStream out) {
    this.in = in;
    this.out = out;
  }
}
