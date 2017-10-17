package lab4;

public class Part2 {

	public static void infiniteLoops() {
		int a = 1, b = 0, c = -1, d = 3, f = 5;

		//Loop #1
		while(a < 10) {
			a += d;
			d -= c;
			a++;
			try {
				Thread.sleep(100);
			} catch(InterruptedException e) { }
		}

		//Loop #2
		/*while(b < 10) {
			b *= a;
			a += c;
			d++;
			try {
				Thread.sleep(100);
			} catch(InterruptedException e) { }
		}*/

		//Loop #3
		while(a > c) {
			a += d;
			d--;
			b++;
			try {
				Thread.sleep(100);
			} catch(InterruptedException e) { }
		}

		//Loop #4
		while(f > 0) {
			f -= c;
			b += c;
			c *= -1;
			try {
				Thread.sleep(100);
			} catch(InterruptedException e) { }
		}
	}
	
}