// Written by Robert Weischedel and Jackson Murphy for CS 3500. Last updated November 17, 2015.

using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using AgCubio;
using System.Drawing;
using System.Collections.Generic;

namespace AgCubioUnitTests
{
    /// <summary>
    /// A class for testing the Cube and World classes in the AgCubio Model
    /// </summary>
    [TestClass]
    public class ModelUnitTests
    {
        /// <summary>
        /// Make sure constructor works and the Width property gets set properly
        /// </summary>
        [TestMethod]
        public void TestCubeConstructor1()
        {

            Cube cube = new Cube(813.0, 878.0, -2987746, 5318, 0, false, "Myname", 1029.1106844616961);

            Assert.IsTrue((int)cube.loc_x == 813);

            Assert.IsTrue((int)cube.loc_y == 878);

            Assert.IsTrue(cube.argb_color == -2987746);

            Assert.IsTrue(cube.uid == 5318);

            Assert.IsTrue(cube.team_id == 0);

            Assert.IsTrue(cube.food == false);

            Assert.IsTrue(cube.Name == "Myname");


            int cubeWidth = cube.Width;

        }

        /// <summary>
        /// Makes sure our Equals(Cube cube) method works properly.
        /// </summary>
        [TestMethod]
        public void TestCubeEquals()
        {

            Cube cube1 = new Cube(813.0, 878.0, -2987746, 5318, 0, false, "Myname", 1029.1106844616961);

            // UID is the same as cube2 even though other properties are different.
            Cube cube2 = new Cube(0.0, 0.0, 0, 5318, 0, false, "Myname", 0.0);

            Cube cube3 = null;

            // UID is different than cube1
            Cube cube4 = new Cube(813.0, 878.0, -2987746, 1, 0, false, "Myname", 1029.1106844616961);

            Assert.IsTrue(cube1.Equals(cube2));
            Assert.IsFalse(cube1.Equals(cube3));
            Assert.IsFalse(cube1.Equals(cube4));

        }



        /// <summary>
        /// Makes sure our Equals(Object cube) method works properly.
        /// </summary>
        [TestMethod]
        public void TestCubeObjectEquals()
        {

            Cube cube1 = new Cube(813.0, 878.0, -2987746, 5318, 0, false, "Myname", 1029.1106844616961);

            // UID is the same as cube2 even though other properties are different.
            Cube cube2 = new Cube(0.0, 0.0, 0, 5318, 0, false, "Myname", 0.0);

            Cube cube3 = null;

            // UID is different than cube1
            Cube cube4 = new Cube(813.0, 878.0, -2987746, 1, 0, false, "Myname", 1029.1106844616961);

            Assert.IsTrue(cube1.Equals((object)cube2));
            Assert.IsFalse(cube1.Equals((object)cube3));
            Assert.IsFalse(cube1.Equals((object)cube4));
            Assert.IsFalse(cube1.Equals("A string is not equal to a cube"));

        }


        /// <summary>
        /// Checks whether the World XML constructor works. If a bad filepath is passed,
        /// the constructor resorts to default values.
        /// </summary>
        [TestMethod]
        public void TestWorldXMLConstructor()
        {
            // This xml file sets the world's height to 1001
            World world = new World("C:\\Users\\jmurphy\\Source\\Repos\\CS3500_Projects\\AgCubio\\Resources\\XMLWorldParameters.xml");

            Assert.IsTrue(world.HEIGHT == 1001);

            // If we give a bad file, the world defaults to a height of 1100
            World world2 = new World("");

            Assert.AreEqual(1100, world2.HEIGHT);
        }


        /// <summary>
        /// Two cubes having the same UID should be treated as duplicates by the 
        /// AddOrUpdate method and therefore the second cube shouldn't be added to
        /// the world.
        /// </summary>
        [TestMethod]
        public void TestAddOrUpdateCubeForSameUID()
        {

            World world = new World("");

            Cube cube = new Cube(813.0, 878.0, -2987746, 5318, 0, false, "Myname", 1029.1106844616961);

            world.AddOrUpdateCube(cube);

            Cube cube2 = new Cube(500.0, 300.0, -2987746, 5318, 0, false, "Myname", 1029.1106844616961);

            world.AddOrUpdateCube(cube2);

            Assert.IsTrue(world.Cubes.Count == 1);


        }

        /// <summary>
        /// Adding a cube with mass 0 when it already exists in the world should
        /// remove the cube from the world.
        /// </summary>
        [TestMethod]
        public void TestAddOrUpdateCubeForAddingCubeWith0Mass()
        {

            World world = new World("");

            // Create cube with mass of 10
            Cube cube = new Cube(0, 0, -2987746, 1, 0, false, "Myname", 10.0);
            world.AddOrUpdateCube(cube);

            // Create another cube with same uid as first but whose mass is 0
            Cube cube2 = new Cube(0.0, 0.0, -2987746, 1, 0, false, "Myname", 0.0);

            world.AddOrUpdateCube(cube2);

            Assert.IsTrue(world.Cubes.Count == 0);


        }

        [TestMethod]
        public void TestCheckForMaxAndMinSpeeds()
        {
            // Top speed is 20, min speed is 5
            World world = new World("");

            double tooFast = world.TOP_SPEED + 1.0;
            double tooFastNeg = -world.TOP_SPEED - 1.0;
            double tooSlow = world.MIN_SPEED - 0.5;
            double tooSlowNeg = -world.MIN_SPEED + 0.5;

            world.CheckForTopAndMinSpeeds(ref tooFast, ref tooFastNeg);
            Assert.AreEqual(world.TOP_SPEED, tooFast);
            Assert.AreEqual(-world.TOP_SPEED, tooFastNeg);


            world.CheckForTopAndMinSpeeds(ref tooSlow, ref tooSlowNeg);
            Assert.AreEqual(world.MIN_SPEED, tooSlow);
            Assert.AreEqual(-world.MIN_SPEED, tooSlowNeg);


            tooFast = world.TOP_SPEED + 1.0;
            tooFastNeg = -world.TOP_SPEED - 1.0;
            tooSlow = world.MIN_SPEED - 0.5;
            tooSlowNeg = -world.MIN_SPEED + 0.5;

            world.CheckForTopAndMinSpeeds(ref tooFastNeg, ref tooFast);
            Assert.AreEqual(world.TOP_SPEED, tooFast);
            Assert.AreEqual(-world.TOP_SPEED, tooFastNeg);


            world.CheckForTopAndMinSpeeds(ref tooSlowNeg, ref tooSlow);
            Assert.AreEqual(world.MIN_SPEED, tooSlow);
            Assert.AreEqual(-world.MIN_SPEED, tooSlowNeg);

        }

        /// <summary>
        /// Tests whether AbsorbCubeCheck works.
        /// </summary>
        [TestMethod]
        public void TestAbsorbCubeCheck()
        {
            Cube bigCube = new Cube(10, 10, -2987746, 1, 1, false, "BigCube", 400);
            Cube smallCube = new Cube(10, 19, -2987746, 1, 1, false, "SmallCube", 100);
            Assert.IsTrue(World.WasEaten(bigCube, smallCube, 0.5));

            bigCube = new Cube(10, 10, -2987746, 1, 1, false, "BigCube", 400);
            smallCube = new Cube(10, 21, -2987746, 1, 1, false, "SmallCube", 100);
            Assert.IsFalse(World.WasEaten(bigCube, smallCube, 0.5));

            bigCube = new Cube(10, 10, -2987746, 1, 1, false, "BigCube", 400);
            smallCube = new Cube(19, 10, -2987746, 1, 1, false, "SmallCube", 100);
            Assert.IsTrue(World.WasEaten(bigCube, smallCube, 0.5));

            bigCube = new Cube(10, 10, -2987746, 1, 1, false, "BigCube", 400);
            smallCube = new Cube(21, 10, -2987746, 1, 1, false, "SmallCube", 100);
            Assert.IsFalse(World.WasEaten(bigCube, smallCube, 0.5));

            bigCube = new Cube(30, 30, -2987746, 1, 1, false, "BigCube", 400);
            smallCube = new Cube(19, 30, -2987746, 1, 1, false, "SmallCube", 100);
            Assert.IsFalse(World.WasEaten(bigCube, smallCube, 0.5));

            bigCube = new Cube(30, 30, -2987746, 1, 1, false, "BigCube", 400);
            smallCube = new Cube(21, 30, -2987746, 1, 1, false, "SmallCube", 100);
            Assert.IsTrue(World.WasEaten(bigCube, smallCube, 0.5));


        }

        /// <summary>
        /// Checks whethe the GiveCubeAJumpTrajectory works.
        /// </summary>
        [TestMethod]
        public void TestGiveCubeAJumpTrajectory()
        {
            World world = new World("");
            Cube c1 = new Cube(30, 30, -2987746, 1, 1, false, "BigCube", 400);

            world.GiveCubeAJumpTrajectory(c1, 350, 350, 100);

            Assert.IsTrue(c1.HasMomentum == true && c1.FuturePositionsAfterSplit != null);



        }

        /// <summary>
        /// Tests whether split works
        /// </summary>
        [TestMethod]
        public void TestSplitMethod()
        {
            World world = new World("");

            // player c1 has sufficient mass to split
            Cube c1 = new Cube(30, 30, -2987746, 1, 1, false, "BigCube", 1000);

            world.Teams.Add(1, new List<Cube>() { c1 });

            world.Split(350, 350, 1);

            Assert.IsTrue(world.Teams[1].Count == 2);
            Assert.IsTrue(c1.CanMerge == false);
            Assert.IsTrue(c1.HasMomentum == false);
            Assert.IsTrue(world.Teams[1][0].HasMomentum == false);

            Cube tooSmallToSplit = new Cube(30, 30, -2987746, 1, 2, false, "BigCube", world.MIN_SPLIT_MASS - 1);
            world.Teams.Add(2, new List<Cube>() { tooSmallToSplit });

            world.Split(350, 350, 2);

            Assert.IsTrue(world.Teams[2].Count == 1);
            Assert.IsTrue(tooSmallToSplit.CanMerge == true);
            Assert.IsTrue(tooSmallToSplit.HasMomentum == false);



        }

        /// <summary>
        /// Checks whether MovePlayer() works properly
        /// </summary>
        [TestMethod]
        public void TestMovePlayer()
        {
            World world = new World("");

            world.CreateFood(5500, true);

            world.CreateVirus(5000, true);

            Cube c1 = new Cube(30, 30, -2987746, 1, 1, false, "BigCube", 1000);
            Cube c2 = new Cube(60, 60, -2987746, 2, 1, false, "BigCube", 1000);
            Cube c3 = new Cube(500, 500, -2987746, 3, 2, false, "BigCube", 1000);
            Cube c4 = new Cube(500, 505, -2987746, 4, 1, false, "BigCube", 1000);
            Cube c5 = new Cube(570, 570, -2987746, 5, 2, false, "BigCube", 900);
            Cube c6 = new Cube(550, 550, -2987746, 6, 1, false, "BigCube", 1000);

            world.AddOrUpdateCube(c1);
            world.AddOrUpdateCube(c2);
            world.AddOrUpdateCube(c3);
            world.AddOrUpdateCube(c4);
            world.AddOrUpdateCube(c5);
            world.AddOrUpdateCube(c6);


            c2.HasMomentum = true;
            c2.FuturePositionsAfterSplit = new List<System.Windows.Point>() { new System.Windows.Point(20, 20) };

            world.Teams.Add(1, new List<Cube>() { c1, c2, c4, c6 });

            List<Cube> team = new List<Cube>() { c1, c2, c4, c6 };

            // Move player down and to the right
            world.MovePlayer(50, 50, team);

            Assert.IsTrue(c1.loc_x > 30 && c1.loc_y > 30);
            Assert.IsTrue(c2.loc_x == 20 && c2.loc_y == 20);





        }

        /// <summary>
        /// Checks whether ExplodePlayerOnVirus() works
        /// </summary>
        [TestMethod]
        public void TestExplodePlayerOnVirus()
        {
            World world = new World("");

            Cube c1 = new Cube(30, 30, -2987746, 1, 1, false, "BigCube", 1000);

            world.Teams.Add(1, new List<Cube>() { c1 });

            world.ExplodePlayerOnVirus(c1);

            Assert.IsTrue(c1.CanMerge == false && world.Teams[1].Count == 4);
        }

        /// <summary>
        /// Tests whether create food works properly
        /// </summary>
        [TestMethod]
        public void TestCreateFood()
        {
            World world = new World("");

            // create an initial 50 foods
            world.CreateFood(50, true);

            // add 10 more foods
            world.CreateFood(10, false);

            Assert.IsTrue(world.Cubes.Count == 60);

        }

        /// <summary>
        /// Checks whether players spawn within the world.
        /// </summary>
        [TestMethod]
        public void TestGetPlayerSpawnLoc()
        {
            World world = new World("");

            Cube c1 = new Cube(300, 300, world.SetCubeColor(), 1, world.GetNewTeamID(), false, "BigCube", 1000);
            Cube c2 = new Cube(600, 600, -2987746, 2, 1, false, "BigCube", 1000);

            world.AddOrUpdateCube(c1);
            world.AddOrUpdateCube(c2);

            for (int i = 0; i < 100; i++)
            {
                System.Windows.Point point = world.GetPlayerSpawnLoc();
                Assert.IsTrue(point.X < 1000 && point.X > 0 && point.Y < 1000 && point.Y > 0);
            }
        }


        /// <summary>
        /// Checks whether we are properly removing and adding players to a team
        /// </summary>
        [TestMethod]
        public void TestTeamDirectoryUpdating()
        {
            Dictionary<int, List<Cube>> Teams = new Dictionary<int, List<Cube>>();

            Cube cube = new Cube(30, 30, -2987746, 1, 1, false, "BigCube", 400);


            Teams.Add(1, new List<Cube>() { cube });

            Cube cube2 = new Cube(30, 30, -2987746, 2, 1, false, "BigCube", 400);
            Cube cube3 = new Cube(30, 30, -2987746, 3, 1, false, "BigCube", 400);
            Cube cube4 = new Cube(30, 30, -2987746, 4, 1, false, "BigCube", 400);

            List<Cube> cubes = new List<Cube>() { cube, cube2, cube3, cube4 };

            Teams[cube.team_id].Remove(cube);

            Teams[cube.team_id].AddRange(cubes);

            Assert.IsTrue(Teams[cube.team_id].Count == 4);






        }

        [TestMethod]
        public void TestDuplicatePlayerCubesInCubesToUpdate()
        {
            World world = new World("");

            Cube cube1 = new Cube(30, 30, -2987746, 2, 1, false, "BigCube", 400);
            Cube cube2 = new Cube(30, 30, -2987746, 2, 1, false, "BigCube", 400);
            Cube cube3 = new Cube(30, 30, -2987746, 4, 1, false, "BigCube", 400);

            world.CubesChangedSinceLastUpdate.Add(cube1);
            world.CubesChangedSinceLastUpdate.Add(cube2);

            Assert.AreEqual(1, world.CubesChangedSinceLastUpdate.Count);
            
        }
    }


}
