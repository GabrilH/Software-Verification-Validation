package slides.dicegame;

public class Player {

	private final String name;
	private int money;

	public Player(String name, int money) {
		this.name = name;
		this.money = money;
	}

	public String getName() {
		return name;
	}

	public int getMoney() {
		return money;
	}
	
	public boolean take(int value) {
		if (value > money) {
			return false;
		} else {
			money -= value;
			return true;
		}
	}

	public void award(int value) {
		money += value;
	}

	public String toString() {
		return name + "/" + money;
	}
}
