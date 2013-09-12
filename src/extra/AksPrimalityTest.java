package extra;
import java.math.BigInteger;


public class AksPrimalityTest {

	public static boolean isPrime(BigInteger n){
		if(n.compareTo(BigInteger.ONE) <= 0)	return false;
		//If n = a^b for integers a>0 and b>1, output composite
		if(isPerfectPower(n)) return false;
		//Find smallest r s.t. O_r(n) > log^2(n) (base 2)
		BigInteger r = multiplicativeOrder(n);
		//if 1 < gcd(a,n) < n for some a<=r, output composite
		for(int a = 1; a <= r.doubleValue(); a++){
			BigInteger gcd = r.gcd(BigInteger.valueOf(a));
			if(gcd.compareTo(n) < 0 && gcd.compareTo(BigInteger.ONE) > 0)	return false;
		}
		//if n<= r output prime
		if(n.compareTo(r) <= 0)		return true;
		//for a=1 to floor(sqrt(eulerTotient(r)) log(n)/log(2))
		BigInteger limit = BigInteger.valueOf((int)(sqrt(eulerTotient(r)) * logBase2(n)));
		for(BigInteger a = BigInteger.ONE; a.compareTo(limit) <= 0; a=a.add(a)){
			//		if (X+a)^n != X^n+a (mod X^r-1, n) output composite
			// TODO: ?????
		}
		return true;
	}

	/**
	 * multiplicativeOrder
	 * 
	 * Finds the o_r(n) for a number for the smallest r > log(n)^2.
	 * Needs to be tested thoroughly.
	 * 
	 * Dependent on the logBase2 function.
	 * @param n
	 * @return
	 */
	public static BigInteger multiplicativeOrder(BigInteger n){
		double loglog = logBase2(n);
		loglog *= loglog;
		BigInteger r = BigInteger.valueOf((int)loglog);
		boolean notOne = false;
		do{
			notOne = false;
			r=r.add(r);
			for(int k=0; k<=loglog; k++){
				if(n.modPow(BigInteger.valueOf(k), r).equals(BigInteger.ONE)){ 
					notOne = true;
					break;
				}
			}
		}while(notOne || r.doubleValue() <= loglog);

		return r;
	}

	public static boolean isPerfectPower(BigInteger n){
		for(BigInteger a=BigInteger.ZERO; a.compareTo(n.divide(BigInteger.valueOf(2))) == -1; a = a.add(BigInteger.ONE)){
			//TODO: 
		}
		return false;
	}

	/**
	 * eulerTotient
	 * 
	 * Finds the number of coprime numbers to n in the range [1, n).
	 * This executes in O(sqrt(n)*log(n)) time? (assuming division is constant time).
	 * @param n
	 * @return
	 */
	public static BigInteger eulerTotient(BigInteger n){
		BigInteger result = n;
		for(BigInteger i = BigInteger.valueOf(2); n.compareTo(i.multiply(i))>=0; 
				i=i.add(BigInteger.ONE)){
			//Check if i is a factor of n
			if(n.mod(i).compareTo(BigInteger.ZERO) == 0){
				//apply the Euler's Product Formula
				result = result.subtract(result.divide(i));
			}
			//Clear out all factors of i
			while(n.mod(i).compareTo(BigInteger.ZERO)==0){
				n = n.divide(i);
			}
		}
		
		//Executes if is a prime number. This assures we don't 
		//count a prime number as being coprime to itself
		if(n.compareTo(BigInteger.ONE)>0)
				result = result.subtract(result.divide(n));
		return result;
	}

	/**
	 * logBase2
	 * 
	 * Finds the logBase2 value of n. Implemented in a way such that it could be inaccurate for large
	 * values. May need to use a different implementation.
	 * 
	 * TODO: BUGGED!!!
	 * @param n
	 * @return
	 */
	public static double logBase2(BigInteger n){
		String decimal = n.toString();
		int powTen = decimal.length();
		//potentially inaccurate, but would it cause problems later?
		return powTen + Math.log10(Double.parseDouble("0."+decimal))/Math.log10(2);
	}

	public static double sqrt(BigInteger n){
		//TODO
		return 0.0;
	}
	
	public static void main(String[] args){
		for(int i=1; i<37; i++){
			BigInteger k = BigInteger.valueOf(i);
			//System.out.println("O_r(" + i + ") is " + AksPrimalityTest.multiplicativeOrder(k));
			System.out.println("The log base 2 of " + i + " is " + AksPrimalityTest.logBase2(k));
			//System.out.println(i + " has " + AksPrimalityTest.eulerTotient(k).toString() + " coprimes below it.");
		}
	}
}
