import java.awt.Color;

public class GameManager implements Runnable {
  private GameData gameData;
  private DLList<ServerThread> serverThreads;
  private int id = 0;

  public GameManager() {
    serverThreads = new DLList<ServerThread>();
    gameData = new GameData();
  }

  public void addThread(ServerThread s) {
    serverThreads.add(s);
  }

  public synchronized void broadCastGameData() {
    for (int i = 0; i < serverThreads.size(); i++) {
      serverThreads.get(i).sendGameData(gameData);
    }
  }

  public void setGameData(GameData gd) {
    this.gameData = gd;
  }

  public void remove(ServerThread s) {
    serverThreads.remove(s);
  }

  public int addNewPlayer() {
    id++;

    int x = id*180 + 100;
    int y = 600;
    gameData.addNewPlayer(new Player(x,y,id));

    return id;
  }

  public void run() {
    while (true) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException ex) {
        Thread.currentThread().interrupt();
      }
    }
  }


public void removePlayer(int id) {
  gameData.removePlayer(id);

  for (int i = 0; i < serverThreads.size(); i++) {
    if (serverThreads.get(i).getMyID() == id) {
      serverThreads.remove(i);
      i--;
    }
  }
}
}
