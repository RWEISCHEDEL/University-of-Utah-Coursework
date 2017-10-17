package assignment10;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

/**
 * 
 * @author
 * 
 */
public class ChainingHashTable implements Set<String> {

	private LinkedList<String>[] chainList;
	private HashFunctor functor;
	private int size;
	
	private long numOfCollisions;

	/**
	 * This method constructs a new ChainingHashTable object.
	 */
	@SuppressWarnings("unchecked")
	public ChainingHashTable(int capacity, HashFunctor functor) {
		chainList = (LinkedList<String>[]) new LinkedList[capacity];
		this.functor = functor;
		size = 0;
		numOfCollisions = 0;
	}

	
	@Override
	public boolean add(String item) {
		
		if(item == null){
			return false;
		}

		if (isLoaded()) {
			reHash();
		}

		int index = findList(item);

		if (chainList[index] == null) {
			chainList[index] = new LinkedList<String>();
		}

		for (String search : chainList[index]) {
			if (search.equals(item)) {
				return false;
			}
			numOfCollisions++;
		}

		chainList[index].add(item);
		
		size++;

		return true;
	}

	@Override
	public boolean addAll(Collection<? extends String> items) {

		boolean wasAdded = false;

		for (String s : items) {
			if (add(s)) {
				wasAdded = true;
			}
		}

		return wasAdded;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void clear() {
		chainList = (LinkedList<String>[]) new LinkedList[chainList.length];
		size = 0;
		numOfCollisions = 0;
	}

	@Override
	public boolean contains(String item) {
		
		if(item == null){
			return true;
		}

		int index = findList(item);

		if (chainList[index] != null) {

			for (String s : chainList[index]) {
				if (s.equals(item)) {
					return true;
				}
			}

		}

		return false;
	}

	@Override
	public boolean containsAll(Collection<? extends String> items) {

		for (String s : items) {

			if (!contains(s)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public int size() {
		return size;
	}

	private boolean isLoaded() {
		return (double) size / chainList.length > 0.75;
	}

	private int findList(String item) {

		int hashValue = functor.hash(item);

		hashValue = Math.abs(hashValue % chainList.length);

		return hashValue;

	}

	private void reHash() {

		LinkedList<String>[] oldArray = Arrays.copyOf(chainList,
				chainList.length);

		@SuppressWarnings("unchecked")
		LinkedList<String>[] newArray = (LinkedList<String>[]) new LinkedList[oldArray.length * 2];
		
		size = 0;

		chainList = newArray;

		for (int i = 0; i < oldArray.length; i++) {
			if (oldArray[i] != null) {
				for (String item : oldArray[i]) {
					add(item);
				}
			}
		}

	}
	
	public int getLength(){
		return chainList.length;
	}
	
	public long getCollisions() {
		return numOfCollisions;
	}

}
