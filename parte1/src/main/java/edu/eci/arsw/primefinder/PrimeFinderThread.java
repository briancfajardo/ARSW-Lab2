package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;

public class PrimeFinderThread extends Thread{

	
	int a,b;

	private Boolean flag = true;

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	private List<Integer> primes=new LinkedList<Integer>();
	private volatile boolean running = true;
	
	public PrimeFinderThread(int a, int b) {
		super();
		this.a = a;
		this.b = b;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public void run(){
		long startTime = System.currentTimeMillis();

		for (int i=a;i<=b;i++){
			if (isPrime(i)){
				synchronized (flag){
					primes.add(i);
				}
				System.out.println(i);
			}
			if (System.currentTimeMillis() - startTime >= 5000){
				running = false;
				a = i;
			}

		}
		
		
	}
	
	boolean isPrime(int n) {
	    if (n%2==0) return false;
	    for(int i=3;i*i<=n;i+=2) {
	        if(n%i==0)
	            return false;
	    }
	    return true;
	}

	public List<Integer> getPrimes() {
		return primes;
	}
	
	
	
	
}
