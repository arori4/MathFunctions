import java.util.ArrayList;
import java.util.List;

public class Factors {

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

	/**
	 * Factors numbers using Fermat's factorization method.
	 * We will check whether a^2 - numberToFactor = b^2
	 * @param numberToFactor
	 * @return
	 */
	public static List<Factor> fermatSimple(Number numberToFactor) {

		// Create list of factors
		List<Factor> factors = new ArrayList<Factor>();

		// Create a
		Number a = numberToFactor.sqRootCeil();

		// Create b^2
		Number b2 = (a.multiply(a)).subtract(numberToFactor);

		// Create Ceiling
		Number ceiling = fermatCeiling(a, b2.sqRootCeil());
		System.out.println("Ceiling of a: " + ceiling);
		boolean hitCeiling = false;

		// Guess and check up until a point. Try and find a B value that is a square.
		while (b2.isSquare() == false) {

			a = a.add(Number.ONE);
			b2 = (a.multiply(a)).subtract(numberToFactor);

			// Compare a - b to the ceiling to see if we need to stop
			if ((a.subtract(b2.sqRootCeil()).compareTo(ceiling) == 1)) {
				hitCeiling = true;
				break;
			}
		}

		// If ceiling is hit, we fallback to brute force algorithm to find a prime
		if (hitCeiling) {
			return factorPrimeGenT(numberToFactor, a.subtract(b2.sqRootCeil()));
		}

		// Find Factors based on number given
		Number int1 = a.add(b2.sqRootCeil());
		Factor factor1 = new Factor();
		factor1.base = int1;
		factor1.exponent = Number.ONE;
		factors.add(factor1);

		Number int2 = a.subtract(b2.sqRootCeil());
		Factor factor2 = new Factor();
		factor2.base = int2;
		factor2.exponent = Number.ONE;
		factors.add(factor2);

		//Return list of factors
		return factors;

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


	private static Number fermatCeiling(Number a, Number b) {
		Number difference = a.subtract(b);

		return difference.multiply(Number.SIX).divide(Number.FIVE);
	}




}
