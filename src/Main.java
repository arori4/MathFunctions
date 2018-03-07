import java.util.List;

public class Main {

	public static void main(String[] args) {

		TestFactors();
		Primes primes = new Primes();

	}

	public static void TestFactors() {
		Factors factors = new Factors();

		long toTest = 5131651810033641l;
		long startTime = System.nanoTime();
		long total = 1;

		//List<Factor> foundFactors = factors.factorNumberPrimeGenerate(18838L);
		List<Factor> foundFactors = factors.factorNumberPrimeGenerate(toTest);

		System.out.println("Time elapsed: " + ((System.nanoTime() - startTime) / 1_000_000_000d) + " seconds." );
		System.out.println();
		System.out.println("Factors: ");
		
		for (Factor k : foundFactors) {
			if (k.exponent > 0) {
				System.out.println(k);
				total *= Math.pow(k.base, k.exponent);
			}
		}

		System.out.println();
		System.out.println("Verifying: " + toTest + " = " + total + " is " + (total == toTest));
		
		Primes primes = new Primes();
		System.out.println("Checking if last number is prime: " + 
				primes.CheckIfPrime(foundFactors.get(foundFactors.size() - 1).base, Primes.SIMPLE));
	}

	public static void TestPrimes() {
		Primes prime = new Primes();
		int primesToGenerate = 200000;

		long[] arr = prime.GeneratePrimes(primesToGenerate, Primes.MAX_COUNT, Primes.ROOT);
		for (long val : arr) {
			System.out.println(val);
		}
	}
}
