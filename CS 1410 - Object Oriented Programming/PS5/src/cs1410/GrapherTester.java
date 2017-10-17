package cs1410;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import graphics.GraphWindow;

import org.junit.Test;

public class GrapherTester
{
	/**
	 * @author Robert Weischedel, CS 1410
	 * These tests are for the methods in Grapher that don't require the Graphics object.
	 * The test____Exception__ cases are for all the exceptions in the method that are handled in the validator method,
	 * The testOperation__ cases are for the operation methods that perform the math operations on the desired data
	 */

	@Test (expected=IllegalArgumentException.class)
	public void testFirstException1()
	{
		String[] column1 = { "Utah", "Nevada", "California", "Oregon", "Utah", "California", "Nevada", "Nevada", "Oregon"};
		Integer[] column2 = { 5, 6, 12, 8, 9, 10, 4, 4, 17, 6};
		String[] categories = { "Utah", "Nevada", "California" };
		Color[] colors = { Color.red, Color.blue, Color.green };
		Grapher.validator(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories)),
				new ArrayList<Color>(Arrays.asList(colors)),0);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testFirstException2()
	{
		String[] column1 = { "Utah", "Nevada", "California", "Oregon", "Utah", "California", "Nevada", "Nevada", "Oregon", "California"};
		Integer[] column2 = { 5, 6, 12, 8, 9, 10, 4, 4, 17};
		String[] categories = { "Utah", "Nevada", "California" };
		Color[] colors = { Color.red, Color.blue, Color.green };
		Grapher.validator(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories)),
				new ArrayList<Color>(Arrays.asList(colors)),0);
	}

	/**
	 * A non-exceptional test case for "by-hand" testing
	 */
	@Test (expected=IllegalArgumentException.class)
	public void testSecondException()
	{
		String[] column1 = { "Utah", "Nevada", "California", "Oregon", "Utah", "California", "Nevada", "Nevada", "Oregon", "California"};
		Integer[] column2 = { 5, 6, 12, 8, 9, 10, -4, 4, 17, 6};
		String[] categories = { "Utah", "Nevada", "California" };
		Color[] colors = { Color.red, Color.blue, Color.green };
		Grapher.validator(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories)),
				new ArrayList<Color>(Arrays.asList(colors)),0);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testThirdException1()
	{
		String[] column1 = { "Utah", "Nevada", "California", "Oregon", "Utah", "California", "Nevada", "Nevada", "Oregon", "California"};
		Integer[] column2 = { 5, 6, 12, 8, 9, 10, 4, 4, 17, 6};
		String[] categories = { "Utah", "Nevada"};
		Color[] colors = { Color.red, Color.blue, Color.green};
		Grapher.validator(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories)),
				new ArrayList<Color>(Arrays.asList(colors)),0);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testThirdException2()
	{
		String[] column1 = { "Utah", "Nevada", "California", "Oregon", "Utah", "California", "Nevada", "Nevada", "Oregon", "California"};
		Integer[] column2 = { 5, 6, 12, 8, 9, 10, 4, 4, 17, 6};
		String[] categories = { "Utah", "Nevada", "California"};
		Color[] colors = { Color.red, Color.blue};
		Grapher.validator(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories)),
				new ArrayList<Color>(Arrays.asList(colors)),0);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testThirdException3()
	{
		String[] column1 = { "Utah", "Nevada", "California", "Oregon", "Utah", "California", "Nevada", "Nevada", "Oregon", "California"};
		Integer[] column2 = { 5, 6, 12, 8, 9, 10, 4, 4, 17, 6};
		String[] categories = {};
		Color[] colors = { Color.red, Color.blue, Color.green};
		Grapher.validator(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories)),
				new ArrayList<Color>(Arrays.asList(colors)),0);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testThirdException4()
	{
		String[] column1 = { "Utah", "Nevada", "California", "Oregon", "Utah", "California", "Nevada", "Nevada", "Oregon", "California"};
		Integer[] column2 = { 5, 6, 12, 8, 9, 10, 4, 4, 17, 6};
		String[] categories = {"Utah", "Nevada", "California"};
		Color[] colors = {};
		Grapher.validator(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories)),
				new ArrayList<Color>(Arrays.asList(colors)),0);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testFourthException()
	{
		String[] column1 = { "Utah", "Nevada", "California", "Oregon", "Utah", "California", "Nevada", "Nevada", "Oregon", "California"};
		Integer[] column2 = { 5, 6, 12, 8, 9, 10, 4, 4, 17, 6};
		String[] categories = {"Utah", "Nevada", "Utah"};
		Color[] colors = {Color.red, Color.blue, Color.green};
		Grapher.validator(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories)),
				new ArrayList<Color>(Arrays.asList(colors)),0);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testFifthException()
	{
		String[] column1 = { "Utah", "Nevada", "California", "Oregon", "Utah", "California", "Nevada", "Nevada", "Oregon", "California"};
		Integer[] column2 = { 5, 6, 12, 8, 9, 10, 4, 4, 17, 6};
		String[] categories = {"Utah", "Nevada", "California"};
		Color[] colors = {Color.red, Color.blue, Color.green};
		Grapher.validator(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories)),
				new ArrayList<Color>(Arrays.asList(colors)),5);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testSixthException()
	{
		String[] column1 = { "Utah", "Nevada", "California", "Oregon", "Utah", "California", "Nevada", "Nevada", "Oregon", "California"};
		Integer[] column2 = { 5, 6, 12, 8, 9, 10, 4, 4, 17, 6};
		String[] categories = {"Utah", "Nevada", "California"};
		Color[] colors = {Color.red, Color.blue, Color.green};
		Grapher.validator(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories)),
				new ArrayList<Color>(Arrays.asList(colors)),-1);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testSeventhException()
	{
		String[] column1 = { "Utah", "Nevada", "California", "Oregon", "Utah", "California", "Nevada", "Nevada", "Oregon", "California"};
		Integer[] column2 = { 5, 6, 12, 8, 9, 10, 4, 4, 17, 6};
		String[] categories = {"Utah", "Nevada", "Georgia"};
		Color[] colors = {Color.red, Color.blue, Color.green};
		Grapher.validator(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories)),
				new ArrayList<Color>(Arrays.asList(colors)),0);
	}
	
	@Test
	public void testOperator0A()
	{
		String[] column1 = { "Utah", "Nevada", "California", "Oregon", "Utah", "California", "Nevada", "Nevada", "Oregon", "California"};
		Integer[] column2 = { 5, 6, 12, 8, 9, 10, 4, 4, 17, 6};
		String[] categories = {"Utah", "Nevada", "California"};
		ArrayList<Integer> arraySummedValues = new ArrayList<Integer>();
		arraySummedValues.add(14);
		arraySummedValues.add(14);
		arraySummedValues.add(28);
		assertEquals(arraySummedValues, Grapher.operation0(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories))));

	}
	
	@Test
	public void testOperator0B()
	{
		String[] column1 = { "Utah", "Nevada", "California", "Oregon", "Utah", "California", "Nevada", "Nevada", "Oregon", "California"};
		Integer[] column2 = { 3, 8, 20, 5, 14, 6, 2, 1, 3, 6};
		String[] categories = {"Utah", "Nevada", "California"};
		ArrayList<Integer> arraySummedValues = new ArrayList<Integer>();
		arraySummedValues.add(17);
		arraySummedValues.add(11);
		arraySummedValues.add(32);
		assertEquals(arraySummedValues, Grapher.operation0(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories))));

	}
	
	@Test
	public void testOperator1A()
	{
		String[] column1 = { "Utah", "Nevada", "California", "Oregon", "Utah", "California", "Nevada", "Nevada", "Oregon", "California"};
		Integer[] column2 = { 5, 6, 12, 8, 9, 10, 4, 4, 17, 6};
		String[] categories = {"Utah", "Nevada", "California"};
		ArrayList<Integer> arrayAvgValues = new ArrayList<Integer>();
		arrayAvgValues.add(7);
		arrayAvgValues.add(4);
		arrayAvgValues.add(9);
		assertEquals(arrayAvgValues, Grapher.operation1(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories))));

	}
	
	@Test
	public void testOperator1B()
	{
		String[] column1 = { "Utah", "Nevada", "California", "Oregon", "Utah", "California", "Nevada", "Nevada", "Oregon", "California"};
		Integer[] column2 = { 3, 8, 20, 5, 14, 6, 2, 1, 3, 6};
		String[] categories = {"Utah", "Nevada", "California"};
		ArrayList<Integer> arrayAvgValues = new ArrayList<Integer>();
		arrayAvgValues.add(8);
		arrayAvgValues.add(3);
		arrayAvgValues.add(10);
		assertEquals(arrayAvgValues, Grapher.operation1(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories))));

	}
	
	@Test
	public void testOperator2A()
	{
		String[] column1 = { "Utah", "Nevada", "California", "Oregon", "Utah", "California", "Nevada", "Nevada", "Oregon", "California"};
		Integer[] column2 = { 5, 6, 12, 8, 9, 10, 4, 4, 17, 6};
		String[] categories = {"Utah", "Nevada", "California"};
		ArrayList<Integer> arrayMaxValues = new ArrayList<Integer>();
		arrayMaxValues.add(9);
		arrayMaxValues.add(6);
		arrayMaxValues.add(12);
		assertEquals(arrayMaxValues, Grapher.operation2(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories))));

	}
	
	@Test
	public void testOperator2B()
	{
		String[] column1 = { "Utah", "Nevada", "California", "Oregon", "Utah", "California", "Nevada", "Nevada", "Oregon", "California"};
		Integer[] column2 = { 3, 8, 20, 5, 14, 6, 2, 1, 3, 6};
		String[] categories = {"Utah", "Nevada", "California"};
		ArrayList<Integer> arrayMaxValues = new ArrayList<Integer>();
		arrayMaxValues.add(14);
		arrayMaxValues.add(8);
		arrayMaxValues.add(20);
		assertEquals(arrayMaxValues, Grapher.operation2(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories))));

	}

}
