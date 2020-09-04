import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Screen extends JPanel implements ActionListener {

  private ObjectOutputStream out;
  private int myID, playerIndex;
  private GameData gameData;
	private int screenW, screenH;

	private JButton loadingButton, playButton, quitButton, diceButton, buyButton, payButton, noButton, endTurnButton, okButton;
  private DLList<JButton> tokenButtons;

  private BufferedImage gameBoardImage;

  public Screen() {
    this.setLayout(null);
      myID = -1;
      playerIndex = -1;
			screenW = 1200;
			screenH = 800;
      setFocusable(true);

			playButton = createOptionButton(playButton, "PLAY", 555, 485);
      tokenButtons = new DLList<JButton>();

      loadingButton = createOptionButton(loadingButton, "LOADING", 555, 400);
      loadingButton.setVisible(true);

      try {
        gameBoardImage = ImageIO.read(new File("gameboard.jpg"));
        gameBoardImage = resize(gameBoardImage, 760,760);
      } catch (IOException e) {
        e.printStackTrace();
      }

      diceButton = createTokenButton(diceButton, "dice.png", 30, 600);
      quitButton = createOptionButton(quitButton, "QUIT", 30, 730);
      buyButton = createOptionButton(buyButton, "BUY", 30, 360);
      payButton = createOptionButton(payButton, "PAY", 30, 330);
      noButton = createOptionButton(noButton, "NO", 160, 360);
      endTurnButton = createOptionButton(endTurnButton, "END TURN", 240,540);
      okButton = createOptionButton(okButton, "OK", 30, 330);
  }

  public Dimension getPreferredSize() {
    return new Dimension(screenW, screenH);
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
		g.setColor(new Color(209, 243, 255));
		g.drawRect(0, 0, screenW, screenH);
		g.fillRect(0, 0, screenW, screenH);

    if(gameData != null) {
      loadingButton.setVisible(false);
      DLList<Player> players = gameData.getPlayers();
			if (gameData.getStartStatus() == false) {
        playButton.setVisible(true);
				try {
	        BufferedImage logo = ImageIO.read(new File("logo.png"));
					logo = resize(logo, 200,800);
	        g.drawImage(logo, 200, 200, null);
	      } catch (IOException e) {
	        e.printStackTrace();
	      }

				for (int i = 0; i < players.size(); i++) {
	        int x = players.get(i).getX();
	        int y = players.get(i).getY();
	        int id = players.get(i).getID();

					if (id == 1) {
						g.setColor(new Color(235, 19, 19));
					}
					if (id == 2) {
						g.setColor(new Color(25, 136, 255));
					}
					if (id == 3) {
						g.setColor(new Color(2, 196, 83));
					}
					if (id == 4) {
						g.setColor(new Color(255, 213, 0));
					}
					g.setFont(new Font("Helvetica", Font.BOLD, 20));
					g.drawString("PLAYER " + id, x, y);

	      }
			}
			else {
        if (gameData.getTokenSelectionFinishedStatus() == false) {
          loadingButton.setVisible(false);
  				playButton.setVisible(false);
          g.setColor(Color.BLACK);
          g.setFont(new Font("Helvetica", Font.BOLD, 50));
          g.drawString("INSTRUCTIONS", 420, 60);
          g.setFont(new Font("Helvetica", Font.BOLD, 30));
          g.drawString("1) Try to become the wealthiest player through aquiring properties and $$$", 30, 120);
          g.drawString("2) Every player starts at GO with $1500 (for purchasing or paying rent)", 30, 160);
            g.drawString("3) Each player will roll the dice (CLICK DICE BUTTON) to see who goes first", 30, 200);
          g.drawString("4) When landing on a space, a player can buy the property or will have to follow ", 30, 240);
          g.drawString("    the space's instruction (pay rent, go to jail, draw community or chance)", 30, 280);
          g.drawString("    CLICK on the buttons that appear to complete an action", 30, 320);
          g.drawString("5) Collect $200 every time you pass GO (will automatically add)", 30, 360);
          g.drawString("6) If you would like to quit and end the game before going bankrupt, press QUIT", 30, 400);
          g.drawString("Now pick your token to start playing!", 350, 560);
          g.setColor(gameData.getPlayers().get(playerIndex).getColor());
          g.drawString("PLAYER " + myID, 520, 770);

          DLList<Token> tokens = gameData.getTokens();
          for (int i = 0; i < tokens.size(); i++) {
            tokenButtons.get(i).setVisible(true);
            if (tokens.get(i).getPlayer() != null) {
              tokenButtons.get(i).setBackground(tokens.get(i).getPlayer().getColor());
              tokenButtons.get(i).removeActionListener(this);
            }
          }
        }
        else {
          if (gameData.getTokenSelectionFinishedStatus() == true) {
            try{
              Thread.sleep(2000); //millisecond
            }catch(InterruptedException ex){
            	Thread.currentThread().interrupt();
            }
            for (int i = 0; i < tokenButtons.size(); i++) {
              tokenButtons.get(i).setVisible(false);
            }
            Player thisPlayer = gameData.getPlayers().get(playerIndex);
            g.setColor(thisPlayer.getColor());
            g.setFont(new Font("Helvetica", Font.BOLD, 50));
            g.drawString("PLAYER " + myID + ":", 30, 60);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Helvetica", Font.BOLD, 30));
            g.drawString("Money: $" + thisPlayer.getMoney(), 30, 130);
            g.drawString("Properties: " + thisPlayer.getProperties().size(), 30, 180);
            g.drawImage(gameBoardImage, 420, 20, null);
            diceButton.setVisible(true);
            quitButton.setVisible(true);

            BufferedImage tokenImg = null;
            try {
              tokenImg = ImageIO.read(new File(thisPlayer.getToken().getUrl()));
              tokenImg = resize(tokenImg, 50,50);
            } catch (IOException ex) {
              ex.printStackTrace();
            }
            g.drawImage(tokenImg, 300, 20, null);

            g.setColor(Color.BLACK);
            g.setFont(new Font("Helvetica", Font.BOLD, 20));

            if (gameData.getOrderSelected() == false) {
              g.drawString("Roll to see who goes first!", 30, 300);
              if (thisPlayer.getFirstRoll() != 0) {
                g.drawString("First roll: " + thisPlayer.getFirstRoll(), 30, 340);
              }
            }
            else {
              if (gameData.getOrderSelected() == true) {
                g.drawString("Current turn: " + gameData.getCurrentTurnID(), 30, 560);
                g.drawString("Player order: " + gameData.getOrder(), 30, 590);
              }

              System.out.println();
            }

            if (gameData.getCurrentTurnID() == myID) {
              endTurnButton.setVisible(true);
              if (thisPlayer.getRolled() == true) {
                  g.drawString("You rolled: " + thisPlayer.getDiceRoll(), 30, 270);

                  if (gameData.getGameboard().get(thisPlayer.getCurrentSpaceNum()) instanceof Property) {
                    Property property = (Property) gameData.getGameboard().get(thisPlayer.getCurrentSpaceNum());
                    if (property.getOwner() == null) {
                      if (thisPlayer.checkDeclinedPurchase() == false) {
                        g.drawString("Would you like to buy ", 30, 300);
                        g.drawString(property.toString() +"?", 30, 330);
                      }
                    }
                    else if (property.getOwner().getID() != myID && thisPlayer.checkPaidRent() == false){
                      g.drawString("Pay rent of $" + property.getRent() + " to Player " + property.getOwner().getID(), 30, 300);
                    }

                  }
                  else if (gameData.getGameboard().get(thisPlayer.getCurrentSpaceNum()) instanceof Space) {

                    g.drawString("You landed on ", 30, 300);
                    g.drawString(gameData.getGameboard().get(thisPlayer.getCurrentSpaceNum()).toString(), 30, 330);
                    if (gameData.getGameboard().get(thisPlayer.getCurrentSpaceNum()).getName().equals("Chance")) {
                      g.drawString(gameData.topChance().toString(), 30, 360);
                      //System.out.println(gameData.getChanceDeck().get(gameData.getPlayers().get(playerIndex).getCurrentSpaceNum()).getAction());
                    }
                    else if (gameData.getGameboard().get(thisPlayer.getCurrentSpaceNum()).getName().equals("Community Chest")) {
                      g.drawString(gameData.topCommunity().toString(), 30, 360);
                      //System.out.println(gameData.getCommunityDeck().get(gameData.getPlayers().get(playerIndex).getCurrentSpaceNum()).getAction());
                    }
                  }
              }
            }
            else {
              endTurnButton.setVisible(false);
            }


            for (int i = 0; i < gameData.getPlayers().size(); i++) {
              Player p = gameData.getPlayers().get(i);
              if (p.getID() != myID) {
                BufferedImage t = null;
                try {
                  t = ImageIO.read(new File(p.getToken().getUrl()));
                  t = resize(t, 50,50);
                } catch (IOException ex) {
                  ex.printStackTrace();
                }
                g.drawImage(t, p.getX(), p.getY(), null);
              }
              if (p.getProperties().size() > 0) {
                HashMap<Integer, Location> map = gameData.getLocations();
                DLList<Integer> spaceNums = p.getOwnedSpaceNums();

                int x = 0;
                int y = 0;
                int diff = 42;
                int length = 50;
                int width = 5;

                g.setColor(p.getColor());

                for (int j = 0; j < spaceNums.size(); j++) {
                  int num = spaceNums.get(j);
                  x = map.get(num).getX();
                  y = map.get(num).getY();
                  if (num < 11) {
                    y -= diff;
                    g.drawRect(x,y,length,width);
                    g.fillRect(x,y,length,width);
                  }
                  else if (num >= 11 && num < 21) {
                    x += diff +100;
                    g.drawRect(x,y,width,length);
                    g.fillRect(x,y,width,length);
                  }
                  else if (num >= 21 && num < 31) {
                    y += diff +100;
                    g.drawRect(x,y,length,width);
                    g.fillRect(x,y,length,width);
                  }
                  else if (num >= 31 && num < 41) {
                    x -= diff;
                    g.drawRect(x,y,width,length);
                    g.fillRect(x,y,width,length);
                  }

                }
              }
            }

            g.drawImage(tokenImg, thisPlayer.getX(), thisPlayer.getY(), null);
          }
        }
			}
    }

  }

	public BufferedImage resize(BufferedImage img, int height, int width) {
		Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = resized.createGraphics();
			g2d.drawImage(tmp, 0, 0, null);
			g2d.dispose();
			return resized;
	}

	public JButton createOptionButton(JButton button, String text, int xPos, int yPos) {
			button = new JButton(text);
			button.setBounds(xPos, yPos, 100, 30);
			button.setBackground(new Color(209, 0, 0));
			button.setForeground(Color.white);
			button.setVisible(false);
			button.addActionListener(this);
			this.add(button);
			return button;
	}

  public JButton createTokenButton(JButton button, String url, int xPos, int yPos) {
      button = new JButton();
      button.setBounds(xPos, yPos, 110, 110);
      button.setBackground(new Color(143, 157, 235));
      BufferedImage img = null;
      try {
        img = ImageIO.read(new File(url));
        img = resize(img, 100,100);
      } catch (IOException ex) {
        ex.printStackTrace();
      }
      button.setIcon(new ImageIcon(img));
      button.setVisible(false);
      button.addActionListener(this);
      this.add(button);
      return button;
  }

  public void assignToken(int index) {
    if (gameData.getPlayers().get(playerIndex).getToken() == null) {
      gameData.assignToken(myID, index);
      System.out.println(gameData.getPlayers().get(playerIndex).getToken() + " selected");
    }
    else {
      System.out.println(gameData.getPlayers().get(playerIndex).getToken() + " token already selected");
    }
    DLList<Token> tokens = gameData.getTokens();
    for (int i = 0; i < tokens.size(); i++) {
      tokenButtons.get(i).setVisible(true);
      if (tokens.get(i).getPlayer() != null) {
        tokenButtons.get(i).setBackground(tokens.get(i).getPlayer().getColor());
        tokenButtons.get(i).removeActionListener(this);
      }
    }
  }

  public void actionPerformed(ActionEvent e) {
		if (e.getSource() == playButton) {
			gameData.start();

		}
    if (e.getSource() == tokenButtons.get(0)) {
      assignToken(0);
      System.out.println(" 0");
    }
    if (e.getSource() == tokenButtons.get(1)) {
      assignToken(1);
      System.out.println(" 1");
    }
    if (e.getSource() == tokenButtons.get(2)) {
      assignToken(2);
      System.out.println(" 2");
    }
    if (e.getSource() == tokenButtons.get(3)) {
      assignToken(3);
      System.out.println(" 3");
    }
    if (e.getSource() == tokenButtons.get(4)) {
      assignToken(4);
      System.out.println(" 4");
    }
    if (e.getSource() == tokenButtons.get(5)) {
      assignToken(5);
      System.out.println(" 5");
    }
    if (e.getSource() == diceButton) {

      gameData.getPlayers().get(playerIndex).setDeclinedPurchase(false);
      if (gameData.getOrderSelected() == false) {
        gameData.addOrder(playerIndex);
        gameData.assignOrder();
      }
      else if (myID == gameData.getCurrentTurnID()) {
        if (gameData.getPlayers().get(playerIndex).getRolled() == false) {
          endTurnButton.setVisible(false);
          gameData.getPlayers().get(playerIndex).rollDice();
          System.out.println(gameData.getPlayers().get(playerIndex).getCurrentSpaceNum());
          gameData.getPlayers().get(playerIndex).setLocation(gameData.getLocations().get(gameData.getPlayers().get(playerIndex).getCurrentSpaceNum()));
          if (gameData.getPlayers().get(playerIndex).getCurrentSpaceNum() == 1) {
            gameData.playerCollect200(playerIndex);
          }
          if (gameData.getPlayers().get(playerIndex).getCurrentSpaceNum() == 2 || gameData.getPlayers().get(playerIndex).getCurrentSpaceNum() == 2) {
            gameData.payTax(playerIndex);
          }
          if (gameData.getGameboard().get(gameData.getPlayers().get(playerIndex).getCurrentSpaceNum()) instanceof Property) {
            Property property = (Property) gameData.getGameboard().get(gameData.getPlayers().get(playerIndex).getCurrentSpaceNum());
            if (property.getOwner() == null) {
              buyButton.setVisible(true);
              noButton.setVisible(true);
            }
            else if (property.getOwner().getID() != myID && gameData.getPlayers().get(playerIndex).checkPaidRent() == false){
              buyButton.setVisible(false);
              noButton.setVisible(false);
              payButton.setVisible(true);
            }
          }
          // else {
          //   if (gameData.getGameboard().get(thisPlayer.getCurrentSpaceNum()).getName().equals("Chance")) {
          //     //okButton.setVisible(true);
          //     //System.out.println(gameData.getChanceDeck().get(gameData.getPlayers().get(playerIndex).getCurrentSpaceNum()).getAction());
          //   }
          //   else if (gameData.getGameboard().get(thisPlayer.getCurrentSpaceNum()).getName().equals("Community Chest")) {
          //     //System.out.println(gameData.getCommunityDeck().get(gameData.getPlayers().get(playerIndex).getCurrentSpaceNum()).getAction());
          //     //okButton.setVisible(false);
          //   }
          // }
        }
        else {
          endTurnButton.setVisible(true);
        }
      }

    }
    if (e.getSource() == buyButton) {
      Property property = (Property) gameData.getGameboard().get(gameData.getPlayers().get(playerIndex).getCurrentSpaceNum());
      property.setOwner(gameData.getPlayers().get(playerIndex));
      gameData.addProperty(playerIndex, property);
      buyButton.setVisible(false);
      noButton.setVisible(false);
      endTurnButton.setVisible(true);
      //gameData.finishedRolled(playerIndex);
    }
    if (e.getSource() == noButton) {
      buyButton.setVisible(false);
      noButton.setVisible(false);
      endTurnButton.setVisible(true);
      gameData.getPlayers().get(playerIndex).setDeclinedPurchase(true);
      //gameData.finishedRolled(playerIndex);
    }
    if (e.getSource() == endTurnButton) {
      gameData.nextTurn();
      System.out.println("Player " + myID + " ended turn");
      System.out.println("Current turn now is Player " + gameData.getCurrentTurnID());
      gameData.finishedRolled(playerIndex);
      gameData.getPlayers().get(playerIndex).setPaidRent(false);
      gameData.getPlayers().get(playerIndex).setDeclinedPurchase(false);
    }
    if (e.getSource() == payButton) {
      Property property = (Property) gameData.getGameboard().get(gameData.getPlayers().get(playerIndex).getCurrentSpaceNum());
      gameData.payRent(playerIndex, property);
      payButton.setVisible(false);
    }
    // if (e.getSource() = okButton) {
    //   if (gameData.getGameboard().get(gameData.getPlayers().get(playerIndex).getCurrentSpaceNum()).getName().equals("Chance")) {
    //     //gameData.getPlayers().get(playerIndex).setLandedChance(true);
    //     //System.out.println(gameData.getChanceDeck().get(gameData.getPlayers().get(playerIndex).getCurrentSpaceNum()).getAction());
    //   }
    //   else if (gameData.getGameboard().get(gameData.getPlayers().get(playerIndex).getCurrentSpaceNum()).getName().equals("Community Chest")) {
    //     //System.out.println(gameData.getCommunityDeck().get(gameData.getPlayers().get(playerIndex).getCurrentSpaceNum()).getAction());
    //   }
    // }
    if (e.getSource() == quitButton) {
      System.exit(0);
    }
		try{
			out.reset();
			out.writeObject(gameData);
		} catch (IOException ex) {
						System.out.println(ex);
		}
		repaint();
  }

  @SuppressWarnings("unchecked")
  public void poll() throws IOException {
    String hostName = "localhost";
    int portNumber = 1024;
    Socket serverSocket = new Socket(hostName, portNumber);

    out = new ObjectOutputStream(serverSocket.getOutputStream());
    ObjectInputStream in = new ObjectInputStream(serverSocket.getInputStream());

    try {
      out.writeObject("Hello from new player");

      myID = (Integer) in.readObject();
      System.out.println("my id is " + myID);

      while (true) {
        gameData = (GameData) in.readObject();

        DLList<Player> players = gameData.getPlayers();
        for (int i = 0; i < players.size(); i++) {
          int id = players.get(i).getID();
          if (id == myID) {
            playerIndex = i;
            break;
          }
        }

        if (tokenButtons.size() == 0) {
          DLList<Token> tokens = gameData.getTokens();

          int x = 200;
          int y = 600;

          for(int i = 0; i < tokens.size(); i++) {
            tokenButtons.add(createTokenButton(new JButton(), tokens.get(i).getUrl(), x, y));
            x+=140;
          }
        }
        if (players.get(playerIndex).checkBankrupt() == true) {
          System.exit(0);
        }


        repaint();
      }
    } catch (UnknownHostException e) {
      System.err.println("Unknown host: " + hostName);
      System.exit(1);
    } catch (IOException e) {
      System.err.println("Couldn't get I/O for the connection to " + hostName);
      System.exit(1);
    } catch (ClassNotFoundException e) {
      System.err.println(e);
      System.exit(1);
    }
  }

}
