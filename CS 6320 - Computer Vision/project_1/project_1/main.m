% Robert Weischedel
% CS 6320 - Homework 1
% u0887905
clc;
clear all;
close all;

addpath(genpath('Functions'));
addpath(genpath('Inputs'));
addpath(genpath('Outputs'))

disp('All outputs will be displayed at the time that the program finishes');
disp('Problem 1')
CannyEdgeDetection();

disp('Problem 2')
SimpleSkySegmentation();

disp('Problem 3')
StereoMatching();