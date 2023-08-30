package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PrimeFinderThread extends Thread{

	
	int a,b;

	private final List<Integer> primes=new LinkedList<>();
	private volatile boolean running = true;
	private final AtomicInteger counter;
	private final Object lock;

	/**
	 * Constructor to initialize parameters of the prime number search thread.
	 *
	 * @param a Initial value of the search range.
	 * @param b   Final value of the search range.
	 * @param counter Atomic counter to keep track of the prime numbers found.
	 * @param lock  Shared lock object between threads for synchronization.
	 */
	public PrimeFinderThread(int a, int b, AtomicInteger counter, Object lock) {
		//super();
		this.a = a;
		this.b = b;
		this.counter = counter;
		this.lock = lock;
	}

	@Override
	public void run() {
		long startTime = System.currentTimeMillis();
		long elapsedTime;

		for (int num = a; num <= b; num++) {
			if (isPrime(num)) {
				synchronized (lock) {
					counter.incrementAndGet();
					System.out.println("Prime found: " + num);
				}
			}

			elapsedTime = System.currentTimeMillis() - startTime;
			if (elapsedTime >= 5000) {
				synchronized (lock) {
					try {
						lock.wait(); // Wait for Main to continue
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				startTime = System.currentTimeMillis(); // Reset the timer
			}
		}
	}



	/**
	 * Checks if a given number is prime.
	 *
	 * @param n Number to be checked.
	 * @return `true` if the number is prime, `false` otherwise.
	 */
	boolean isPrime(int n) {
	    if (n%2==0) return false;
	    for(int i=3;i*i<=n;i+=2) {
	        if(n%i==0)
	            return false;
	    }
	    return true;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public synchronized void resumeThread() {
		notifyAll();
	}
}
