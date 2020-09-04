import java.io.Serializable;

public class Location implements Serializable {
  private int x,y;
  public Location(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {return x;}
  public int getY() {return y;}

  public String toString() {return "location " + x +": " + y;}
}
