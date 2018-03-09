public class Factorial {

	public static Number factorial(Number num) {
		
		Number returnVal = new Number("1");
		
		while (num.intValue() != 1) {
			returnVal = returnVal.multiply(num);
			num = num.subtract(new Number("1"));
		}
		
		return returnVal;
		
	}
	
}
