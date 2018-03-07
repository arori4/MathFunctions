import java.util.ArrayList;
import java.util.List;

public class Factors {

	/**
	 * Factors a number.
	 * Generates a list of prime values to check, 
	 * @param numberToFactor
	 * @return
	 */
	public List<Factor> factorNumberSimple(long numberToFactor) {

		// Generate prime values up till half the value (smallest possible factor is 2)
		Primes primes = new Primes();
		long[] primeVals = primes.GeneratePrimes(
				numberToFactor / 3, Primes.MAX_VALUE, Primes.ROOT);
		// Create list of factors
		List<Factor> factors = new ArrayList<Factor>();

		// Find all factors
		// Loop through each prime and find the prime factor amounts
		for (int index = 0; index < primeVals.length; index++) {

			// Create factor
			Factor factor = new Factor();
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


	public List<Factor> factorNumberPrimeGenerate(long numberToFactor) {

		// List exists for look-at-itself generation
		List<Long> primeVals = new ArrayList<Long>();
		primeVals.add(2L);
		primeVals.add(3L);
		List<Factor> factors = new ArrayList<Factor>();

		int index = 0;

		// Find all factors
		boolean condition = true;
		while (condition) {

			// Current prime value
			long currentPrime = primeVals.get(index);

			// Create factor
			Factor factor = new Factor();
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

			// Check whether we need to continue or not by look-ahead
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
			Factor factor = new Factor();
			factor.base = numberToFactor;
			factor.exponent = 1;
			factors.add(factor);
		}
		
		// Return factor
		return factors;

	}

}
