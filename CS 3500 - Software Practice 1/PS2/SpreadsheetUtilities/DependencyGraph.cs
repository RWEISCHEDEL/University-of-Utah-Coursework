// Skeleton implementation written by Joe Zachary for CS 3500, September 2013.
// Version 1.1 (Fixed error in comment for RemoveDependency.)

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpreadsheetUtilities
{

    /// <summary>
    /// (s1,t1) is an ordered pair of strings
    /// s1 depends on t1 --> t1 must be evaluated before s1
    /// 
    /// A DependencyGraph can be modeled as a set of ordered pairs of strings.  Two ordered pairs
    /// (s1,t1) and (s2,t2) are considered equal if and only if s1 equals s2 and t1 equals t2.
    /// (Recall that sets never contain duplicates.  If an attempt is made to add an element to a 
    /// set, and the element is already in the set, the set remains unchanged.)
    /// 
    /// Given a DependencyGraph DG:
    /// 
    ///    (1) If s is a string, the set of all strings t such that (s,t) is in DG is called dependents(s).
    ///        
    ///    (2) If s is a string, the set of all strings t such that (t,s) is in DG is called dependees(s).
    ///
    /// For example, suppose DG = {("a", "b"), ("a", "c"), ("b", "d"), ("d", "d")}
    ///     dependents("a") = {"b", "c"}
    ///     dependents("b") = {"d"}
    ///     dependents("c") = {}
    ///     dependents("d") = {"d"}
    ///     dependees("a") = {}
    ///     dependees("b") = {"a"}
    ///     dependees("c") = {"a"}
    ///     dependees("d") = {"b", "d"}
    ///  Robert Weischedel CS 3500 9/17/15
    /// </summary>
    public class DependencyGraph
    {
        // Private Member variables to hold the Dependents, Dependees, and the number of ordered pairs
        private Dictionary<String, HashSet<String>> dependents;
        private Dictionary<String, HashSet<String>> dependees;
        private int orderedPairs;

        /// <summary>
        /// This serves as the constructor for this library, it creates an empty DependencyGraph.
        /// </summary>
        public DependencyGraph()
        {
            // Create a two new Dictionaries that hold the dependents and dependees, also set number of orderedPairs to zero.
            dependents = new Dictionary<string, HashSet<string>>();
            dependees = new Dictionary<string, HashSet<string>>();
            orderedPairs = 0;
        }


        /// <summary>
        /// Returns number of ordered pairs in the DependencyGraph.
        /// </summary>
        /// <returns>A integer value indicating how many order pairs exist in the DependencyGraph</returns>
        public int Size
        {
            // Return number of order pairs.
            get { return orderedPairs; }
        }


        /// <summary>
        /// The size of dependees(s).
        /// This property is an example of an indexer.  If dg is a DependencyGraph, you would
        /// invoke it like this:
        /// dg["a"]
        /// It should return the size of dependees("a")
        /// </summary>
        /// <param name="s">The name of the string you wish to know home many dependent key values there are.</param>
        /// <return>A integer value indicating how many dependent values currently exist.</return>
        public int this[string s]
        {
            // If the string s is a key in dependents, return the number of dependent keys there are. Or return 0 if there are none.
            get
            {
                if (dependents.ContainsKey(s))
                {
                    return dependents[s].Count();
                }
                else
                {

                    return 0;
                }
            }
        }


        /// <summary>
        /// Reports whether a given inputs dependents(s) is non-empty.
        /// </summary>
        /// <param name="s">The name of the string you wish to see if it has any dependents associated with it.</param>
        /// <returns>A boolean value indicating wether or not the input string has dependents associated with it.</returns>
        public bool HasDependents(string s)
        {
            // Check and see if s is a valid input string, if its not return false.
            if (!stringValidator(s, " "))
            {
                return false;
            }

            // If dependees contains the string key s, return true.
            if (dependees.ContainsKey(s))
            {
                return true;
            }
            return false;
        }


        /// <summary>
        /// Reports whether dependees(s) is non-empty.
        /// </summary>
        /// <param name="s">The name of the string you wish to see if it has any dependees associated with it</param>
        /// <returns>A boolean value indicating wether or not the input string has dependees associated with it.</returns>
        public bool HasDependees(string s)
        {
            // Check and see if s is a valid input string, if its not return false.
            if (!stringValidator(s, " "))
            {
                return false;
            }

            // If dependents contains the string key s, return true.
            if (dependents.ContainsKey(s))
            {
                return true;
            }
            return false;
        }


        /// <summary>
        /// Given a string s it returns all the depenents assocaited with it. Or if it has none or it doesn't exist, it returns an empty list.
        /// </summary>
        /// <param name="s">A string that you wish to get all the dependent values of.</param>
        /// <returns>A IEnumerable obj that contains all the dependents of string s.</returns>
        public IEnumerable<string> GetDependents(string s)
        {
            // Check and see if s is a valid input string, if its not return a empty HashSet.
            if (!stringValidator(s, " "))
            {
                return new HashSet<String>();
            }

            // If dependees contains the key s, return a HashSet containing all its dependent values, or return an empty list.
            if (dependees.ContainsKey(s))
            {
                return new HashSet<String>(dependees[s]);
            }
            
            return new HashSet<String>();

        }

        /// <summary>
        /// Given a string s it returns all the dependees assocaited with it. Or if it has none or it doesn't exist, it returns an empty list.
        /// </summary>
        /// <param name="s">A string that you wish to get all the dependees values of.</param>
        /// <returns>A IEnumerable obj that contains all the dependees of string s.</returns>
        public IEnumerable<string> GetDependees(string s)
        {
            // Check and see if s is a valid input string, if its not return a empty HashSet.
            if (!stringValidator(s, " "))
            {
                return new HashSet<String>();
            }

            // If dependents contains the key s, return a HashSet containing all its dependee values, or return an empty list.
            if (dependents.ContainsKey(s))
            {
                return new HashSet<String>(dependents[s]);
            }
            
            return new HashSet<String>();
        }


        /// <summary>
        /// <para>Adds the ordered pair (s,t), if it doesn't exist</para>
        /// 
        /// <para>This should be thought of as:</para>   
        /// 
        ///   s depends on t
        ///
        /// </summary>
        /// <param name="s">A string value you wish to add. s cannot be evaluated until t is</param>
        /// <param name="t">A string value you wish to add. t must be evaluated first.  S depends on T</param>
        public void AddDependency(string s, string t)
        {
            // Check and ensure that both input strings are valid, if not end the method.
            if (!stringValidator(s, t))
            {
                return;
            }

            // If the pair doesn't exist, increment the orderedPair counter.
            if (!(dependents.ContainsKey(t) && dependees.ContainsKey(s)))
            {
                orderedPairs++;
            }

            // If t is not in the dependents dictionary as a key add it in.
            if (!dependents.ContainsKey(t))
            {
                // Create a new HashSet to add Dependee values in, add the new s dependee value.
                HashSet<String> inputdependees = new HashSet<String>();
                inputdependees.Add(s);
                // Add the new dependent key value t with its new dependee HashSet containing s.
                dependents.Add(t, inputdependees);
            }
            else
            {
                // If t is already in dependents, just add the new dependee value to its HashSet
                dependents[t].Add(s);
            }

            // If t is not in the dependees dictionary as a key add it in.
            if (!dependees.ContainsKey(s))
            {
                // Create a new HashSet to add Dependent values in, add the new t dependent value.
                HashSet<String> inputdependents = new HashSet<String>();
                inputdependents.Add(t);
                // Add the new dependee key value s with its new dependent HashSet containing t.
                dependees.Add(s, inputdependents);

            }
            else
            {
                // If s is already in dependees, just add the new dependent value to its HashSet
                dependees[s].Add(t);
            }


        }


        /// <summary>
        /// Removes the ordered pair (s,t), if it exists
        /// </summary>
        /// <param name="s">A string value you wish to remove. </param>
        /// <param name="t">A string value you wish to remove. </param>
        public void RemoveDependency(string s, string t)
        {
            // Check and ensure that both input strings are valid, if not end the method.
            if (!stringValidator(s, t))
            {
                return;
            }

            // If the pair does exist. 
            if (dependents.ContainsKey(t) && dependents[t].Contains(s) && dependees.ContainsKey(s) && dependees[s].Contains(t))
            {
                // First decrement the orderedPairs value.
                orderedPairs--;
                // Remove the depencencies from both Dictionaries.
                dependents[t].Remove(s);
                dependees[s].Remove(t);

                // Check and see if the recent removal made either key value HashSet empty, delete the key value from the respective Dictionary.
                if (dependents[t].Count() == 0)
                {
                    dependents.Remove(t);
                }

                if (dependees[s].Count == 0)
                {
                    dependees.Remove(s);
                }
            }
        }


        /// <summary>
        /// Removes all existing ordered pairs of the form (s,r).  Then, for each
        /// t in newDependents, adds the ordered pair (s,t).
        /// </summary>
        /// <param name="s">The name of the string value you wish to replace the dependent values of.</param>
        /// <param name="newDependents">The IEnumerable obj containing the new string dependent values for the input string.</param>
        public void ReplaceDependents(string s, IEnumerable<string> newDependents)
        {
            // Check and ensure that both input strings are valid, if not end the method.
            if (!stringValidator(s, " "))
            {
                return;
            }

            // Get the old list of the dependents of s
            HashSet<String> dependentsOfSList = (HashSet<String>)GetDependents(s);

            // Iterate through all the old dependent values of s and remove them one at a time.
            foreach (String oldDependency in dependentsOfSList)
            {
                // Call the RemoveDependancy method to remove each string.
                RemoveDependency(s, oldDependency);
            }

            // Iterate through all the new dependent value of s and add them one at a time.
            foreach (String newDependency in newDependents)
            {
                // Call the  AddDependancy method to add each string.
                AddDependency(s, newDependency);
            }

        }


        /// <summary>
        /// Removes all existing ordered pairs of the form (r,s).  Then, for each 
        /// t in newDependees, adds the ordered pair (t,s).
        /// </summary>
        /// <param name="s">The name of the string value you wish to replace the dependee values of.</param>
        /// <param name="newDependees">The IEnumerable obj containing the new string dependee values for the input string.</param>
        public void ReplaceDependees(string s, IEnumerable<string> newDependees)
        {
            // Check and ensure that both input strings are valid, if not end the method.
            if (!stringValidator(s, " "))
            {
                return;
            }

            // Get the old list of the dependees of s
            HashSet<String> dependeesOfSList = (HashSet<String>)GetDependees(s);

            // Iterate through all the old dependee values of s and remove them one at a time.
            foreach (String oldDependees in dependeesOfSList)
            {
                // Call the RemoveDependancy method to remove each string.
                RemoveDependency(oldDependees, s);
            }

            // Iterate through all the new dependee value of s and add them one at a time.
            foreach (String newDependee in newDependees)
            {
                // Call the  AddDependancy method to add each string.
                AddDependency(newDependee, s);
            }

        }

        /// <summary>
        /// This serves as a helper method for the class to ensure that all input strings are not empty or null.
        /// </summary>
        /// <param name="s">The first string value to validate if its empty or null.</param>
        /// <param name="t">The second string value to validate if its empty or null.</param>
        /// <returns>A boolean value that indicates if either or both of the strings are empty or null.</returns>
        public Boolean stringValidator(string s, string t)
        {
            // Return false if either or both strings are empty or null.
            if (String.IsNullOrEmpty(s) || String.IsNullOrEmpty(t))
            {
                return false;
            }
            return true;
        }
    }




}
