package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PrimeFinderThread extends Thread{

	
	int a,b;

	private final List<Integer> primes=new LinkedList<Integer>();
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
		boolean detenido = false;
		for (int i=a;i<=b;i++){
			if (isPrime(i)){
				synchronized (this){
					try {
						if (System.currentTimeMillis() - startTime >= 5000 && !detenido){
							System.out.println("The total number of primes found is: "+ primes.size());
							detenido = true;
							wait();
						}
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				}
				primes.add(i);
				System.out.println(i);
			}
		}
		System.out.println("In the end, this thread found "+ primes.size() + " threads");
	}
	public synchronized void resumeThread() {
		notifyAll();
	}
	
	boolean isPrime(int n) {
	    if (n%2==0) return false;
	    for(int i=3;i*i<=n;i+=2) {
	        if(n%i==0)
	            return false;
	    }
	    return true;
	}
}
