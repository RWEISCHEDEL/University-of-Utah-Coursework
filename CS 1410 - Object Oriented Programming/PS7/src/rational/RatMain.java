package rational;

import java.util.ArrayList;

/**
 * This method tests the Rat class and its methods.
 * @author Joe Zachary
 *
 */
public class RatMain
{
    public static void main (String[] args)
    {
        Rat r1 = new Rat(3,4);
        Rat r2 = new Rat(6,8);
        Rat r3 = r1;
        Rat r4 = new Rat(1,2);
        Rat r5 = new Rat(1,3);
        Rat r6 = new Rat(7,5);
        Rat r7 = new Rat(3,9);
        Rat r8 = new Rat(0,9);
        
        System.out.println("r1 == r2: " + (r1 == r2));
        System.out.println("r1 == r3: " + (r1 == r3));
        System.out.println("r2 == r3: " + (r2 == r3));
        System.out.println("r1 == r4: " + (r1 == r4));
        
        System.out.println("r1.equals(r2): " + r1.equals(r2));
        System.out.println("r1.equals(r3): " + r1.equals(r3));
        System.out.println("r2.equals(r3): " + r2.equals(r3)); 
        System.out.println("r1.equals(r4): " + r1.equals(r4)); 
        
        ArrayList<Rat> ar1 = new ArrayList<Rat>();
        ar1.add(r1);
        
        ArrayList<Rat> ar2 = new ArrayList<Rat>();
        ar2.add(r2);
        
        System.out.println("ar1.equals(ar2): " + ar1.equals(ar2));
        
        System.out.println(r1);
        System.out.println(r2);
        System.out.println(r3);
        System.out.println(r4);
        
        System.out.println("Adding r4 amd r4 : " + r5.div(r5));
        
    }
}
