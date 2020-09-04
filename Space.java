import java.io.Serializable;

public class Space implements Serializable {
  protected String name;
  protected Location location;

  public Space(String name) {
    this.name = name;
  }
  public void setName(String name) {this.name = name;}
  public String getName() {return name;}
  public String toString() {return name;}
  public int hashCode() {
    return name.hashCode();
    //return name.hashCode() + location.getX() + location.getY();
  }
}
