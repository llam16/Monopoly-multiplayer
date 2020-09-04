import java.io.Serializable;

public class Property extends Space {
  protected Player owner;
  protected int price;
  protected int rent;

  public Property(String name, int price, int rent) {
    super(name);
    this.price = price;
    this.rent = rent;
    owner = null;
  }

  public int getPrice() {return price;}
  public int getRent() {return rent;}
  public Player getOwner() {return owner;}

  public void setOwner(Player owner) {this.owner = owner;}
}
