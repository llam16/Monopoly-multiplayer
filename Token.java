import java.io.*;
import java.awt.*;

public class Token implements Serializable {
  private String name, imgUrl;
  private Player player;
  private boolean selected;

  public Token(String name, String imgUrl) {
    this.name = name;
    this.imgUrl = imgUrl;
    selected = false;
    player = null;
  }

  public String toString() {return name;}
  public String getUrl() {return imgUrl;}
  public boolean isSelected() {return selected;}

  public Player getPlayer() {return player;}
  public void setPlayer(Player p) {
    player = p;
    selected = true;
  }

}
