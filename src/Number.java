import java.math.BigInteger;
import java.util.Random;

/**
 * Wrapper class for BigInteger
 * Provides many more functions for math
 * @author chris
 *
 */
public class Number extends BigInteger{

	private static final long serialVersionUID = -8669276703600901769L;

	public static final Number ZERO = new Number("0");
	public static final Number ONE = new Number("1");
	public static final Number TWO = new Number("2");
	public static final Number THREE = new Number("3");
	public static final Number FOUR = new Number("4");
	public static final Number FIVE = new Number("5");
	public static final Number SIX = new Number("6");

	public static final Number INT_MAX = new Number("2147483647");
	public static final Number INT_MIN = new Number("-2147483648");
	public static final Number LONG_MAX = new Number("9223372036854775807");
	public static final Number LONG_MIN = new Number("-9223372036854775808");
	
	/**
	 * Translates a byte array containing the two's-complement binary representation of a BigInteger into a BigInteger.
	 * @param val
	 */
	public Number(byte[] val) {
		super(val);
	}

	/**
	 * Translates the sign-magnitude representation of a BigInteger into a BigInteger.
	 * @param signum
	 * @param magnitude
	 */
	public Number(int signum, byte[] magnitude) {
		super(signum, magnitude);
	}

	/**
	 * Constructs a randomly generated BigInteger, uniformly distributed over the range 0 to (2numBits - 1), inclusive.
	 * @param bitLength
	 * @param certainty
	 * @param rnd
	 */
	public Number(int bitLength, int certainty, Random rnd) {
		super(bitLength, certainty, rnd);
	}

	/**
	 * Constructs a randomly generated BigInteger, uniformly distributed over the range 0 to (2numBits - 1), inclusive.
	 * @param numBits
	 * @param rnd
	 */
	public Number(int numBits, Random rnd) {
		super(numBits, rnd);
	}

	/**
	 * Translates the decimal String representation of a BigInteger into a BigInteger.
	 * @param val
	 */
	public Number(String val) {
		super(val);
	}

	
	/**
	 * Translates the String representation of a BigInteger in the specified radix into a BigInteger.
	 * @param val
	 * @param radix
	 */
	public Number(String val, int radix) {
		super(val, radix);
	}

	public Number(long val) {
		super(val + "");
	}

	public Number(BigInteger val) {
		super(val.toByteArray());
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
		Number y = sqRootFloor();
		
		if (compareTo(y.multiply(y)) == 0) {
			return y;
		} else {
			return y.add(ONE);
		}
	}

	/**
	 * Returns true if the number is a perfect square.
	 * @return
	 */
	public boolean isSquare() {
		Number y = sqRootFloor();
		
		if (compareTo(y.multiply(y)) == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns whether the number can be represented in a primitive int.
	 * @return
	 */
	public boolean canBeInt() {
		if (compareTo(INT_MAX) == 1) {
			return false;
		}
		else if (compareTo(INT_MIN) == -1) {
			return false;
		}
		else {
			return true;
		}
	}
	
	/**
	 * Returns whether the number can be represented in a primitive long.
	 * @return
	 */
	public boolean canBeLong() {
		if (compareTo(LONG_MAX) == 1) {
			return false;
		}
		else if (compareTo(LONG_MIN) == -1) {
			return false;
		}
		else {
			return true;
		}
	}
	
	
	/**
	 * The same compareTo as BigInteger, but compares with a primitive long value.
	 * @param val
	 * @return
	 */
	public int compareTo(long val) {
		return super.compareTo(new Number(val));
	}


	/**
	 * All overloaded methods
	 */
	public Number abs() {
		// Must go to byte array to prevent loop constructor
		return new Number(super.abs());
	}

	public Number add(Number val) {
		return new Number(super.add(val));
	}

	public Number and(Number val) {
		return new Number(super.and(val));
	}

	public Number andNot(Number val) {
		return new Number(super.andNot(val));
	}

	public int compareTo(Number val) {
		return super.compareTo(val);
	}

	public Number divide(Number val) {
		return new Number( super.divide(val));
	}

	public Number max(Number val) {
		return new Number( super.max(val));
	}

	public Number min(Number val) {
		return new Number( super.min(val));
	}

	public Number mod(Number val) {
		return new Number( super.mod(val));
	}

	public Number multiply(Number val) {
		return new Number( super.multiply(val));
	}
	
	public Number pow(int val) {
		return new Number (super.pow(val));
	}

	public Number shiftLeft(int val) {
		return new Number( super.shiftLeft(val));
	}

	public Number shiftRight(int val) {
		return new Number( super.shiftRight(val));
	}

	public Number subtract(Number val) {
		return new Number( super.subtract(val));
	}
	
	public Number xor(Number val) {
		return new Number( super.xor(val));
	}

}
