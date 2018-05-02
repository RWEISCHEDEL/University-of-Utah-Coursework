function transMat = RT2Trans(rotMat, transVec)

transMat = eye(4);
transMat(1:3, 1:3) = rotMat(1:3, 1:3);
transMat(1:3, 4) = transVec;


