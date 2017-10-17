/**
 * 
 */
package assignment10;

/**
 * @author jmurphy
 *
 */
public class MediocreHashFunctor implements HashFunctor {

	@Override
	public int hash(String item) {
		
		int count = 0;
		
		for(int i = 0; i < item.length(); i++){
			count += item.charAt(i);
		}
		
		return count;
	}

}
