function [T] = calculateTransformation(images)

inlierRatioVal = 0.3;
epsilonVal = 1.5;
thresholdVal = 10;
confidenceVal = 0.99;

numberImages = size(images, 4);

T = zeros(3, 3, numberImages);
T(:, :, 1) = eye(3);

[features2, data2] = SIFTImage(images(:, :, :, 1), thresholdVal);

for i = 2 : numberImages
    
    features1 = features2;
    data1 = data2;
    [features2, data2] = SIFTImage(images(:, :, :, i), thresholdVal);
    [matches, ~] = findMatches(features1, data1, features2, data2);
    [T(:, :, i),~] = RANSAC(confidenceVal, inlierRatioVal, 1, matches, epsilonVal);
    
end
end

