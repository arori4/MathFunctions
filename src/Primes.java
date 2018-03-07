
public class Primes {

	// Algorithms
	public static final int ROOT = 0;
	public static final int SIMPLE = 1;

	// Generate conditions
	// This is a case where I wish I can pass in a comparison function.
	public static final int MAX_VALUE = 0;
	public static final int MAX_COUNT = 1;

	public long[] GeneratePrimes(long number, int genCondition, int algorithm) {
		if (algorithm == ROOT) {
			return GeneratePrimesRoot(number, genCondition);
		}

		// Fallback
		return GeneratePrimesRoot(number, genCondition);
	}

	public boolean CheckIfPrime(long toCheck, int algorithm) {
		if (algorithm == ROOT) {
			return CheckIfPrimeRoot(toCheck);
		}
		else if (algorithm == SIMPLE) {
			return CheckIfPrimeSimple(toCheck);
		}

		return CheckIfPrimeRoot(toCheck);
	}


	/**
	 * Generates primes with the root checkback method
	 * @param number
	 * @param condition
	 * @return
	 */
	private long[] GeneratePrimesRoot(long number, int condition) {
		
		// Create return list
		long[] returnList;
		if (condition == MAX_VALUE) {
			returnList = new long[(int)Math.sqrt(number) * 100];
		}
		else if (condition == MAX_COUNT) {
			returnList = new long[(int)number];
		}
		else {
			returnList = new long[(int)number];
		}

		int index = 2;
		long nextToCheck = 5;
		int primesFound = 2;

		// Add beginning values
		returnList[0] = 2;
		returnList[1] = 3;

		// Generate primes
		boolean checkCondition = true;
		while(checkCondition) {
			// Check against other primes generated
			boolean isPrime = true;
			for (int inner = 0; inner < primesFound; inner++) {
				if (nextToCheck % returnList[inner] == 0) {
					// If we find a factor, then break out of loop
					isPrime = false;
					break;
				}
			}
			if (isPrime) {
				returnList[primesFound] = nextToCheck;
				primesFound++;
			}
			nextToCheck += 2;
			index++;

			// Check conditions
			if (condition == MAX_VALUE) {
				if (nextToCheck >= number) {
					checkCondition = false;
				}
			}
			else if (condition == MAX_COUNT) {
				if (primesFound >= number) {
					checkCondition = false;
				}
			}
		}

		// Trim at the end
		if (condition == MAX_VALUE) {
			long[] returnArray = new long[primesFound];
			for (index = 0; index < primesFound; index++) {
				returnArray[index] = returnList[index];
			}

			return returnArray;
		}
		else {
			return returnList;
		}


	}


	private boolean CheckIfPrimeRoot(long toCheck) {

		long maxToCheck = (long)Math.sqrt(toCheck) + 1;
		long[] primeList = new long[5000];


		return false;

	}

	/**
	 * Very simple Prime Checker.
	 * Checks for primality using odd numbers.
	 * A separate check is done for any even numbers.
	 * @param toCheck
	 * @return
	 */
	private boolean CheckIfPrimeSimple(long toCheck) {

		// Check even numbers
		if (toCheck % 2 == 0) {
			return false;
		}

		// If not even, then do a check using odd numbers.
		long maxToCheck = (long)Math.sqrt(toCheck) + 1;
		for (long index = 3; index < maxToCheck; index += 2) {
			if (toCheck % index == 0) {
				return false;
			}
		}

		return true;
	}

}
