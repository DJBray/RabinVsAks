package extra;
import java.math.BigInteger;


public class PrimalityResult {

	private boolean[] primeResults;
	private long time;
	private BigInteger startingNum;
	
	PrimalityResult(int rangeOfPrimes, BigInteger startingNum){
		primeResults = new boolean[rangeOfPrimes];
		this.startingNum = startingNum;
	}
	
	PrimalityResult(boolean[] primeResults, long time, BigInteger startingNum){
		this.primeResults = primeResults;
		this.time = time;
		this.startingNum = startingNum;
	}
	
	public void setPrimeResults(boolean[] primeResults){
		this.primeResults = primeResults;
	}
	
	public void setTime(long time){
		this.time = time;
	}
	
	public boolean[] getPrimeResults(){
		return primeResults;
	}
	
	public long getTime(){
		return time;
	}
	
	public BigInteger getStartingNum(){
		return startingNum;
	}
}
