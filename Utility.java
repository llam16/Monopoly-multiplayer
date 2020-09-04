import java.io.Serializable;

public class Utility extends Property {
  private int multiplier;
  public Utility(String name, int price, int rent) {
    super(name, price, rent);
    multiplier = 0;
  }

  public void setRent() {rent = owner.getDiceRoll()*multiplier;}
  public void setMultiplier() {
    if (owner.getUtilities().size() == 1) {
      multiplier = 4;
    }
    if (owner.getUtilities().size() == 2) {
      multiplier = 10;
    }
  }
}
