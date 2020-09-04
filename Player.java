import java.io.Serializable;
import java.awt.Color;

public class Player implements Serializable {
  private int x, y, id, money, diceRoll, firstRoll, currentSpaceNum;
  private Token token;
  private Color color;
  private Location location;
  private boolean rolled, purchased, paidRent, declinedPurchase, landedChance, landedCommunity;
  private DLList<Property> properties;
  private DLList<Railroad> railroads;
  private DLList<Utility> utilities;
  private DLList<Card> cards;
  private DLList<Integer> ownedSpacesNums;

  public Player(int x, int y, int id) {
    this.x = x;
    this.y = y;
    this.id = id;
    if (id == 1) {
      color = new Color(235, 19, 19);
    }
    if (id == 2) {
      color = new Color(25, 136, 255);
    }
    if (id == 3) {
      color = new Color(2, 196, 83);
    }
    if (id == 4) {
      color = new Color(255, 213, 0);
    }
    location = null;
    properties = new DLList<Property>();
    railroads = new DLList<Railroad>();
    utilities = new DLList<Utility>();
    ownedSpacesNums = new DLList<Integer>();
    money = 1500;
    diceRoll = 0;
    firstRoll = 0;
    currentSpaceNum = 1;
    rolled = false;
    purchased = false;
    declinedPurchase = false;
    landedChance = false;
    landedCommunity = false;
  }

  public DLList<Property> getProperties() {return properties;}
  public DLList<Railroad> getRailroads() {return railroads;}
  public DLList<Utility> getUtilities() {return utilities;}
  public DLList<Integer> getOwnedSpaceNums() {return ownedSpacesNums;}

  public int getID() {return id;}
  public int getMoney() {return money;}
  public int getCurrentSpaceNum() {return currentSpaceNum;}
  public int getX() {return x;}
  public int getY() {return y;}
  public Color getColor() {return color;}
  public Token getToken() {return token;}

  public void setToken(Token t) {token = t;}
  public void setLocation(Location l) {
    location = l;
    x = location.getX();
    y = location.getY();
  }

  public String toString() {return "I am Player " + id;}

  public void firstRoll() {
    firstRoll = (int) (Math.random() * 12 + 1);
  }
  public int getFirstRoll() {return firstRoll;}
  public boolean getRolled() {return rolled;}
  public void setRolled(boolean rolled) {this.rolled = rolled;}

  public int rollDice() {
    diceRoll = (int) (Math.random() * 12 + 1);
    currentSpaceNum += diceRoll;
    if (currentSpaceNum > 40) {
      currentSpaceNum -= 40;
      collect200();
    }
    rolled = true;
    return diceRoll;
  }
  public int getDiceRoll() {return diceRoll;}

  public boolean checkBankrupt() {return (money < 0);}

  public void buyProperty(Property p) {
    if (money >= p.getPrice()) {
      properties.add(p);
      money -= p.getPrice();
      ownedSpacesNums.add(currentSpaceNum);
      if (p instanceof Railroad) {
        Railroad r = (Railroad) p;
        railroads.add(r);
      }
      if (p instanceof Utility) {
        Utility u = (Utility) p;
        utilities.add(u);
      }
    }
  }

  public void pay(int amount) {
    money -= amount;
  }

  public void collect200() {
    money += 200;
  }

  public void receive(int amount) {
    money += amount;
  }

  public boolean checkPurchased() {return purchased;}
  public void setPurchased(boolean purchased) {this.purchased = purchased;}

  public boolean checkPaidRent() {return paidRent;}
  public void setPaidRent(boolean paidRent) {this.paidRent = paidRent;}

  public boolean checkDeclinedPurchase() {return declinedPurchase;}
  public void setDeclinedPurchase(boolean declinedPurchase) {this.declinedPurchase = declinedPurchase;}

  public boolean checkLandedChance() {return landedChance;}
  public boolean checkLandedCommunity() {return landedCommunity;}

  public void setLandedChance(boolean landedChance) {this.landedChance = landedChance;}
  public void setLandedCommunity(boolean landedCommunity) {this.landedCommunity = landedCommunity;}
}
