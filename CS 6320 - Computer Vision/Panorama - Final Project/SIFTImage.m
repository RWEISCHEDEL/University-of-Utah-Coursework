function [features, data] = SIFTImage(image, edgeThresholdVal)

if (size(image, 3) == 3)
    siftImage = single(rgb2gray(image));
else
    siftImage = single(image);
end

[features, data] = vl_sift(siftImage, 'EdgeThresh', edgeThresholdVal);

end