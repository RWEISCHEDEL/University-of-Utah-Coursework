using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using SpreadsheetUtilities;
using System.Collections.Generic;
/// <summary>
/// This class is a series of tests for the DependencyGraph class in the SpreadsheetUtilites project.
/// Robert Weischedel CS 3500 9/17/15
/// </summary>
namespace MyPS2Tester
{
    /// <summary>
    /// This testing suite contians a variety of methods to test the DependencyGraph class and its associated methods.
    /// </summary>
    [TestClass]
    public class UnitTest1
    {
        /// <summary>
        /// Test 1 add a few depenencies and make sure that all are added correctly.
        /// </summary>
        [TestMethod]
        public void TestMethod1()
        {
            DependencyGraph a1 = new DependencyGraph();
            a1.AddDependency("a", "t");
            a1.AddDependency("a", "s");
            a1.AddDependency("s", "b");
            a1.AddDependency("s", "z");
            Assert.AreEqual(4, a1.Size);
        }

        /// <summary>
        /// Test 2 add a few depenencies and make sure that all are added correctly, then add an additional dependency.
        /// </summary>
        [TestMethod]
        public void TestMethod2()
        {
            DependencyGraph a1 = new DependencyGraph();
            a1.AddDependency("a", "t");
            a1.AddDependency("a", "s");
            a1.AddDependency("s", "b");
            a1.AddDependency("s", "z");
            Assert.AreEqual(4, a1.Size);

            a1.AddDependency("s", "a");
            Assert.AreEqual(5, a1.Size);

        }

        /// <summary>
        /// Test 3 after adding a few dependices try to add the same dependcy twice to see if it counts twice.
        /// </summary>
        [TestMethod]
        public void TestMethod3()
        {
            DependencyGraph a1 = new DependencyGraph();
            a1.AddDependency("a", "t");
            a1.AddDependency("a", "s");
            a1.AddDependency("s", "b");
            a1.AddDependency("s", "z");
            Assert.AreEqual(4, a1.Size);

            a1.AddDependency("s", "a");
            Assert.AreEqual(5, a1.Size);

            a1.AddDependency("s", "a");
            Assert.AreEqual(5, a1.Size);
        }

        /// <summary>
        /// Test 4 add a few dependices and then add a few more including one were both values are already accounted for.
        /// </summary>
        [TestMethod]
        public void TestMethod4()
        {
            DependencyGraph a1 = new DependencyGraph();
            a1.AddDependency("a", "t");
            a1.AddDependency("a", "s");
            a1.AddDependency("s", "b");
            a1.AddDependency("s", "z");
            Assert.AreEqual(4, a1.Size);

            a1.AddDependency("t", "a");
            a1.AddDependency("s", "a"); // Ordered pair already accounted for
            a1.AddDependency("b", "s");
            a1.AddDependency("z", "s");
            Assert.AreEqual(7, a1.Size);

        }
        /// <summary>
        /// Test 5 add a few dependices and then add the exact same depencies again to ensure that they aren't added again.
        /// </summary>
        [TestMethod]
        public void TestMethod5()
        {
            DependencyGraph a1 = new DependencyGraph();
            a1.AddDependency("a", "t");
            a1.AddDependency("a", "s");
            a1.AddDependency("s", "b");
            a1.AddDependency("s", "z");
            Assert.AreEqual(4, a1.Size);

            a1.AddDependency("a", "t");
            a1.AddDependency("a", "s");
            a1.AddDependency("s", "b");
            a1.AddDependency("s", "z");
            Assert.AreEqual(4, a1.Size);

        }

        /// <summary>
        /// Test 6 add a few dependices and then remove one.
        /// </summary>
        [TestMethod]
        public void TestMethod6()
        {
            DependencyGraph a1 = new DependencyGraph();
            a1.AddDependency("t", "a");
            a1.AddDependency("s", "a");
            a1.AddDependency("b", "s");
            a1.AddDependency("z", "s");
            Assert.AreEqual(4, a1.Size);

            a1.RemoveDependency("t", "a");
            Assert.AreEqual(3, a1.Size);
        }
        /// <summary>
        /// Test 7 add a few dependices and then remove two.
        /// </summary>
        [TestMethod]
        public void TestMethod7()
        {
            DependencyGraph a1 = new DependencyGraph();
            a1.AddDependency("t", "a");
            a1.AddDependency("s", "a");
            a1.AddDependency("b", "s");
            a1.AddDependency("z", "s");
            Assert.AreEqual(4, a1.Size);

            a1.RemoveDependency("t", "a");
            a1.RemoveDependency("s", "a");
            Assert.AreEqual(2, a1.Size);
        }
        /// <summary>
        /// Test 8 add a few dependices and then remove three.
        /// </summary>
        [TestMethod]
        public void TestMethod8()
        {
            DependencyGraph a1 = new DependencyGraph();
            a1.AddDependency("t", "a");
            a1.AddDependency("s", "a");
            a1.AddDependency("b", "s");
            a1.AddDependency("z", "s");
            Assert.AreEqual(4, a1.Size);

            a1.RemoveDependency("t", "a");
            a1.RemoveDependency("s", "a");
            a1.RemoveDependency("b", "s");
            Assert.AreEqual(1, a1.Size);
        }
        /// <summary>
        /// Test 9 add a few dependices and then remove all of them.
        /// </summary>
        [TestMethod]
        public void TestMethod9()
        {
            DependencyGraph a1 = new DependencyGraph();
            a1.AddDependency("t", "a");
            a1.AddDependency("s", "a");
            a1.AddDependency("b", "s");
            a1.AddDependency("z", "s");
            Assert.AreEqual(4, a1.Size);

            a1.RemoveDependency("t", "a");
            a1.RemoveDependency("s", "a");
            a1.RemoveDependency("b", "s");
            a1.RemoveDependency("z", "s");
            Assert.AreEqual(0, a1.Size);
        }
        /// <summary>
        /// Test 10 add a few dependices and then remove all of them and then add one once again.
        /// </summary>
        [TestMethod]
        public void TestMethod10()
        {
            DependencyGraph a1 = new DependencyGraph();
            a1.AddDependency("t", "a");
            a1.AddDependency("s", "a");
            a1.AddDependency("b", "s");
            a1.AddDependency("z", "s");
            Assert.AreEqual(4, a1.Size);

            a1.RemoveDependency("t", "a");
            a1.RemoveDependency("s", "a");
            a1.RemoveDependency("b", "s");
            a1.RemoveDependency("z", "s");
            Assert.AreEqual(0, a1.Size);

            a1.AddDependency("r", "w");
            Assert.AreEqual(1, a1.Size);
        }
        /// <summary>
        /// Test 11 see if a value that doesn't belong in the Graph has any dependees.
        /// </summary>
        [TestMethod]
        public void TestMethod11()
        {
            DependencyGraph a1 = new DependencyGraph();
            Assert.IsFalse(a1.HasDependees("a"));
        }
        /// <summary>
        /// Test 12 see if a value that doesn't belong in the Graph has any dependents.
        /// </summary>
        [TestMethod]
        public void TestMethod12()
        {
            DependencyGraph a1 = new DependencyGraph();
            Assert.IsFalse(a1.HasDependents("b"));
        }
        /// <summary>
        /// Test 13 see if all the values that are first entered in have dependents attached to them.
        /// </summary>
        [TestMethod]
        public void TestMethod13()
        {
            DependencyGraph a1 = new DependencyGraph();
            a1.AddDependency("t", "a");
            a1.AddDependency("s", "a");
            a1.AddDependency("b", "s");
            a1.AddDependency("z", "s");

            Assert.IsTrue(a1.HasDependents("t"));

            Assert.IsTrue(a1.HasDependents("s"));

            Assert.IsTrue(a1.HasDependents("b"));

            Assert.IsTrue(a1.HasDependents("z"));
        }
        /// <summary>
        /// Test 14 see if all values entered in second all have dependees, and as well test and see if s has both.
        /// </summary>
        [TestMethod]
        public void TestMethod14()
        {
            DependencyGraph a1 = new DependencyGraph();
            a1.AddDependency("t", "a");
            a1.AddDependency("s", "a");
            a1.AddDependency("b", "s");
            a1.AddDependency("z", "s");

            Assert.IsFalse(a1.HasDependees("t"));

            Assert.IsTrue(a1.HasDependees("s"));

            Assert.IsTrue(a1.HasDependents("s"));

            Assert.IsFalse(a1.HasDependees("b"));

            Assert.IsFalse(a1.HasDependees("z"));
        }
        /// <summary>
        /// Test 15 see if a value that is entered in twice in the second spot only has dependees and not dependents.
        /// </summary>
        [TestMethod]
        public void TestMethod15()
        {
            DependencyGraph a1 = new DependencyGraph();
            a1.AddDependency("t", "a");
            a1.AddDependency("s", "a");
            a1.AddDependency("b", "s");
            a1.AddDependency("z", "s");

            Assert.IsFalse(a1.HasDependents("a"));

            Assert.IsTrue(a1.HasDependees("a"));

            Assert.IsTrue(a1.HasDependents("s"));
        }
        /// <summary>
        /// Test 16 to ensure that a input that should only have dependents doesn't have any dependees.
        /// </summary>
        [TestMethod]
        public void TestMethod16()
        {
            DependencyGraph a1 = new DependencyGraph();
            a1.AddDependency("t", "a");
            a1.AddDependency("s", "a");
            a1.AddDependency("b", "s");
            a1.AddDependency("z", "s");

            Assert.IsTrue(a1.HasDependents("z"));

            Assert.IsFalse(a1.HasDependees("z"));
        }
        /// <summary>
        /// Test 17 ensure that a empty DependencyGraph has no dependees for a particular value.
        /// </summary>
        [TestMethod]
        public void TestMethod17()
        {
            DependencyGraph a1 = new DependencyGraph();
            Assert.IsFalse(a1.GetDependees("z").GetEnumerator().MoveNext());
        }

        /// <summary>
        /// Test 18 ensure that a empty DependencyGraph has no dependents for a particular value.
        /// </summary>
        [TestMethod]
        public void TestMethod18()
        {
            DependencyGraph a1 = new DependencyGraph();
            Assert.IsFalse(a1.GetDependents("z").GetEnumerator().MoveNext());
        }

        /// <summary>
        ///Test 19 ensure that using the getting number of depencies method returns 0 on Graph that has no values.
        ///</summary>
        [TestMethod]
        public void TestMethod19()
        {
            DependencyGraph t = new DependencyGraph();
            Assert.AreEqual(0, t["z"]);
        }
        /// <summary>
        /// Test 20 to ensure that get dependents is working properly by adding values and ensuring those that should have dependents do.
        /// </summary>
        [TestMethod]
        public void TestMethod20()
        {
            DependencyGraph a1 = new DependencyGraph();
            a1.AddDependency("t", "a");
            a1.AddDependency("s", "a");
            a1.AddDependency("b", "s");
            a1.AddDependency("z", "s");

            HashSet<String> aDents = new HashSet<String>(a1.GetDependents("a"));
            HashSet<String> bDents = new HashSet<String>(a1.GetDependents("b"));
            HashSet<String> sDents = new HashSet<String>(a1.GetDependents("s"));
            HashSet<String> tDents = new HashSet<String>(a1.GetDependents("t"));
            HashSet<String> zDents = new HashSet<String>(a1.GetDependents("z"));

            Assert.AreEqual(0, aDents.Count);
            Assert.AreEqual(1, bDents.Count);
            Assert.AreEqual(1, sDents.Count);
            Assert.AreEqual(1, tDents.Count);
            Assert.AreEqual(1, zDents.Count);

            Assert.IsTrue(bDents.Contains("s"));
            Assert.IsTrue(sDents.Contains("a"));
            Assert.IsTrue(tDents.Contains("a"));
            Assert.IsTrue(zDents.Contains("s"));
        }
        /// <summary>
        /// Test 21 to ensure that get dependees is working properly by adding values and ensuring those that should have dependees do.
        /// </summary>
        [TestMethod]
        public void TestMethod21()
        {
            DependencyGraph a1 = new DependencyGraph();
            a1.AddDependency("t", "a");
            a1.AddDependency("s", "a");
            a1.AddDependency("b", "s");
            a1.AddDependency("z", "s");

            HashSet<String> aDees = new HashSet<String>(a1.GetDependees("a"));
            HashSet<String> bDees = new HashSet<String>(a1.GetDependees("b"));
            HashSet<String> sDees = new HashSet<String>(a1.GetDependees("s"));
            HashSet<String> tDees = new HashSet<String>(a1.GetDependees("t"));
            HashSet<String> zDees = new HashSet<String>(a1.GetDependees("z"));

            Assert.AreEqual(2, aDees.Count);
            Assert.AreEqual(0, bDees.Count);
            Assert.AreEqual(2, sDees.Count);
            Assert.AreEqual(0, tDees.Count);
            Assert.AreEqual(0, zDees.Count);

            Assert.IsTrue(aDees.Contains("s") && aDees.Contains("t"));
            Assert.IsTrue(sDees.Contains("b") && sDees.Contains("z"));
        }
        /// <summary>
        /// Test 22 method tests the this constructor to return the number of dependents of each string key value.
        /// </summary>
        [TestMethod]
        public void TestMethod22()
        {
            DependencyGraph a1 = new DependencyGraph();
            a1.AddDependency("t", "a");
            a1.AddDependency("s", "a");
            a1.AddDependency("b", "s");
            a1.AddDependency("z", "s");

            Assert.AreEqual(2, a1["a"]);
            Assert.AreEqual(0, a1["b"]);
            Assert.AreEqual(2, a1["s"]);
            Assert.AreEqual(0, a1["t"]);
            Assert.AreEqual(0, a1["z"]);
        }
        /// <summary>
        /// Test 24 method tests the ReplaceDependents method with a value that has no dependents.
        /// </summary>
        [TestMethod]
        public void TestMethod23()
        {
            DependencyGraph a1 = new DependencyGraph();
            a1.AddDependency("t", "a");
            a1.AddDependency("s", "a");
            a1.AddDependency("b", "s");
            a1.AddDependency("z", "s");
            HashSet<String> oldDends = new HashSet<string>(a1.GetDependents("a"));

            Assert.IsTrue(oldDends.Count == 0);

            a1.ReplaceDependents("a", new HashSet<String>() { "w", "o" });
            HashSet<String> newDends = new HashSet<string>(a1.GetDependents("a"));

            Assert.IsTrue(newDends.Count == 2 && newDends.Contains("w") && newDends.Contains("o"));
            Assert.IsTrue(newDends.SetEquals(new HashSet<string>() { "w", "o"}));
        }
        /// <summary>
        /// Test 25 method tests the ReplaceDependents method with a value that has one dependent.
        /// </summary>
        [TestMethod]
        public void TestMethod25()
        {
            DependencyGraph a1 = new DependencyGraph();
            a1.AddDependency("t", "a");
            a1.AddDependency("s", "a");
            a1.AddDependency("b", "s");
            a1.AddDependency("z", "s");
            HashSet<String> oldDends = new HashSet<string>(a1.GetDependents("s"));

            Assert.IsTrue(oldDends.Count == 1);

            a1.ReplaceDependents("s", new HashSet<String>() { "w", "o" });
            HashSet<String> newDends = new HashSet<string>(a1.GetDependents("s"));

            Assert.IsTrue(newDends.Count == 2 && newDends.Contains("w") && newDends.Contains("o"));
            Assert.IsTrue(newDends.SetEquals(new HashSet<string>() { "w", "o" }));
        }
        /// <summary>
        /// Test 26 method tests the ReplaceDependees method with a value that has two dependees.
        /// </summary>
        [TestMethod]
        public void TestMethod26()
        {
            DependencyGraph a1 = new DependencyGraph();
            a1.AddDependency("t", "a");
            a1.AddDependency("s", "a");
            a1.AddDependency("b", "s");
            a1.AddDependency("z", "s");
            HashSet<String> oldDees = new HashSet<string>(a1.GetDependees("a"));

            Assert.IsTrue(oldDees.Count == 2 && oldDees.Contains("t") && oldDees.Contains("s"));

            a1.ReplaceDependees("a", new HashSet<String>() { "w", "o" });
            HashSet<String> newDees = new HashSet<string>(a1.GetDependees("a"));

            Assert.IsTrue(newDees.Count == 2 && newDees.Contains("w") && newDees.Contains("o"));
            Assert.IsTrue(newDees.SetEquals(new HashSet<string>() { "w", "o" }));
        }

        // These methods and all subsequent methods test the stringValidator method to ensure the correct string input is given.

        /// <summary>
        /// Test 27 test stringValidator in the hasDependents method.
        /// </summary>
        [TestMethod]
        public void TestMethod27()
        {
            DependencyGraph a1 = new DependencyGraph();
            a1.AddDependency("t", "a");
            a1.AddDependency("s", "a");
            a1.AddDependency("b", "s");
            a1.AddDependency("z", "s");

            Assert.IsTrue(a1.HasDependents("t"));

            Assert.IsFalse(a1.HasDependents(""));
        }
        /// <summary>
        /// Test 28 test stringValidator in the hasDependees method.
        /// </summary>
        [TestMethod]
        public void TestMethod28()
        {
            DependencyGraph a1 = new DependencyGraph();
            a1.AddDependency("t", "a");
            a1.AddDependency("s", "a");
            a1.AddDependency("b", "s");
            a1.AddDependency("z", "s");

            Assert.IsTrue(a1.HasDependees("a"));

            Assert.IsFalse(a1.HasDependees(""));
        }
        /// <summary>
        /// Test 29 test stringValidator in the getDependees method.
        /// </summary>
        [TestMethod]
        public void TestMethod29()
        {
            DependencyGraph a1 = new DependencyGraph();
            a1.AddDependency("t", "a");
            a1.AddDependency("s", "a");
            a1.AddDependency("b", "s");
            a1.AddDependency("z", "s");

            HashSet<String> aDees = new HashSet<String>(a1.GetDependees("a"));

            Assert.AreEqual(2, aDees.Count);

            HashSet<String> emptyDees = new HashSet<String>(a1.GetDependees(""));

            Assert.AreEqual(0, emptyDees.Count);

        }
        /// <summary>
        /// Test 30 test stringValidator in the getDependents method.
        /// </summary>
        [TestMethod]
        public void TestMethod30()
        {
            DependencyGraph a1 = new DependencyGraph();
            a1.AddDependency("t", "a");
            a1.AddDependency("s", "a");
            a1.AddDependency("b", "s");
            a1.AddDependency("z", "s");

            HashSet<String> bDents = new HashSet<String>(a1.GetDependents("b"));

            Assert.AreEqual(1, bDents.Count);

            HashSet<String> emptyDents = new HashSet<String>(a1.GetDependents(""));

            Assert.AreEqual(0, emptyDents.Count);
        }
        /// <summary>
        /// Test 31 test stringValidator in the this method for the first input.
        /// </summary>
        [TestMethod]
        public void TestMethod31()
        {
            DependencyGraph a1 = new DependencyGraph();
            a1.AddDependency("t", "a");
            a1.AddDependency("s", "a");
            a1.AddDependency("b", "s");
            a1.AddDependency("", "s");

            Assert.AreEqual(3, a1.Size);

            Assert.AreEqual(2, a1["a"]);
            Assert.AreEqual(0, a1["b"]);
            Assert.AreEqual(1, a1["s"]);
            Assert.AreEqual(0, a1["t"]);
            Assert.AreEqual(0, a1[""]);
        }
        /// <summary>
        /// Test 32 test stringValidator in the this method for the second input.
        /// </summary>
        [TestMethod]
        public void TestMethod32()
        {
            DependencyGraph a1 = new DependencyGraph();
            a1.AddDependency("t", "a");
            a1.AddDependency("s", "a");
            a1.AddDependency("b", "s");
            a1.AddDependency("z", "");

            Assert.AreEqual(3, a1.Size);

            Assert.AreEqual(2, a1["a"]);
            Assert.AreEqual(0, a1["b"]);
            Assert.AreEqual(1, a1["s"]);
            Assert.AreEqual(0, a1["t"]);
            Assert.AreEqual(0, a1["z"]);
        }
        /// <summary>
        /// Test 33 test stringValidator in the removeDependency method for the second input.
        /// </summary>
        [TestMethod]
        public void TestMethod33()
        {
            DependencyGraph a1 = new DependencyGraph();
            a1.AddDependency("t", "a");
            a1.AddDependency("s", "a");
            a1.AddDependency("b", "s");
            a1.AddDependency("z", "s");

            Assert.AreEqual(4, a1.Size);

            a1.RemoveDependency("z", "");

            Assert.AreEqual(4, a1.Size);
        }
        /// <summary>
        /// Test 34 test stringValidator in the removeDependency method for the first input.
        /// </summary>
        [TestMethod]
        public void TestMethod34()
        {
            DependencyGraph a1 = new DependencyGraph();
            a1.AddDependency("t", "a");
            a1.AddDependency("s", "a");
            a1.AddDependency("b", "s");
            a1.AddDependency("z", "s");

            Assert.AreEqual(4, a1.Size);

            a1.RemoveDependency("", "s");

            Assert.AreEqual(4, a1.Size);

            
        }
        /// <summary>
        /// Test 35 test stringValidator in the replaceDependents method.
        /// </summary>
        [TestMethod]
        public void TestMethod35()
        {
            DependencyGraph a1 = new DependencyGraph();
            a1.AddDependency("t", "a");
            a1.AddDependency("s", "a");
            a1.AddDependency("b", "s");
            a1.AddDependency("z", "s");

            HashSet<String> oldDends = new HashSet<string>(a1.GetDependents("s"));

            Assert.IsTrue(oldDends.Count == 1);

            a1.ReplaceDependents("", new HashSet<String>() { "w", "o" });
            HashSet<String> newDends = new HashSet<string>(a1.GetDependents("s"));

            Assert.IsTrue(newDends.SetEquals(oldDends));
        }
        /// <summary>
        /// Test 36 test stringValidator in the replaceDependees method.
        /// </summary>
        [TestMethod]
        public void TestMethod36()
        {
            DependencyGraph a1 = new DependencyGraph();
            a1.AddDependency("t", "a");
            a1.AddDependency("s", "a");
            a1.AddDependency("b", "s");
            a1.AddDependency("z", "s");

            HashSet<String> oldDees = new HashSet<string>(a1.GetDependees("a"));

            Assert.IsTrue(oldDees.Count == 2);

            a1.ReplaceDependees("", new HashSet<String>() { "w", "o" });
            HashSet<String> newDees = new HashSet<string>(a1.GetDependees("a"));

            Assert.IsTrue(newDees.SetEquals(oldDees));
        }
        /// <summary>
        /// Test 37 test stringValidator in the addDependency method for the first input.
        /// </summary>
        [TestMethod]
        public void TestMethod37()
        {
            DependencyGraph a1 = new DependencyGraph();
            a1.AddDependency("t", "a");
            a1.AddDependency("s", "a");
            a1.AddDependency("b", "s");
            a1.AddDependency("z", "s");

            Assert.AreEqual(4, a1.Size);

            a1.AddDependency("", "s");

            Assert.AreEqual(4, a1.Size);
        }
        /// <summary>
        /// Test 38 test stringValidator in the addDependency method for the second input.
        /// </summary>
        [TestMethod]
        public void TestMethod38()
        {
            DependencyGraph a1 = new DependencyGraph();
            a1.AddDependency("t", "a");
            a1.AddDependency("s", "a");
            a1.AddDependency("b", "s");
            a1.AddDependency("z", "s");

            Assert.AreEqual(4, a1.Size);

            a1.AddDependency("z", "");

            Assert.AreEqual(4, a1.Size);
        }


    }
}
