package csc403wk6;

import java.util.ArrayList;


public class HashMap403 <K extends Comparable<K>, V> {
	private static int  capacity;
	private static int MAX_LAMBDA;
	private static final int VACANT = 0, 
			OCCUPIED = 1, DELETED = 2;
	
	private int status[];
	//Since java doesn't allow an array of generic types --> list of entries. 
	private ArrayList<Entry<K,V>> entries = new ArrayList<Entry<K,V>>();
	
//	private static final int[] PRIMES = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31,
//		37, 41, 43,  47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 
//		127, 131, 137, 139, 149, 151, 157, 163, 167, 173};
	
	public HashMap403(int cap, int max){
		capacity = cap;
		MAX_LAMBDA = max;
		status = new int[capacity];
	}
	
	private class Entry<K,V> {
		private K key;
		private V value;
		
		private Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}


	//get takes a key and returns the corresponding value. 
	//if key not in the map returns null
	public V get(K key) {
		int low = 0, high = entries.size()-1;
		while (low <= high) {
			int mid = (low + high)/2;
			int comp = entries.get(mid).key.compareTo(key);
			if (comp == 0) 
				return entries.get(mid).value;
			else if (comp > 0)
				high = mid-1;
			else // comp < 0
				low = mid + 1;
		}
		return null;
	}
	
	/* for put you'll hash on the key, that will determine the value for that
	 * particular entry
	 */
	
	private int hashCode(K x) {
		probeNumber = 0;
		return (x.hashCode() & 0x7fffffff) % capacity;
	}
	
	int probeNumber = 0;
	
	private int nextProbe(K x, int p) {
		return (p + probeNumber++* hashCode2(x)) % capacity;
	}
	
	private int hashCode2(K k){
		int P = prevPrime(capacity);
		return P - k.hashCode()%P;
	}
	
	
	private static boolean isPrime(int x) {
		int sq = ((int) Math.sqrt(x));
		for (int i=2; i<sq; i++)
			if (x % i == 0)
				return false;
		return true;
	}
	
	// the smallest prime number > x
	private static int prevPrime(int x) {
		do x--;
		while (!isPrime(x));
		return x;
	}
	
	
	
	
	/*Finds the next lowest prime from a list of prime numbers
	 */
//	private int findPrime(int p){
//		int low = 0, high = PRIMES.length-1;
//		while (low <= high) {
//			int mid = (low + high)/2;
//			int comp = PRIMES[mid] - p;
//			if (comp == 0) 
//					return PRIMES[mid - 1];
//			else if (comp > 0)
//				high = mid-1;
//			else // comp < 0
//				low = mid + 1;
//		}
//		return 0;
//	}
	
}