import java.io.Serializable;

public class Card implements Serializable {
  private String action;
  public Card(String action) {
    this.action = action;
  }
  public String getAction() {return action;}
  public String toString() {return action;}
}
