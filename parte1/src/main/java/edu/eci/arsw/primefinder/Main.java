package edu.eci.arsw.primefinder;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

	public static void main(String[] args){

		ArrayList<PrimeFinderThread> newThread = generatorThreadsByN(3, 0, 30000000);

		//PrimeFinderThread pft=new PrimeFinderThread(0, 30000000);
		//pft.start();

		//joinThreads(newThread);
		//waitThreads(newThread);

		Scanner scanner = new Scanner(System.in);

		scanner.nextLine();
		scanner.close();

		notifyAllThreads(newThread);
	}
	public static ArrayList<PrimeFinderThread> generatorThreadsByN (int N, int a, int b){
		ArrayList<PrimeFinderThread> threads= new ArrayList<>();

		int newA = (b - a)/N;
		int j = 0;

		for (int i = 0; i < b; i += newA){
			j += newA;
			System.out.println(i+ " "+ j);
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
	public static void notifyAllThreads(ArrayList<PrimeFinderThread> threads){
		for (PrimeFinderThread thread : threads){
			thread.resumeThread();
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
}
