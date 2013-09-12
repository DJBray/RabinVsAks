import java.awt.EventQueue;
import java.awt.Point;
import java.math.BigInteger;
import java.util.Random;


public class MillerPrimalityTest extends Thread{
	private RvAGui gui;
	private static Random rnd = new Random();
	private BigInteger max;
	
	private long myTime;
	private long systTime;
	//private PrimalityResult result;
	//private BigInteger offset;
	//private BigInteger end;
	
	public MillerPrimalityTest(RvAGui gui){
		this.gui = gui;
		this.start();
	}

	/**
	 * rndBigInteger
	 * 
	 * Finds a random Big Integer in the range [0, max)
	 * @param max The exclusive max for the range of random numbers
	 * @return a random BigInteger in the range [0, max)
	 */
	public static BigInteger rndBigInteger(BigInteger max){
		//according to the API this will take on average 2 iterations to terminate
		do{
			BigInteger r = new BigInteger(max.bitLength(), rnd);
			if(r.compareTo(max) == -1)
				return r;
		}while(true);
	}
	
	/**
	 * isPrime
	 * 
	 * Tests whether or not a number is prime. Returns true if prime, false otherwise. This uses
	 * a probabilistic primality test and thus can only return a probability that the number is prime.
	 * 
	 * @param n, a BigInteger to be tested as prime
	 * @param k is the number of times to repeat the Miller-Rabin Primality Test
	 * @return true if probably prime, false if composite
	 */
	public static boolean isPrime(BigInteger n, int k){
		if(n.doubleValue() == 2) return true;
		if(n.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO) || BigInteger.valueOf(3).compareTo(n) == 1)		return false;
		if(n.equals(BigInteger.valueOf(3)))		return true;	//Have to hard code 3 as prime b/c code breaks for all n < 4
		BigInteger m = n.subtract(BigInteger.ONE);
		int s = m.getLowestSetBit();
		BigInteger d = m;
		d = d.shiftRight(s);
		
		for(int i=0; i<k; i++){		//repeat k times
			BigInteger a = rndBigInteger(n.subtract(BigInteger.valueOf(3)));  //random int [0, n-3)
			a = a.add(BigInteger.valueOf(2));	//random int [2, n-2]
			BigInteger x = a.modPow(d, n);  //x = a^d mod n
			if(x.equals(BigInteger.ONE) || x.equals(m))  //if x = 1 or n - 1, continue
				continue;
			for(int r = 1; r <= s-1; r++){  //else for( r = 1; r < s-1; r++)
				x = x.modPow(BigInteger.valueOf(2), n);  // x = x^2 mod n
				if(x.equals(BigInteger.ONE))	return false; //if x = 1 return false
				if(x.equals(m))		break;//if x = n-1 continue
			}
			if(x.equals(m))		continue;
			return false;
		}
		return true;
	}
	
	public void run(){
		try{
			for(int i = 0; i <= 20; i++){
				performExperiment(i);
				gui.appendText("\nThread sleeping for 10 seconds");
				this.sleep(10000);
			}
		}
		catch(Exception e){	}
		gui.appendText("\nTest Completed");
		
		//result.setPrimeResults(numsTested);
		//result.setTime(finish-start);
	}
	
	public void performExperiment(int k){
		gui.setText("Miller-Rabin Test Starting...");
		int repeat = 300;
		max = BigInteger.valueOf(1000000000);
		//max = max.multiply(max);
		
		//result = new PrimalityResult((int)(end.subtract(offset).doubleValue()), offset);
		BigInteger[] numsTested = new BigInteger[repeat];
		long start = System.currentTimeMillis();
		for(int i=0; i<repeat; i++){
			boolean is_prime;
			do{
				numsTested[i] = rndBigInteger(max);
				is_prime = isPrime(numsTested[i], k);
			}while(!is_prime);
		}
		long finish = System.currentTimeMillis();
		
		myTime = finish-start;
		
		start = System.currentTimeMillis();
		gui.appendText("Performing Java Library Probability Test...");
		BigInteger[] systemTested = new BigInteger[repeat];
		for(int i=0; i<repeat; i++){
			systemTested[i] = BigInteger.probablePrime(max.bitLength(), rnd);
		}
		finish = System.currentTimeMillis();
		
		systTime = finish-start;
		gui.appendText("\nA k value of " + k + " for my test yields...");
		gui.appendText("\nMy Miller-Rabin execution time: " + myTime + " ms");
		gui.appendText("System Miller-Rabin execution time: " + systTime + " ms");
		
		gui.appendText("Graphing...");
		gui.addMyPoint(new Point(k, (int)myTime));
		gui.addSystPoint(new Point(k, (int)systTime));
		gui.invalidate();
		
		
		gui.appendText("\nTesting my accuracy...");
		int myNotPrime=0;
		for(int i=0; i<numsTested.length; i++){
			for(BigInteger j=BigInteger.valueOf(2); j.multiply(j).compareTo(numsTested[i])<0; j=j.add(BigInteger.ONE)){
				if(numsTested[i].mod(j).equals(BigInteger.ZERO)){
					myNotPrime++;
					break;
				}
			}
		}
		
		int systemNotPrime=0;
		gui.appendText("Testing System accuracy...");
		for(int i=0; i<systemTested.length; i++){
			for(BigInteger j=BigInteger.valueOf(2); j.multiply(j).compareTo(systemTested[i])<0; j=j.add(BigInteger.ONE)){
				if(systemTested[i].mod(j).equals(BigInteger.ZERO)){
					systemNotPrime++;
					break;
				}
			}
		}
		gui.appendText("\nMy Miller-Rabin's accuracy: " + (repeat-myNotPrime) + "/" + repeat);
		gui.appendText("System's Miller-Rabin's accuracy: " + (repeat-systemNotPrime) + "/" + repeat);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RvAGui frame = new RvAGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
