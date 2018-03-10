import java.util.ArrayList;
import java.util.List;

public class Factors {

	public static final int MOD = 20;

	/**
	 * Factors numbers using Fermat's factorization method.
	 * We will check whether a^2 - numberToFactor = b^2
	 * @param numberToFactor
	 * @return
	 */
	public static List<Factor> fermatSimple(Number numberToFactor) {
		
		System.out.println("Factoring " + numberToFactor);
		
		// Get sieve values
		int[] endValues = squareSieve(numberToFactor);
		int endIndex = 0;
		/*
		// Create Interval list from sieve values
		int[] intervals = new int[endValues.length];
		System.out.println("");
		for (int index = 0; index < intervals.length; index++) {
			// Special case for the last value
			if (index == intervals.length - 1) {
				intervals[index] = (endValues[0] - endValues[index] + MOD) % MOD;
			}
			else {
				intervals[index] = endValues[index + 1] - endValues[index];
			}
		}*/

		// Create a and ceiling
		Number a = numberToFactor.sqRootCeil();
		Number ceiling = fermatCeiling(a);
		boolean hitCeiling = false;
		System.out.println("Ceiling of a: " + ceiling);

		/*
		// Find an A that could be a square
		boolean foundIt = false;
		while (foundIt == false) {
			for (int index = 0; index < endValues.length; index++) {
				if (a.mod(new Number(MOD)).equals(new Number(endValues[index]))) {
					foundIt = true;
					endIndex = index;
					break;
				}
			}
			a = a.add(Number.ONE);
		}
		*/
		// Calculate b^2
		Number b2 = (a.multiply(a)).subtract(numberToFactor);

		// Guess and check up until a point. Try and find a B value that is a square.
		while (true) {

			// If we reach a square, then we have found a Fermat factor.
			if (b2.isSquare()) {
				break;
			}

			// Compare a - b to the ceiling to see if we need to stop.
			if ((a.compareTo(ceiling) == 1)) {
				hitCeiling = true;
				break;
			}

			// Process
			a = a.add(Number.ONE);
			b2 = (a.pow(2)).subtract(numberToFactor);
		}

		// If ceiling is hit, we fall back to brute force algorithm to find prime factors.
		if (hitCeiling) {
			System.out.println("Hit ceiling. Using prime generator to check with reduced range to " + a.subtract(b2.sqRootCeil()));
			return factorPrimeGenT(numberToFactor, a.subtract(b2.sqRootCeil()));
		}
		else {
			// Create list of factors
			List<Factor> factors = new ArrayList<Factor>();
			
			// Factor the next factors recursively
			Number int1 = a.add(b2.sqRootCeil());
			List<Factor> factorsOf1 = fermatSimple(int1);
			for (Factor k : factorsOf1) {
				factors.add(k);
			}
			Number int2 = a.subtract(b2.sqRootCeil());
			List<Factor> factorsOf2 = fermatSimple(int2);
			for (Factor k : factorsOf2) {
				factors.add(k);
			}

			//Return list of factors
			return factors;
		}

	}

	/**
	 * Returns the next value to add to A based on possible squares to check.
	 * @param num
	 * @return
	 */
	public static int[] squareSieve(Number num) {

		// Squares mod 20, and numberToFactor mod 20
		final int[] mods = {0, 1, 4, 5, 9, 16};
		int[] changedMods = {0, 1, 4, 5, 9, 16};
		int ntf20 = num.mod(new Number(MOD)).intValue();

		boolean[] isPossibleEnd = new boolean[MOD];

		// Match possible square values to a^2 values
		for (int index = 0; index < mods.length; index++) {
			changedMods[index] = (mods[index] + (MOD - ntf20)) % MOD;
			System.out.println(changedMods[index]);

			// For each new changedMods, see if it is a possible square end
			for (int inner = 0; inner < mods.length; inner++) {
				if (changedMods[index] == mods[inner]) {

					// For a possible a^2 value, find all possible a endings
					for (int count = 0; count < MOD; count++) {
						if ( (count * count) % MOD == mods[index] ) { // intentionally index
							isPossibleEnd[count] = true;
						}
					}
				}
			}
		}

		// Find how many processable numbers there are.
		int numEndings = 0;
		for (int index = 0; index < MOD; index++) {
			if (isPossibleEnd[index]) {
				numEndings++;
			}
		}

		// Create a new array with the endings
		int[] returnArray = new int[numEndings];
		int endingCounter = 0;
		for (int index = 0; index < MOD; index++) {
			if (isPossibleEnd[index]) {
				returnArray[endingCounter] = index;
				endingCounter++;
			}
		}

		return returnArray;
	}

	public static List<Factor> primeGen(Number numberToFactor) {
		return factorPrimeGenT(numberToFactor, null);
	}

	private static List<Factor> factorPrimeGenT(
			Number numberToFactor, Number threshold) {

		// List exists for look-at-itself generation
		List<Number> primeVals = new ArrayList<Number>();
		primeVals.add(Number.TWO);
		primeVals.add(Number.THREE);
		List<Factor> factors = new ArrayList<Factor>();

		int index = 0;

		// Find all factors
		while (true) {

			// Current prime value
			Number currentPrime = primeVals.get(index);

			// Create factor
			Factor factor = new Factor();
			factor.base = currentPrime;
			factor.exponent = Number.ZERO;

			// Create exponent
			while ((numberToFactor.mod(currentPrime)).equals(Number.ZERO)) {
				factor.exponent = factor.exponent.add(Number.ONE);
				numberToFactor = numberToFactor.divide(currentPrime);
				System.out.println(numberToFactor);
			}

			// Add factor to list
			if (factor.exponent.compareTo(Number.ZERO) == 1 ) {
				factors.add(factor);
			}

			// Generate next candidate
			Number nextPrimeToCheck = currentPrime;

			// Check whether we need to continue or not
			if (nextPrimeToCheck.compareTo(numberToFactor.sqRootCeil()) == 1) {
				break;
			}
			if (threshold != null && nextPrimeToCheck.compareTo(threshold) == 1) {
				break;
			}

			// Generate next prime, if not 2 or 3
			if (index >= 1) {

				boolean foundPrime = false;

				while (foundPrime == false) {
					nextPrimeToCheck = nextPrimeToCheck.add(Number.TWO);

					for (int inner = 0; inner < primeVals.size(); inner++) {
						if (nextPrimeToCheck.mod(primeVals.get(inner)).equals(Number.ZERO)) {
							break;
						}

						// if we reach here, we have found a prime number
						foundPrime = true;
					}
				} // end foundPrime while

				primeVals.add(nextPrimeToCheck);
			}

			index++;
		} // end condition while

		// If the number left to factor is not 1, then it is a prime number of itself.
		if (numberToFactor.equals(Number.ONE) == false) {
			Factor factor = new Factor();
			factor.base = numberToFactor;
			factor.exponent = Number.ONE;
			factors.add(factor);
		}

		// Return factor
		return factors;

	}


	private static Number fermatCeiling(Number a) {
		return a.multiply(Number.THREE).divide(Number.ONE);
	}



	/****************** Legacy Code ******************/

	/**
	 * Factors a number.
	 * Generates a list of prime values to check, 
	 * @param numberToFactor
	 * @return
	 */
	public static List<FastFactor> smallSimple(long numberToFactor) {

		// Generate prime values up till half the value (smallest possible factor is 2)
		long[] primeVals = Primes.generateRoot(numberToFactor / 3, Primes.MAX_VALUE);
		// Create list of factors
		List<FastFactor> factors = new ArrayList<FastFactor>();

		// Find all factors
		// Loop through each prime and find the prime factor amounts
		for (int index = 0; index < primeVals.length; index++) {

			// Create factor
			FastFactor factor = new FastFactor();
			factor.base = primeVals[index];
			factor.exponent = 0;

			// Check for factoriality. Continue to divide by the factor until 0.
			while (numberToFactor % primeVals[index] == 0) {
				factor.exponent++;
				numberToFactor /= primeVals[index];
			}

			// Add factor to list
			factors.add(factor);
		}

		if (numberToFactor != 1) {
			System.err.println("FAILED: number can be factored: " + numberToFactor);
		}

		// Return list
		return factors;
	}


	public static List<FastFactor> smallPrimeGen(long numberToFactor) {

		// List exists for look-at-itself generation
		List<Long> primeVals = new ArrayList<Long>();
		primeVals.add(2L);
		primeVals.add(3L);
		List<FastFactor> factors = new ArrayList<FastFactor>();

		int index = 0;

		// Find all factors
		boolean condition = true;
		while (condition) {

			// Current prime value
			long currentPrime = primeVals.get(index);

			// Create factor
			FastFactor factor = new FastFactor();
			factor.base = currentPrime;
			factor.exponent = 0;

			// Create exponent
			while (numberToFactor % currentPrime == 0) {
				factor.exponent++;
				numberToFactor /= currentPrime;
				System.out.println(numberToFactor);
			}

			// Add factor to list
			if (factor.exponent > 0) {
				factors.add(factor);
			}

			// Generate next candidate
			long nextPrimeToCheck = currentPrime;

			// Check whether we need to continue or not
			if (nextPrimeToCheck > (long)Math.sqrt(numberToFactor) + 1) {
				break;
			}

			// Generate next prime, if not 2 or 3
			if (index >= 1) {

				boolean foundPrime = false;

				while (foundPrime == false) {
					nextPrimeToCheck += 2;

					for (int inner = 0; inner < primeVals.size(); inner++) {
						if (nextPrimeToCheck % primeVals.get(inner) == 0) {
							break;
						}

						// if we reach here, we have found a prime number
						foundPrime = true;
					}
				} // end foundPrime while

				primeVals.add(nextPrimeToCheck);
			}

			index++;
		} // end condition while

		// If the number left to factor is not 1, then it is a prime number of itself.
		if (numberToFactor != 1) {
			FastFactor factor = new FastFactor();
			factor.base = numberToFactor;
			factor.exponent = 1;
			factors.add(factor);
		}

		// Return factor
		return factors;

	}




}
