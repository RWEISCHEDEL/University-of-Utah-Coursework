/**
 * 
 */
package assignment10;

/**
 * @author 
 *
 */
public class BadHashFunctor implements HashFunctor {

	@Override
	public int hash(String item) {
		return item.length();
	}

}
