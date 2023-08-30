package edu.eci.arsw.primefinder;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

	public static void main(String[] args) throws InterruptedException, IOException {

		AtomicInteger counter = new AtomicInteger(0);
		Object lock = new Object();
		ArrayList<PrimeFinderThread> threads = generatorThreadsByN(3, 0, 30000000, counter, lock);
		startThreads(threads);

		while (true) {
			Thread.sleep(5000); // Wait for 5 seconds

			synchronized (lock) {
				System.out.println("Prime count: " + counter.get());
				System.out.println("Press ENTER to resume...");
				new BufferedReader(new InputStreamReader(System.in)).readLine(); // Wait for ENTER key press
				lock.notifyAll(); // Notify threads to continue

			}

			boolean allThreadsFinished = true;
			for (PrimeFinderThread thread : threads) {
				if (thread.isAlive()) {
					allThreadsFinished = false;
					break;
				}
			}

			if (allThreadsFinished) {
				break; // All threads have finished, exit loop
			}
		}

		System.out.println("All threads are dead :)");
	}



	/**
	 * Generates an ArrayList of PrimeFinderThread instances based on the specified parameters.
	 *
	 * @param N       Number of threads to generate.
	 * @param a       Starting value of the range for each thread.
	 * @param b       Ending value of the range for each thread.
	 * @param counter Atomic counter to keep track of prime numbers found.
	 * @param lock    Shared lock object between threads for synchronization.
	 * @return ArrayList of PrimeFinderThread instances with the specified ranges.
	 */
	public static ArrayList<PrimeFinderThread> generatorThreadsByN (int N, int a, int b, AtomicInteger counter, Object lock){
		ArrayList<PrimeFinderThread> threads= new ArrayList<>();
		int newA = (b - a)/N;
		int j = 0;

		for (int i = 0; i < b ; i += newA){
			j += newA;
			if (j < b){
				//System.out.println(i+ " "+ j);
				threads.add(new PrimeFinderThread(i, j, counter, lock));
			}
		}

		return threads;
	}

	/**
	 * Starts the execution of the provided list of PrimeFinderThread instances.
	 *
	 * @param threads ArrayList of PrimeFinderThread instances to start.
	 */
	public static void startThreads(ArrayList<PrimeFinderThread> threads){
		for (PrimeFinderThread thread : threads){
			thread.start();
		}
	}

}
