import java.io.Serializable;

public class Railroad extends Property {
  private int multiplier;
  public Railroad(String name, int price, int rent) {
    super(name, price, rent);
    multiplier = 0;
  }

  public void setRent() {rent = rent*multiplier;}
  public void setMultiplier() {
    if (owner.getRailroads().size() == 1) {
      multiplier = 1;
    }
    if (owner.getRailroads().size() == 2) {
      multiplier = 2;
    }
    if (owner.getRailroads().size() == 3) {
      multiplier = 4;
    }
    if (owner.getRailroads().size() == 4) {
      multiplier = 8;
    }
  }
}
