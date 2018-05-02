function [sortedImages] = imorder(images)

inlierRatioVal = 0.1;
epsilonVal = 1.5;
thresholdVal = 5;
confidenceVal = 0.999;

numberImages = size(images, 4);

T = zeros(3, 3, numberImages);
T(:, :, 1) = eye(3);

imageFeatures{numberImages} = {};
imageDistances{numberImages} = {};

for i = 1: numberImages
    
    [feature, distance] = SIFTImage(images(:, :, :, i), thresholdVal);
    imageFeatures{i} = feature;
    imageDistances{i} = distance;
    
end

isMatch = zeros(numberImages, numberImages);
transformVals{numberImages, numberImages} = {};

for i = 1 : numberImages
    for j = 1 : numberImages
        
        if j == i
            continue
        end
        
        [allMatches, ~] = findMatches(imageFeatures{i}, imageDistances{i}, imageFeatures{j}, imageDistances{j});
        [T, nInliersVal] = RANSAC(confidenceVal, inlierRatioVal, 1, allMatches, epsilonVal);
        
        if nInliersVal > 5.9 + 0.22 * length(allMatches)
            isMatch(i,j) = 1;
            transformVals{i,j} = T;
        end
        
    end
end

sequenceList = [];
sequenceList(1) = 1;

% forward matching
for i = 2:numberImages
    
    nextIndex = find(isMatch(sequenceList(i - 1), :) == 1);
    
    if size(nextIndex, 2) == 0
        break
    end
    
    haveFailed = true;
    
    for matchIndex = 1 : size(nextIndex, 2)
        
        if size(find(sequenceList == nextIndex(matchIndex)), 2) == 1
            continue
        end
        
        realIndex = matchIndex;
        haveFailed = false;
        break
        
    end
    
	if haveFailed == false
        sequenceList(i) = nextIndex(realIndex);
    else
        break
    end
    
end

% backward matching
for i = 2:numberImages
    
    nextIndex = find(isMatch(sequenceList(1), :) == 1);
    
    if size(nextIndex, 2) == 0
        break
    end
    
    haveFailed = true;
    
    for matchIndex = 1:size(nextIndex, 2)
        
        if size(find(sequenceList == nextIndex(matchIndex)), 2) == 1
            continue
        end
        
        realIndex = matchIndex;
        haveFailed = false;
        break
        
    end
    
    if haveFailed == false
        sequenceList = [nextIndex(realIndex), sequenceList];
    else
        break
    end
    
end

% reorder
disp(['Using ',int2str(length(sequenceList)), ' of ', int2str(length(images(1,1,1,:))), ' Unordered Images.']);
sortedImages = zeros(size(images,1), size(images,2), size(images,3), length(sequenceList), 'like', images);

for i = 1:length(sequenceList)
    sortedImages(:,:,:,i) = images(:, :, :, sequenceList(i));
end