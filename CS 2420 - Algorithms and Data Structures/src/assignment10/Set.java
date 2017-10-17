package assignment10;

import java.util.Collection;


/**
 * An interface for representing a set of generically-typed items. By
 * definition, a set contains no duplicate items. Note that this
 * interface is much like Java's Set, but simpler.
 *
 *
 */
public interface Set<Type> {

  /**
   * Ensures that this set contains the specified item.
   * 
   * @param item -
   *          the item whose presence is ensured in this set
   * @return true if this set changed as a result of this method call (that is,
   *         if the input item was actually inserted); otherwise, returns false
   */
  public boolean add(Type item);

  /**
   * Ensures that this set contains all items in the specified collection.
   * 
   * @param items -
   *          the collection of items whose presence is ensured in this set
   * @return true if this set changed as a result of this method call (that is,
   *         if any item in the input collection was actually inserted);
   *         otherwise, returns false
   */
  public boolean addAll(Collection<? extends Type> items);

  /**
   * Removes all items from this set. The set will be empty after this method
   * call.
   */
  public void clear();

  /**
   * Determines if there is an item in this set that is equal to the specified
   * item.
   * 
   * @param item -
   *          the item sought in this set
   * @return true if there is an item in this set that is equal to the input
   *         item; otherwise, returns false
   */
  public boolean contains(Type item);

  /**
   * Determines if for each item in the specified collection, there is an item
   * in this set that is equal to it.
   * 
   * @param items -
   *          the collection of items sought in this set
   * @return true if for each item in the specified collection, there is an item
   *         in this set that is equal to it; otherwise, returns false
   */

  public boolean containsAll(Collection<? extends Type> items);

  /**
   * Returns true if this set contains no items.
   */
  public boolean isEmpty();
  
  /**
   * Returns the number of items in this set.
   */
  public int size();
}

