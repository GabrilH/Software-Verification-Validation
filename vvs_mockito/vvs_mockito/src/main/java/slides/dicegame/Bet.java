package slides.dicegame;

/**
 * Bet placed by a player.
 * 
 * @author Eduardo Marques, DI/FCUL, 2013/14
 *
 */
public class Bet {

  private Player player;

  private int amount;
  
  public Bet(Player player, int amount) {
    this.player = player;
    this.amount = amount;
  }

  public Player getPlayer() {
    return player;
  }
  
  public int getAmount() {
    return amount;
  } 
}
