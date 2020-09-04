import java.io.Serializable;
import javax.swing.*;

public class GameData implements Serializable {
  private DLList<Player> players, turnOrder;
  private DLList<Token> tokens;
  private DLList<JButton> tokenButtons;
  private DLList<Card> communityCards, chanceCards;
  private Token thimble, hat, shoe, car, iron, ship;
  private boolean started, tokenSelectionFinished, orderSelected;
  private int numTokenSelected, currentTurnID, turnIndex;
  private HashMap<Integer, Location> locations;
  private HashMap<Integer, Space> gameboard;

  public GameData() {
    players = new DLList<Player>();
    turnOrder = new DLList<Player>();
    started = false;
    tokenSelectionFinished = false;
    orderSelected = false;
    currentTurnID = -1;
    turnIndex = 0;
    thimble = new Token("thimble", "thimble.png");
    hat = new Token("hat", "hat.png");
    shoe = new Token("shoe", "shoe.png");
    car = new Token("car", "car.png");
    iron = new Token("iron", "iron.png");
    ship = new Token("ship", "ship.png");

    tokens = new DLList<Token>();
    tokenButtons = new DLList<JButton>();

    tokens.add(thimble);
    tokens.add(hat);
    tokens.add(shoe);
    tokens.add(car);
    tokens.add(iron);
    tokens.add(ship);

    locations = new HashMap<Integer, Location>(41);

    int x = 1113;
    int y = 715;
    int diff = 62;
    int corner = 28;

    //setting up locations
    for (int i = 1; i < 41; i++) {
      if (i < 11) {
        locations.put(i, new Location(x,y));
        if (i == 1 || i == 10) {
          x -= (diff + corner);
        }
        else {
          x -= diff;
        }
      }
      else if (i >= 11 && i < 21) {
        locations.put(i, new Location(x,y));
        if (i == 11 || i == 20) {
          y -= (diff + corner);
        }
        else {
          y -= diff;
        }
      }
      else if (i >= 21 && i < 31) {
        locations.put(i, new Location(x,y));
        if (i == 21 || i == 30) {
          x += (diff + corner);
        }
        else {
          x += diff;
        }
      }
      else if (i >= 31 && i < 41) {
        locations.put(i, new Location(x,y));
        if (i == 31) {
          y += (diff + corner);
        }
        else {
          y += diff;
        }
      }
    }

    //set up game board spaces
    gameboard = new HashMap<Integer, Space>(41);
    gameboard.put(1, new Space("GO! Collect $200"));
    gameboard.put(2, new Property("Mediterranean Avenue", 60, 2));
    gameboard.put(3, new Space("Community Chest"));
    gameboard.put(4, new Property("Baltic Avenue", 60, 4));
    gameboard.put(5, new Space("Income Tax - Pay $200"));
    gameboard.put(6, new Railroad("Reading Railroad", 200, 25));
    gameboard.put(7, new Property("Oriental Avenue", 100, 6));
    gameboard.put(8, new Space("Chance"));
    gameboard.put(9, new Property("Vermont Avenue", 100, 6));
    gameboard.put(10, new Property("Connecticut Avenue", 120, 8));
    gameboard.put(11, new Space("Jail"));
    gameboard.put(12, new Property("St. Charles Place", 140, 10));
    gameboard.put(13, new Utility("Electric Company", 150, 0));
    gameboard.put(14, new Property("States Avenue", 140, 10));
    gameboard.put(15, new Property("Virginia Avenue", 160, 12));
    gameboard.put(16, new Railroad("Pennsylvania Railroad", 200, 25));
    gameboard.put(17, new Property("St. James Place", 180, 14));
    gameboard.put(18, new Space("Community Chest"));
    gameboard.put(19, new Property("Tennessee Avenue", 180, 14));
    gameboard.put(20, new Property("New York Avenue", 200, 16));
    gameboard.put(21, new Space("Free Parking"));
    gameboard.put(22, new Property("Kentucky Avenue", 220, 18));
    gameboard.put(23, new Space("Chance"));
    gameboard.put(24, new Property("Indiana Avenue", 220, 18));
    gameboard.put(25, new Property("Illinois Avenue", 240, 20));
    gameboard.put(26, new Railroad("B. & O. Railroad", 200, 25));
    gameboard.put(27, new Property("Atlantic Avenue", 260, 22));
    gameboard.put(28, new Property("Ventnor Avenue", 260, 22));
    gameboard.put(29, new Utility("Water Works", 150, 0));
    gameboard.put(30, new Property("Marvin Gardens", 280, 24));
    gameboard.put(31, new Space("Go to Jail"));
    gameboard.put(32, new Property("Pacific Avenue", 300, 26));
    gameboard.put(33, new Property("North Carolina Avenue", 300, 26));
    gameboard.put(34, new Space("Community Chest"));
    gameboard.put(35, new Property("Pennsylvania Avenue", 320, 28));
    gameboard.put(36, new Railroad("Short Line Railroad", 200, 25));
    gameboard.put(37, new Space("Chance"));
    gameboard.put(38, new Property("Park Place", 350, 35));
    gameboard.put(39, new Space("Luxury Tax - Pay $200"));
    gameboard.put(40, new Property("Park Place", 350, 35));

    communityCards = new DLList<Card>();
    //communityCards.add(new Card("Advance to \"Go\". (Collect $200)"));
    communityCards.add(new Card("Bank error in your favor. Collect $200."));
    communityCards.add(new Card("Doctor's fees. Pay $50."));
    communityCards.add(new Card("From sale of stock you get $50."));
    communityCards.add(new Card("Get Out of Jail Free. – This card may be kept until needed."));
    communityCards.add(new Card("Go to Jail. Go directly to Jail. Do not pass GO, do not collect $200."));
    communityCards.add(new Card("Grand Opera Night. Collect $50 from every player for opening night seats."));
    communityCards.add(new Card("Holiday fund matures. Receive $100."));
    communityCards.add(new Card("Income tax refund. Collect $20"));
    communityCards.add(new Card("It is your birthday. Collect $10 from every player."));
    communityCards.add(new Card("Life insurance matures – Collect $100"));
    communityCards.add(new Card("Hospital Fees. Pay $50."));
    communityCards.add(new Card("School fees. Pay $50."));
    communityCards.add(new Card("Receive $25 consultancy fee."));
    communityCards.add(new Card("You have won second prize in a beauty contest. Collect $10."));
    communityCards.add(new Card("You inherit $100."));
    shuffle(communityCards);

    chanceCards = new DLList<Card>();
    chanceCards.add(new Card("Advance to \"Go\". (Collect $200)"));
    chanceCards.add(new Card("Advance to Illinois Ave. If you pass Go, collect $200."));
    chanceCards.add(new Card("Advance to St. Charles Place. If you pass Go, collect $200."));
    chanceCards.add(new Card("Advance token to nearest Utility. If unowned, you may buy it from the Bank. If owned, throw dice and pay owner a total 10 times the amount thrown."));
    chanceCards.add(new Card("Advance token to the nearest Railroad and pay owner twice the rental to which he/she is otherwise entitled. If Railroad is unowned, you may buy it from the Bank."));
    chanceCards.add(new Card("Advance token to the nearest Railroad and pay owner twice the rental to which he/she is otherwise entitled. If Railroad is unowned, you may buy it from the Bank."));
    chanceCards.add(new Card("Bank pays you dividend of $50."));
    chanceCards.add(new Card("Get out of Jail Free. This card may be kept until needed."));
    chanceCards.add(new Card("Go Back Three Spaces."));
    chanceCards.add(new Card("Go to Jail. Go directly to Jail. Do not pass GO, do not collect $200."));
    chanceCards.add(new Card("Pay poor tax of $15."));
    chanceCards.add(new Card("Take a trip to Reading Railroad. If you pass Go, collect $200."));
    chanceCards.add(new Card("Take a walk on the Boardwalk. Advance token to Boardwalk."));
    chanceCards.add(new Card("You have been elected Chairman of the Board. Pay each player $50."));
    chanceCards.add(new Card("Your building loan matures. Receive $150."));
    chanceCards.add(new Card("You have won a crossword competition. Collect $100."));
    shuffle(chanceCards);
  }

  public void addNewPlayer(Player p) {
    players.add(p);
  }

  public void removePlayer(int id) {
    for (int i = 0; i < players.size(); i++) {
      if (id == players.get(i).getID()) {
        players.remove(i);
        i--;
      }
    }
  }

  public DLList<Player> getPlayers() {return players;}
  public DLList<Token> getTokens() {return tokens;}
  public HashMap<Integer,Location> getLocations() {return locations;}
  public HashMap<Integer, Space> getGameboard() {return gameboard;}
  public DLList<Card> getCommunityDeck() {return communityCards;}
  public DLList<Card> getChanceDeck() {return chanceCards;}

  public void shuffle(DLList<Card> deck) {
    DLList<Card> temp = new DLList<Card>();
    int n = deck.size() + 1;
    int s = deck.size();
    for (int i = 0; i < s; i++) {
      int random = (int) (Math.random() * n);
      Card c = (Card) deck.remove(random);
      if (c != null)
        temp.add(c);
      n--;
    }
    for (int i = 0; i < temp.size(); i++) {
      Card c = (Card) temp.get(i);
      deck.add(c);
    }
  }

  public Card topCommunity() {
    Card top = (Card) communityCards.get(0);
    communityCards.remove(top);
    return top;
  }

  public void returnCommunity(Card c) {
    communityCards.add(c);
  }

  public Card topChance() {
    Card top = (Card) chanceCards.get(0);
    chanceCards.remove(top);
    return top;
  }

  public void returnChance(Card c) {
    chanceCards.add(c);
  }

  public boolean getStartStatus() {return started;}
  public boolean getTokenSelectionFinishedStatus() {return tokenSelectionFinished;}
  public boolean getOrderSelected() {return orderSelected;}

  public void start() {
    started = true;
    for (int i = 0; i < players.size(); i++) {
      players.get(i).setLocation(locations.get(1));
    }
  }


  public void assignToken(int id, int index) {
    players.get(id - 1).setToken(tokens.get(index));
    tokens.get(index).setPlayer(players.get(id - 1));
    numTokenSelected++;
    if (numTokenSelected == players.size()) {
      tokenSelectionFinished = true;
    }
  }

  public void addOrder(int index) {
    players.get(index).firstRoll();
    if (turnOrder.size() < players.size()) {
      if (turnOrder.contains(players.get(index)) == false) {
        turnOrder.add(players.get(index));
      }
    }
  }

  public void assignOrder() {
    if (turnOrder.size() == players.size()) {
      for (int i = 0; i < turnOrder.size()-1; i++) {
        for (int j = (i+1); j < turnOrder.size(); j++) {
          if (turnOrder.get(j).getFirstRoll() > turnOrder.get(i).getFirstRoll()) {
            Player temp = turnOrder.get(i);
            turnOrder.set(i, turnOrder.get(j));
            turnOrder.set(j, temp);
          }
        }
      }
      currentTurnID = turnOrder.get(turnIndex).getID();
      orderSelected = true;
    }
  }

  public String getOrder() {
    String string = "";
    for (int i = 0; i < turnOrder.size(); i++) {
      string += turnOrder.get(i).getID();
      if (i != (turnOrder.size() - 1)) {
        string += ", ";
      }
    }
    return string;
  }

  public int getCurrentTurnID() {return currentTurnID;}
  public void nextTurn() {
    // takeAction();
    turnIndex++;
    if (turnIndex >= turnOrder.size()) {
      turnIndex = 0;
    }
    currentTurnID = turnOrder.get(turnIndex).getID();
  }

  public void finishedRolled(int index) {
    players.get(index).setRolled(false);
  }

  public void playerCollect200(int index) {
    players.get(index).collect200();
  }

  public void payTax(int index) {
    players.get(index).pay(200);
  }

  public void addProperty(int index, Property property) {
    players.get(index).buyProperty(property);
  }

  public void payRent(int renterIndex, Property property) {
    Player renter = players.get(renterIndex);
    Player owner = property.getOwner();
    renter.pay(property.getRent());
    owner.receive(property.getRent());
    renter.setPaidRent(true);
  }

}
