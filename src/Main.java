import java.math.BigInteger;
import java.util.List;

public class Main {
	
	public static final String TEST1 = "32866262297865748";

	public static void main(String[] args) {

		//TestFactors(TEST1);
		//TestFactorial();
		TestFactorsFermat(new BigInteger(TEST1));
	}

	public static void TestFactorsSmall(long toTest) {
		long startTime = System.nanoTime();
		long total = 1;

		List<FastFactor> foundFactors = Factors.factorNumberPrimeGenerateSmall(toTest);

		System.out.println("Time elapsed: " + ((System.nanoTime() - startTime) / 1_000_000_000d) + " seconds." );
		System.out.println();
		System.out.println("Factors: ");
		
		for (FastFactor k : foundFactors) {
			if (k.exponent > 0) {
				System.out.println(k);
				total *= (long)Math.pow(k.base, k.exponent);
			}
		}

		System.out.println();
		System.out.println("Verifying: " + toTest + " = " + total + " is " + (total == toTest));
		
		System.out.println("Checking if last factor is prime: " + 
				Primes.CheckIfPrime(foundFactors.get(foundFactors.size() - 1).base, Primes.SIMPLE));
	}
	
	public static void TestFactors(String toTest) {
		long startTime = System.nanoTime();
		BigInteger total = BigInteger.ONE;

		List<Factor> foundFactors = Factors.factorNumberPrimeGenerate(new BigInteger(toTest));

		System.out.println("Time elapsed: " + ((System.nanoTime() - startTime) / 1_000_000_000d) + " seconds." );
		System.out.println();
		System.out.println("Factors: ");
		
		for (Factor k : foundFactors) {
			if (k.exponent.compareTo(BigInteger.ZERO) == 1) {
				System.out.println(k);
				total = total.multiply(k.value());
			}
		}

		System.out.println();
		System.out.println("Verifying: " + toTest + " = " + total + " is " + (total.equals(new BigInteger(toTest))));
		
		//System.out.println("Checking if last factor is prime: " + 
		//		Primes.CheckIfPrime(foundFactors.get(foundFactors.size() - 1).base, Primes.SIMPLE));
	}
	
	public static void TestFactorsFermat(BigInteger toTest) {
		long startTime = System.nanoTime();
		BigInteger total = BigInteger.ONE;

		System.out.println("Testing factorization of " + toTest);
		
		List<Factor> foundFactors = Factors.factorFermat(toTest);

		System.out.println("Time elapsed: " + ((System.nanoTime() - startTime) / 1_000_000_000d) + " seconds." );
		System.out.println();
		System.out.println("Factors: ");
		
		for (Factor k : foundFactors) {
			if (k.exponent.compareTo(BigInteger.ZERO) == 1) {
				System.out.println(k);
			}
		}
	}
	

	public static void TestPrimes() {
		int primesToGenerate = 200000;

		long[] arr = Primes.GeneratePrimes(primesToGenerate, Primes.MAX_COUNT, Primes.ROOT);
		for (long val : arr) {
			System.out.println(val);
		}
	}
	
	public static void TestFactorial() {
		System.out.println(Factorial.factorial(new BigInteger("20")));
	}
	

}
