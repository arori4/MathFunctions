public class Factor {

	public Number base;
	public Number exponent;

	public Factor() {
		base = Number.ONE;
		exponent = Number.ZERO;
	}

	public String toString() {

		return base + " ^ " + exponent;

	}

	/**
	 * Returns the true value of the exponent as a big number
	 * @return
	 */
	public Number value(){
		Number result = Number.ONE;
		while (exponent.signum() > 0) {
			if (exponent.testBit(0)) result = result.multiply(base);
			base = base.multiply(base);
			exponent = (Number) exponent.shiftRight(1);
		}
		return result;
	}
}
