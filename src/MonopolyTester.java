import java.util.ArrayList;
import java.util.List;

public class MonopolyTester {

	enum levels {
		UNIMPROVED, ONE, TWO, THREE, FOUR, HOTEL
	}
	
	enum specials {
		COMMUNITY, CHANCE, INCOME, LUXURY, VISITJAIL, FREE, GOTOJAIL, GO
	}
	
	public class Color {
		public String name;
		public int houseBuyPrice;
		public int numberOfProperties;
		public List<Property> propertyList = new ArrayList<Property>();
	}
	
	public class Player {
		
	}
	
	public class Tile {
		public int position;
	}
	
	public class Property extends Tile {
		public int buyPrice;
		public Player owner;
	}
	
	public class RegularProperty extends Property {
		public Color color;
		public int rentPrices[];
	}
	
	public class Railroad extends Property {
		Railroad() {
			buyPrice = 200;
		}
	}
	
	public class Utility extends Property {
		Utility() {
			buyPrice = 150;
		}
	}
	
	public class Special extends Property {
		int tileType;
	}
	
	public void runTest(int numTries) {
		
	}
	
}
