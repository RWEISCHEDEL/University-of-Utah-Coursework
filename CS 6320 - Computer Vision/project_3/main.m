clear all;
clc;

disp('Homework 3');
disp('By: Robert Weischedel');

addpath(genpath('Functions'));
addpath(genpath('Inputs'));
addpath(genpath('Outputs'));

disp('Run: Belief Propagation Program');
BP();

disp('Run: Stereo Matching Program');
lambda = [1 5 10 100];
disp('First Image')
for l = 1:1:size(lambda, 2)
    SM2('left1.png', 'right1.png', lambda(l));
end

disp('Second Image')
for l = 1:1:size(lambda, 2)
    SM2('left2.png', 'right2.png', lambda(l));
end