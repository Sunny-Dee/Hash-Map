package csc403wk6;

import java.util.ArrayList;


public class HashMap403 <K, V> {
	private static int  capacity;
	private static int MAX_LAMBDA;
	private static final int VACANT = 0, 
			OCCUPIED = 1, DELETED = 2;
	
	private int status[];
	//Since java doesn't allow an array of generic types --> list of entries. 
	private ArrayList<Entry<K,V>> entries = new ArrayList<Entry<K,V>>();

	
	public HashMap403(int cap, int max){
		capacity = cap;
		MAX_LAMBDA = max;
		status = new int[capacity];
	}
	
	public class Entry<K,V> {
		public K key;
		public V value;
		
		private Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}


	//get takes a key and returns the corresponding value. 
	//if key not in the map returns null
	public V get(K key) {
		for (Entry entry : entries){
			if (key.equals(entry.key))
				return (V) entry.value;
			}
		return null;
		}

	
	
	/* 1. for put you'll hash on the key, that will determine the index in the list for that
	 * particular entry
	 * 2. if there is a collision fix it with double hashing 
	 */
	public V put(K key, V value) {
		int p = hashCode(key);
		
		if (entries.contains(key)){
			V oldVal = entries.get(p).value;
			entries.get(p).value = value;
			return oldVal;
		}
		
		
		if (status[p] == VACANT) {
			entries.add(p, new Entry<K,V>(key, value));
			return value;
		}
		
		else{
			p = nextProbe(key, p);
			entries.add(p, new Entry<K,V>(key, value));
			return value;
		}

//		for (int i=0; i<entries.size(); i++) {
//			int comp = entries.get(i).key.compareTo(key);
//			if (comp == 0) {
//				V oldVal = entries.get(i).value;
//				entries.get(i).value = value;
//				return oldVal;
//			}
//			else if (comp > 0)
//				entries.add(i, new Entry<K,V>(key, value));
//		}
//		entries.add(new Entry<K,V>(key, value));
		return null;
	}
	
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
		//int sq = ((int) Math.sqrt(x));
		for (int i=2; i<x; i++)
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
	
	public boolean contains(K key, V val) {
		int p = hashCode(key);
		while (status[p] != VACANT) {
			if (status[p] == OCCUPIED &&
				entries.get(p).equals(val))
				return true;
			p = nextProbe(key, p);
		}
		return false;
	}
	
	public static void main(String[] args) {
		System.out.println("Hash Map Double Hashing");
		HashMap403<Integer, Integer> map = new HashMap403(5, 11);
		
		int x = prevPrime(9);
		System.out.println(x);

	}

	
}