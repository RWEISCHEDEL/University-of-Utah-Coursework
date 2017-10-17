/**
 * 
 */
package assignment10;

/**
 * @author
 *
 */
public class GoodHashFunctor implements HashFunctor {

	@Override
	public int hash(String item) {
		
		int hashVal = 0;
		
		for(int i = 0; i < item.length(); i++){
			hashVal += 37 * item.charAt(i);
		}
		
		return hashVal;
	}

}
