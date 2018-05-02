function [T, finalInliers] = RANSAC(confidenceVal, inlinerRatioVal, numberPairs, dataVal, epsilonVal)

iterations = ceil(log(1 - confidenceVal) / log(1 - inlinerRatioVal ^ numberPairs));

numberPoints = size(dataVal, 1);
finalInliers = 0;

A = zeros(2 * numberPairs, 2);
B = zeros(2 * numberPairs, 1);

for i = 1 : numberPairs
    
    A(2 * i - 1, 1) = 1;
    A(2 * i, 2) = 1;
    
end

for i = 1 : iterations
    
    sampleIndicies = randperm(numberPoints, numberPairs);
    
    sampleVal = dataVal(sampleIndicies, :, :);
    
    firstPair = sampleVal(:, :, 1);
    secondPair = sampleVal(:, :, 2);

    for j = 1 : numberPairs
        
        B(2 * j - 1) = firstPair(j, 1) - secondPair(j, 1);
        B(2 * j) = firstPair(j, 2) - secondPair(j, 2);
        
    end
    
    t = A \ B;
    T = [1 0 t(1); 0 1 t(2); 0 0 1];
    
    pPrime = T * dataVal(:,:,2)';
    errorVal = dataVal(:, :, 1)' - pPrime;
    SE = errorVal .^ 2;
    finalSE = sum(SE);
    
    numInliers = sum(finalSE < epsilonVal);
    
    if numInliers > finalInliers
        bestSet = find(finalSE < epsilonVal);
        finalInliers = numInliers;
    end
end


firstPair = dataVal(bestSet, :, 1);
secondPair = dataVal(bestSet, :, 2);

for j = 1 : numberPairs
    
    B(2 * j - 1) = firstPair(j, 1) - secondPair(j, 1);
    B(2 * j) = firstPair(j, 2) - secondPair(j, 2);
    
end

t = A \ B;
T = [1 0 t(1); 0 1 t(2); 0 0 1];

end