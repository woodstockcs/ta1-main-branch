import java.util.Scanner;

/** Text adventure game. */
public class TextAdventure {

	// public static void main(String[] args) {
	// 	new TextAdventure().run();
	// }

	/** Damage done by the best weapon the player has picked up. */
	private int bestWeaponDamage;

	/** The room where the player currently is. */
	private Room currentRoom;

	/** Total value of all treasures taken by the player. */
	private int score;

	public TextAdventure() {
		// Create rooms
		Room entrance = new Room("entrance",
				"a cramped natural passage, filled with dripping stalactites");
		Room hall = new Room("hall", "a vast hall with a vaulted stone ceiling");
		Room armory = new Room("armory", "an abandoned armory");
		Room lair = new Room("lair",
				"a large cavern, steaming with volcanic heat");
		// Create connections
		entrance.addNeighbor("north", hall);
		hall.addNeighbor("south", entrance);
		hall.addNeighbor("west", armory);
		armory.addNeighbor("east", hall);
		hall.addNeighbor("east", lair);
		lair.addNeighbor("west", hall);
		// Create and install monsters
		hall.setMonster(new Monster("wolf", 2, "a ferocious, snarling wolf"));
		lair.setMonster(new Monster("dragon", 4, "a fire-breathing dragon"));
		// Create and install treasures
		hall.setTreasure(new Treasure("diamond", 10,
				"a huge, glittering diamond"));
		lair.setTreasure(new Treasure("chalice", 90,
				"a golden chalice encrusted with all manner of gemstones"));
		// Create and install weapons
		armory.setWeapon(new Weapon("axe", 5, "a mighty battle axe"));
		// The player starts in the entrance, armed with a sword
		currentRoom = entrance;
		bestWeaponDamage = 3;
	}

	/**
	 * Attacks the specified monster and prints the result. If the monster is
	 * present, this either kills it (if the player's weapon is good enough) or
	 * results in the player's death (and the end of the game).
	 */
	public void attack(String name) {
		Monster monster = currentRoom.getMonster();
		if (monster != null && monster.getName().equals(name)) {
			if (bestWeaponDamage > monster.getArmor()) {
				System.out.println("You strike it dead!");
				currentRoom.setMonster(null);
			} else {
				System.out.println("Your blow bounces off harmlessly.");
				System.out.println("The " + monster.getName() + " eats your head!");
				System.out.println();
				System.out.println("GAME OVER");
				System.exit(0);
			}
		} else {
			System.out.println("There is no " + name + " here.");
		}
	}

	/** Moves in the specified direction, if possible. */
	public void go(String direction) {
		Room destination = currentRoom.getNeighbor(direction);
		if (destination == null) {
			System.out.println("You can't go that way from here.");
		} else {
			currentRoom = destination;
		}
	}

	/** Handles a command read from standard input. */
	public void handleCommand(String line) {
		String[] words = line.split(" ");
		if (currentRoom.getMonster() != null
				&& !(words[0].equals("attack") || words[0].equals("look"))) {
			System.out.println("You can't do that with unfriendlies about.");
			listCommands();
		} else if (words[0].equals("attack")) {
			attack(words[1]);
		} else if (words[0].equals("go")) {
			go(words[1]);
		} else if (words[0].equals("look")) {
			look();
		} else if (words[0].equals("take")) {
			take(words[1]);
		} else {
			System.out.println("I don't understand that.");
			listCommands();
		}
	}

	/** Prints examples of possible commands as a hint to the player. */
	public void listCommands() {
		System.out.println("Examples of commands:");
		System.out.println("  attack wolf");
		System.out.println("  go north");
		System.out.println("  look");
		System.out.println("  take diamond");
	}

	/** Prints a description of the current room and its contents. */
	public void look() {
		System.out.println("You are in " + currentRoom.getDescription() + ".");
		if (currentRoom.getMonster() != null) {
			System.out.println("There is "
					+ currentRoom.getMonster().getDescription() + " here.");
		}
		if (currentRoom.getWeapon() != null) {
			System.out.println("There is "
					+ currentRoom.getWeapon().getDescription() + " here.");
		}
		if (currentRoom.getTreasure() != null) {
			System.out.println("There is "
					+ currentRoom.getTreasure().getDescription() + " here.");
		}
		System.out.println("Exits: " + currentRoom.listExits());
	}

	/** Runs the game. */
	public void run() {
    Scanner s = new Scanner(System.in);
		listCommands();
		System.out.println();
		while (true) {
			System.out.println("You are in the " + currentRoom.getName() + ".");
			System.out.print("> ");
			handleCommand(s.nextLine());
			System.out.println();
		}
	}

	/** Attempts to pick up the specified treasure or weapon. */
	public void take(String name) {
		Treasure treasure = currentRoom.getTreasure();
		Weapon weapon = currentRoom.getWeapon();
		if (treasure != null && treasure.getName().equals(name)) {
			currentRoom.setTreasure(null);
			score += treasure.getValue();
			System.out.println("Your score is now " + score + " out of 100.");
			if (score == 100) {
				System.out.println();
				System.out.println("YOU WIN!");
				System.exit(0);
			}
		} else if (weapon != null && weapon.getName().equals(name)) {
			currentRoom.setWeapon(null);
			if (weapon.getDamage() > bestWeaponDamage) {
				bestWeaponDamage = weapon.getDamage();
				System.out.println("You'll be a more effective fighter with this!");
			}
		} else {
			System.out.println("There is no " + name + " here.");
		}
	}

}
