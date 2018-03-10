import java.util.List;

public class Main {
	
	public static final String COMP1 = "32866262297865748";
	public static final String COMP2 = "4963469855339";
	
	public static final String PRIME1 = "2345678917";
	public static final String PRIME2 = "348116657341";
	
	public static final String RSA100 = "15226050279225"
			+ "3336053561837813263742971806811496138068"
			+ "8657908494580122963258952897654000350692006139";

	public static void main(String[] args) {
		//Primes.load(); // Always load this
		
		//System.out.println(Primes.checkQuick(new Number("348116657341")));

		//TestFactorsSmall(4963469855689l);
		TestFactorsFermat(new Number(COMP1));
		//TestPrimes();
	}

	
	public static void TestFactorsSmall(long toTest) {
		long startTime = System.nanoTime();
		long total = 1;

		List<FastFactor> foundFactors = Factors.smallPrimeGen(toTest);

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
				Primes.checkSimple(foundFactors.get(foundFactors.size() - 1).base));
	}
	
	public static void TestFactors(String toTest) {
		long startTime = System.nanoTime();
		Number total = Number.ONE;
		
		List<Factor> foundFactors = Factors.primeGen(new Number(toTest));

		System.out.println("Time elapsed: " + ((System.nanoTime() - startTime) / 1_000_000_000d) + " seconds." );
		System.out.println();
		System.out.println("Factors: ");
		
		for (Factor k : foundFactors) {
			if (k.exponent.compareTo(Number.ZERO) == 1) {
				System.out.println(k);
				total = total.multiply(k.value());
			}
		}

		System.out.println();
		System.out.println("Verifying: " + toTest + " = " + total + " is " + (total.equals(new Number(toTest))));
		
		//System.out.println("Checking if last factor is prime: " + 
		//		Primes.CheckIfPrime(foundFactors.get(foundFactors.size() - 1).base, Primes.SIMPLE));
	}
	
	public static void TestFactorsFermat(Number toTest) {
		long startTime = System.nanoTime();
		Number total = Number.ONE;

		System.out.println("Testing factorization of " + toTest);
		
		List<Factor> foundFactors = Factors.fermatSimple(toTest);

		System.out.println("Time elapsed: " + ((System.nanoTime() - startTime) / 1_000_000_000d) + " seconds." );
		System.out.println();
		System.out.println("Factors: ");
		
		for (Factor k : foundFactors) {
			if (k.exponent.compareTo(Number.ZERO) == 1) {
				System.out.println(k);
				total = total.multiply(k.value());
			}
		}
		
		System.out.println();
		System.out.println("Verifying: " + toTest + " = " + total + " is " + (total.equals(new Number(toTest))));
	}
	

	public static void TestPrimes() {
		int primesToGenerate = 200000;

		long startTime = System.nanoTime();
		long[] arr = Primes.generateRoot(primesToGenerate, Primes.MAX_COUNT);
		
		for (long val : arr) {
			System.out.println(val);
		}
		System.out.println("Time elapsed: " + ((System.nanoTime() - startTime) / 1_000_000_000d) + " seconds." );
		
	}
}
