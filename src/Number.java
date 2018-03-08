import java.math.BigInteger;

public class Number extends BigInteger{

	private static final long serialVersionUID = -8669276703600901769L;
	
	public static final Number ZERO = new Number("0");
	public static final Number ONE = new Number("1");
	public static final Number TWO = new Number("2");
	public static final Number THREE = new Number("3");
	public static final Number FOUR = new Number("4");
	public static final Number FIVE = new Number("5");
	public static final Number SIX = new Number("6");
	
	public Number(String val) {
		super(val);
	}
	
	public Number(long val) {
		super(val + "");
	}
	
	public Number sqRootFloor() throws IllegalArgumentException {
		if (compareTo(ZERO) < 0) {
			throw new IllegalArgumentException("Negative argument.");
		}
		
		// square roots of 0 and 1 are trivial and
		// y == 0 will cause a divide-by-zero exception
		if (equals(ZERO) || equals(ONE)) {
			return this;
		}
		
		Number y;
		// starting with y = x / 2 avoids magnitude issues with x squared
		for (y = divide(TWO); y.compareTo(divide(y)) > 0; y = ((divide(y)).add(y)).divide(TWO));
		return y;
	}

	public Number sqRootCeil() throws IllegalArgumentException {
		if (compareTo(ZERO) < 0) {
			throw new IllegalArgumentException("Negative argument.");
		}
		
		// square roots of 0 and 1 are trivial and
		// y == 0 will cause a divide-by-zero exception
		if (equals(ZERO) || equals(ONE)) {
			return this;
		}
		
		Number y;
		// starting with y = x / 2 avoids magnitude issues with x squared
		for (y = divide(TWO);
				y.compareTo(divide(y)) > 0;
				y = ((divide(y)).add(y)).divide(TWO));
		
		if (compareTo(y.multiply(y)) == 0) {
			return y;
		} else {
			return y.add(ONE);
		}
	}

	public boolean isSquare() {
		return sqRootCeil().equals(sqRootFloor());
	}
	

	/**
	 * All overloaded methods
	 */
	
	public Number abs() {
		return (Number) super.abs();
	}
	
	public Number add(Number val) {
		return (Number) super.add(val);
	}
	
	public int compareTo(Number val) {
		return super.compareTo(val);
	}
	
	public Number divide(Number val) {
		return (Number) super.divide(val);
	}
	
	public Number max(Number val) {
		return (Number) super.max(val);
	}
	
	public Number min(Number val) {
		return (Number) super.min(val);
	}
	
	public Number mod(Number val) {
		return (Number) super.mod(val);
	}
	
	public Number multiply(Number val) {
		return (Number) super.multiply(val);
	}
	
	public Number subtract(Number val) {
		return (Number) super.subtract(val);
	}
	
}
