package slides.dicegame;

import java.util.Random;


/**
 * The dice game.
 * 
 * @author Eduardo Marques, DI/FCUL, 2013/14.
 *
 */
public class Game {

	private final Random rng;  // Pseudo-random number generator.
	private final Bet[] bets;  // Placed bets.


	public Game() {
		// Not a test friendly constructor w.r.t. Dice,
		// as it instantiates the object directly.
		rng  = new Random();
		bets = new Bet[6];
	}

	// Test-friendly constructor.
	// It allows for dependency injection.
	public Game(Random r) {
		rng  = r;
		bets = new Bet[6];
	}

	public boolean bet(Player player, int diceValue, int amount) {
		if (amount <= 0) {
			System.out.printf("Invalid bet!");
			return false;
		}

		if (bets[diceValue - 1] != null) {
			System.out.printf("There is already a bet for %d! Sorry :(\n", diceValue);  
			return false;
		}

		if (!player.take(amount)) {
			System.out.printf("%s has %d euros and can not place a bet of %d euros!\n", 
					player.getName(), player.getMoney(),  amount);  
			return false;  // invalid bet: player has insufficient money
		}
		
		System.out.printf("Bet of %d euros placed by %s on %s!\n",
				amount, player.getName(), diceValue);
		
		bets[diceValue-1] = new Bet(player, amount);
		return true;
	}

	public void proceed() {
		// Compute total amount in bets.
		int total = 0;
		for (int i=0; i < bets.length; i++)
			if (bets[i] != null)
				total += bets[i].getAmount();

		if (total == 0) {
			System.out.printf("No bets placed!"); 
			return;
		}

		System.out.printf("Total bets: %d euros\n", total);

		// Roll the dice.
		int value = 1 + rng.nextInt(6);
		System.out.println("Dice has rolled: " + value + " !!");
		
		// See if there is a winner.
		Bet bet = bets[value - 1];

		if (bet != null) {
			// Award all money to the winner.
			Player player = bet.getPlayer();
			player.award(total);
			System.out.printf("The winner is %s!\n", player.getName());
		} else {
			System.out.println("No winners!");
		}

		// Reset bets for next round
		for (int i=0; i < bets.length; i++)
			bets[i] = null;
	}

	public static void main(String[] args) {
		Player a = new Player("Alberto", 100);
		Player b = new Player("Manuel", 100);
		Game g = new Game();
		
		System.out.printf("Player state: %s ; %s\n", a,b);
		g.bet(a, 5, 30); // a bets 30 euros on 5
		g.bet(b, 6, 50); // b bets 50 euros on 6
		g.proceed();
		System.out.printf("Player state: %s ; %s\n", a,b);
	}
}
