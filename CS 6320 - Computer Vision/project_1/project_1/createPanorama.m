function [panoramaImage] = createPanorama(imageSet, focus, full360Image)

if full360Image
    imageSet(:, :, :, end + 1) = imageSet(:, :, :, 1);
end

numberImages = size(imageSet, 4);

cylindricalImages = zeros(size(imageSet), 'like', imageSet);

t = cputime;

for i = 1 : numberImages
    cylindricalImages(:, :, :, i) = warp(imageSet(:, :, :, i), focus);
end

disp(['Warping Image: ',int2str(cputime - t),' sec']);
t = cputime;

transformations = calculateTransformation(cylindricalImages);

disp(['Running SIFT & RANSAC: ',int2str(cputime-t),' sec']);
t = cputime;

finalTransformations = zeros(size(transformations));
finalTransformations(:, :, 1) = transformations(:, :, 1);

for i = 2 : numberImages
    finalTransformations(:, :, i) = finalTransformations(:, :, i - 1) * transformations(:, :, i);
end


heightVal = size(cylindricalImages, 1);
widthVal = size(cylindricalImages, 2);

if full360Image
    
    panoramaWidth = abs(round(finalTransformations(2, 3, end))) + widthVal;
    slope = finalTransformations(1, 3, end) / finalTransformations(2, 3, end);
    
    panoramaHeight = heightVal;
    
    if finalTransformations(2, 3, end) < 0
        finalTransformations(2, 3, :) = finalTransformations(2, 3, :) - finalTransformations(2, 3, end);
        finalTransformations(1, 3, :) = finalTransformations(1, 3, :) - finalTransformations(1, 3, end);
    end
    
    drift = [1 - slope slope; 0 1 0; 0 0 1];
    
    for i = 1 : numberImages
        finalTransformations(:, :, i) = drift * finalTransformations(:, :, i);
    end
    
else
    
    minX = 1;
    minY = 1;
    maxX = widthVal;
    maxY = heightVal;
    
    for i = 2 : numberImages
        minX = min(minX, finalTransformations(2,3,i));
        minY = min(minY, finalTransformations(1,3,i));
        maxY = max(maxY, finalTransformations(1,3,i) + heightVal);
        maxX = max(maxX, finalTransformations(2,3,i) + widthVal);
    end
    
    panoramaHeight = ceil(maxY) - floor(minY) + 1;
    panoramaWidth = ceil(maxX) - floor(minX) + 1;
    
    finalTransformations(2, 3, :) = finalTransformations(2, 3, :) - floor(minX);
    finalTransformations(1, 3, :) = finalTransformations(1, 3, :) - floor(minY);
end

disp(['Perform End-End Alignment: ',int2str(cputime-t),' sec']);
t=cputime;

panoramaImage = merge(cylindricalImages, finalTransformations, panoramaHeight, panoramaWidth, focus);

disp(['Alpha Merging: ',int2str(cputime-t),' sec']);

end

