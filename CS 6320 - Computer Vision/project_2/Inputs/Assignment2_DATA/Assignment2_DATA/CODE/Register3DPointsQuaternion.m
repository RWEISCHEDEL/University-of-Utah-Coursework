% compute transformation from pointsA and poitnsB so that
% pointsB = R * pointsA + t

function finalTrans = Register3DPointsQuaternion(pointsA, pointsB)
% pointsA, pointsB - 3 x n matrices.
% clear all; close all; clc;
% 
% pointsA = [5 6 8; 10 2 3; 18 9 10]';
% 
% trueRotMat = RPY2Rot(10, 15, 30);
% trueTransVec = [10 7 33]';
% trueTrans = RT2Trans(trueRotMat, trueTransVec);
% truePose = Trans2Pose(trueTrans)'
% 
% pointsB = trueTrans * [pointsA; ones(1, size(pointsA, 2))];
% pointsB = pointsB(1:3,:) ./ repmat(pointsB(4,:), 3, 1);

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

numPoints = size(pointsA, 2);

% comptue centroid
centroidA = mean(pointsA, 2);
centroidB = mean(pointsB, 2);

% find rotation
pA = pointsA - repmat(centroidA, 1, numPoints);
pB = pointsB - repmat(centroidB, 1, numPoints);

M = zeros(3, 3);
for i=1:numPoints
    M = M + pA(:,i) * pB(:,i)';
end

N = [
    M(1,1)+M(2,2)+M(3,3), M(2,3)-M(3,2), M(3,1)-M(1,3), M(1,2)-M(2,1);
    M(2,3)-M(3,2), M(1,1)-M(2,2)-M(3,3), M(1,2)+M(2,1), M(3,1)+M(1,3);
    M(3,1)-M(1,3), M(1,2)+M(2,1), -M(1,1)+M(2,2)-M(3,3), M(2,3)+M(3,2);
    M(1,2)-M(2,1), M(3,1)+M(1,3), M(2,3)+M(3,2), -M(1,1)-M(2,2)+M(3,3);
    ];

[V, D] = eig(N);
DVec = diag(D);
% sortedDVec = sort(DVec);
maxIdx = find(DVec == max(DVec));

qmin = V(:, maxIdx);
R = Quaternion2R(qmin);

% find translation given rotation
rotPointsA = R * pointsA;
rotCentroidA = mean(rotPointsA, 2);

t = centroidB - rotCentroidA;

finalTrans = RT2Trans(R, t);





