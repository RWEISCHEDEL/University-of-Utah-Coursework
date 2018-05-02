function [mergedImage] = merge(images, transforms, newHeight, newWidth, focus)

images = im2double(images);
height = size(images, 1);
width = size(images, 2);
numberChannels = size(images, 3);
numberImages = size(images, 4);

mask = ones(height, width);
mask = warp(mask, focus);
mask = imcomplement(mask);
mask = bwdist(mask, 'euclidean');

mask = mask./max(max(mask));

tempMask = ones([height, width, numberChannels], 'like', images);

for i = 1:numberChannels
    tempMask(:,:,i) = mask;
end

mask = tempMask;

minHeight = 0;
minWidth = 0;
maxHeight = 0;
maxWidth = 0;

for i=1:numberImages
    
    pPrime = transforms(:,:,i)*[1;1;1];
    pPrime = pPrime./pPrime(3);
    baseHeight = floor(pPrime(1));
    baseWidth = floor(pPrime(2));
    
    if baseHeight > maxHeight
        maxHeight = baseHeight;
    end
    
    if baseHeight < minHeight
        minHeight = baseHeight;
    end
    
    if baseWidth > maxWidth
        maxWidth = baseWidth;
    end
    
    if baseWidth < minWidth
        minWidth = baseWidth;
    end
    
end

mergedImage = zeros([newHeight + 20, newWidth + 20, numberChannels], 'like', images);
denominatorVal = zeros([newHeight + 20, newWidth + 20, numberChannels], 'like', images);

for i=1:numberImages
    
    pPrime = transforms(:,:,i) * [minHeight + 10; minWidth + 10; 1];
    pPrime = pPrime./pPrime(3);
    
    baseHeight = floor(pPrime(1));
    baseWidth = floor(pPrime(2));
    
    if baseHeight == 0
        baseHeight = 1;
    end
    
    if baseWidth == 0
        baseWidth = 1;
    end

    mergedImage(baseHeight:baseHeight + height - 1, baseWidth:baseWidth + width - 1,:) = mergedImage(baseHeight:baseHeight + height - 1, baseWidth:baseWidth + width - 1, :) + images(:, :, :, i).*mask;
    denominatorVal(baseHeight:baseHeight + height - 1, baseWidth:baseWidth + width - 1, :) = denominatorVal(baseHeight:baseHeight + height - 1, baseWidth:baseWidth + width - 1, :) + mask;
end

mergedImage = mergedImage./denominatorVal;

end
