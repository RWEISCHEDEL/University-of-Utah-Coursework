function [finalMatches, matchScores] = findMatches(features1, data1, features2, data2)

[matches, matchScores] = vl_ubcmatch(data1, data2);

numberMatches = size(matches, 2);

pairs = nan(numberMatches, 3, 2);

pairs(:,:,1) = [features1(2,matches(1,:)); features1(1,matches(1,:)); ones(1,numberMatches)]';
pairs(:,:,2) = [features2(2,matches(2,:)); features2(1,matches(2,:)); ones(1,numberMatches)]';

finalMatches = pairs;

end
