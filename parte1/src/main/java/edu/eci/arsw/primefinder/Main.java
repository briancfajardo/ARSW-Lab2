package edu.eci.arsw.primefinder;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		ArrayList<PrimeFinderThread> newThread = generatorThreadsByN(3, 0, 30000000);

		//PrimeFinderThread pft=new PrimeFinderThread(0, 30000000);
		//pft.start();
		Thread.sleep(5000);
		joinThreads(newThread);
		waitThreads(newThread);
		Scanner scanner = new Scanner(System.in);

		scanner.nextLine();

		scanner.close();

	}
	public static ArrayList<PrimeFinderThread> generatorThreadsByN (int N, int a, int b){
		ArrayList<PrimeFinderThread> threads= new ArrayList<>();

		int newA = (b - a)/N;
		int j = 0;

		for (int i = 0; i < b; i += newA){
			j += newA;
			//System.out.println(i+ " "+ j);
			threads.add(new PrimeFinderThread(i, j));
		}
		startThreads(threads);
		return threads;
	}

	public static void startThreads(ArrayList<PrimeFinderThread> threads){
		for (PrimeFinderThread thread : threads){
			thread.start();
		}
	}

	/**
	 * Esperar a que los hilos finalicen
	 * @param threads
	 */
	public static void joinThreads(ArrayList<PrimeFinderThread> threads) throws InterruptedException {
		for (PrimeFinderThread thread : threads){
			thread.join();
		}
	}
	public static void waitThreads (ArrayList<PrimeFinderThread> threads) throws InterruptedException {
		for (PrimeFinderThread thread : threads){
			//thread.setRunning(status);
			thread.getFlag().wait();

		}
	}
}
