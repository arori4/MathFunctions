
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Primes {

	// Generate conditions
	// This is a case where I wish I can pass in a comparison function.
	public static final int MAX_VALUE = 0;
	public static final int MAX_COUNT = 1;
	
	public static final int NUM_PRIME_FILES = 5;
	public static final int NUM_PRIMES_PER_FILE = 1_000_000;
	public static final int NUM_PRIMES = NUM_PRIME_FILES * NUM_PRIMES_PER_FILE;
	
	public static long[] arr_primes = new long[NUM_PRIMES];

	/**
	 * Generates primes with the root checkback method
	 * @param number
	 * @param condition
	 * @return
	 */
	public static long[] generateRoot(long number, int condition) {

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

	/**
	 * Very simple Prime Checker.
	 * Checks for primality using odd numbers.
	 * A separate check is done for any even numbers.
	 * @param toCheck
	 * @return
	 */
	public static boolean checkSimple(long toCheck) {

		// Check for 2
		if (toCheck == 2) {
			return true;
		}

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
	
	/**
	 * Checks using our precomputed array.
	 * For large numbers, this is not a comprehensive test.
	 * @param number
	 * @return
	 */
	public static boolean checkQuick(Number number) {
		int index = 0;
		
		// We can make this faster if the number we check is within long range
		if (number.canBeLong()) {
			long lNumber = number.longValue();
			long squareRoot = (long)Math.sqrt(lNumber) + 1;
			
			// Check for primality by checking if the number is divisible.
			while (squareRoot > arr_primes[index] && index < NUM_PRIMES) {
				if (lNumber % arr_primes[index] == 0) {
					return false;
				}
				index++;
			}
			
		}
		else {
			Number squareRoot = number.sqRootCeil();
			
			// Check for primality by checking if the number is divisible
			while (squareRoot.compareTo(arr_primes[index]) == 1 && index < NUM_PRIMES) {
				if (number.mod(new Number(arr_primes[index])).equals(Number.ZERO)) {
					return false;
				}
				index++;
			}
		}
		
		// Give a warning to the user if we exhaust the array
		if (index >= NUM_PRIMES) {
			System.out.println("Prime number array has been exhausted. Larger check needed.");
		}
		
		// If we reach here, we have either a prime number, or we need a bigger check.
		return true;
	}

	/**
	 * Loads primes from file.
	 */
	public static void load() {
		BufferedReader reader = null;
		int primes = 0;

		try {
			for (int index = 0; index < 5; index++) {
				File file = new File("primes" + (index + 1) + ".txt");
				reader = new BufferedReader(new FileReader(file));

				String line;
				while ((line = reader.readLine()) != null) {
					arr_primes[primes] = Long.parseLong(line);
					primes++;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} 
		finally {
			try {
				reader.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Number lastPrecomputed() {
		return new Number(arr_primes[arr_primes.length - 1]);
	}
}
