import java.math.BigInteger;

public class Factorial {

	public static BigInteger factorial(BigInteger num) {
		
		BigInteger returnVal = new BigInteger("1");
		
		while (num.intValue() != 1) {
			returnVal = returnVal.multiply(num);
			num = num.subtract(new BigInteger("1"));
		}
		
		return returnVal;
		
	}
	
}
