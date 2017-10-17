package lab5;

public class BetterRandomNumberGenerator implements RandomNumberGenerator {

	long const1 = 15485863;
	
	long const2 = 15485868;
	
	long seed;
	@Override
	public int next_int(int max) {
		// TODO Auto-generated method stub
		set_constants(const1 + 8, const2 + 5);
		seed = ((const1 + 1 * seed + const2) % max);
		return (int) seed;
	}

	@Override
	public void set_seed(long seed) {
		// TODO Auto-generated method stub
		this.seed = seed;
		
	}

	@Override
	public void set_constants(long const1, long const2) {
		// TODO Auto-generated method stub
		this.const1 = const1;
		this.const2 = const2;
		
	}

}
