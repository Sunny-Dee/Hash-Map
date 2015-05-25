package csc403wk6;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;



public class HashMap403 <K, V> implements Iterable<V>{
	private int INIT_CAPACITY = 5;
	private double MAX_LAMBDA = .5;
	
	private static final int VACANT = 0, 
			OCCUPIED = 1, DELETED = 2;
	
	
    private int status[];
    private K[ ] keys;
    private V[ ] vals; 
	
	
	private int size = 0; // size of the set
	private int capacity = INIT_CAPACITY;
	
	
	public HashMap403(int initCapacity, double maxLambda) {
		capacity = initCapacity;
		MAX_LAMBDA = maxLambda;
		status = new int[capacity];
		keys = (K[ ]) new Object[capacity];
	    vals = (V[ ]) new Object[capacity];
	  }


	
	public String toString() {
		return " size " + size + " capacity " + capacity + "\n" + toListvals().toString();
	}
	
	private List<V> toListvals() {
		List<V> values = new ArrayList<V>();
		for (int i=0; i<capacity; i++)
			if (status[i] == OCCUPIED)
				values.add(vals[i]);
		return values;
	}
	
	private List<K> toListKeys() {
		List<K> keyList = new ArrayList<K>();
		for (int i=0; i<capacity; i++)
			if (status[i] == OCCUPIED)
				keyList.add(keys[i]);
		return keyList;
	}
	
	public V put(K k, V v) {
		int p = hashCode(k);
		//System.out.println(p);
		while (status[p] == OCCUPIED){ 
			if (keys[p].equals(k)){
				V oldVal = vals[p];
				vals[p] = v;
				return oldVal;
			}
			p = nextProbe(k, p);

			}
		
		status[p] = OCCUPIED;
		keys[p] = k;
		vals[p] = v;
		size++;
		if (size/((double) capacity) > MAX_LAMBDA)
			expandAndRehash();
		//System.out.println(p);
		return null;
			
	}
	
	public V get(K key) {
		int p = hashCode(key);
		
		while (status[p] != VACANT){
			if (key.equals(keys[p])){
				return vals[p];
			}
			p = nextProbe(key, p);
		}

		return null;
		}
	
	private int hashCode(K x) {
		probeNumber = 0;
		return (x.hashCode() & 0x7fffffff) % capacity;
	}

	
	int probeNumber = 0;
	int collisions = 0;
	
	
	private int nextProbe(K k, int p) {
		collisions++;
		return (p + probeNumber++* hashCode2(k)) % capacity;
	}
	
	private int hashCode2(K k){
		int P = prevPrime(capacity);
		return P - (k.hashCode()%P);
	}
	
	private static boolean isPrime(int x) {
		int sq = ((int) Math.sqrt(x));
		for (int i=2; i<sq; i++)
			if (x % i == 0)
				return false;
		return true;
	}
	
	// the smallest prime number > x
	private static int nextPrime(int x) {
		do x++;
		while (!isPrime(x));
		return x;
	}
	
	private static int prevPrime(int x) {
		do x--;
		while (!isPrime(x));
		return x;
	}
	
	
	private void expandAndRehash() {
		List<V> val = toListvals();
		List<K> key = toListKeys();
		capacity = nextPrime(capacity * 2);
		keys = (K[]) new Object[capacity];
		vals = (V[]) new Object[capacity];
		status = new int[capacity];
		int temp = size;
		size = 0;
		for (int i = 0; i<temp; i++){
			put(key.get(i), val.get(i));}
	}

	
	public boolean contains(K x) {
		int p = hashCode(x);
		while (status[p] != VACANT) {
			if (status[p] == OCCUPIED &&
				keys[p].equals(x))
				return true;
			p = nextProbe(x, p);
		}
		return false;
	}
	
	public V remove(K x) {
		int p = hashCode(x);
		while (status[p] != VACANT) {
			if (status[p] == OCCUPIED &&
				keys[p].equals(x)) {
				status[p] = DELETED;
				size--;
				return vals[p];
			}
			p = nextProbe(x, p);
		}
		return null;
	}
	
	public Set<K> keySet(){
		Set<K> keySet = new HashSet();; 
		for (int i = 0; i<keys.length; i++){
			if (keys[i] != null)
				keySet.add(keys[i]);
		}
		return keySet;
	}
	

	public Iterator<V> iterator() {
		return toListvals().iterator();
	}
	
	public static void main(String[] args) {
		System.out.println("Hash Map Double Hashing");
		HashMap403<Integer, Integer> map = new HashMap403(7, .75);
		map.put(0, 4);
		map.put(2, 8);
		map.put(7, 5);
		map.put(7, 6);
		map.put(9, 11);
		map.put(11, 12);
		map.put(13, 5);
		map.put(12, 1);
		
		System.out.println(map.get(7));
		System.out.println(map.toString());
		System.out.println(map.toListKeys());
		
		
		System.out.println(map.get(7));
		System.out.println(map.get(13));
		
		//map.remove(12);
		System.out.println(map.toString());
		System.out.println(map.keySet().toString());

	}
}

