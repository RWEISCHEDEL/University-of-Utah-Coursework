function [warpedImage] = warp(maskVal, focusVal)

    warpedImage = zeros(size(maskVal), 'like', maskVal);
    
    if length(size(maskVal)) == 2
        layerCount = 1;
    else
        layerCount = size(maskVal,3);
    end
    
    for layerIndex = 1 : layerCount
        
        xCenter = size(maskVal,2) / 2;
        yCenter = size(maskVal,1) / 2;
        
        x = (1:size(maskVal,2)) - xCenter;
        y = (1:size(maskVal,1)) - yCenter;
        
        [meshX, meshY] = meshgrid(x, y);
        
        meshY = focusVal * meshY./sqrt(meshX.^2 + double(focusVal)^2) + yCenter;
        meshX = focusVal * atan(meshX / double(focusVal)) + xCenter;
        meshX = floor(meshX + 0.5);
        meshY = floor(meshY + 0.5);

        index = sub2ind([size(maskVal,1),size(maskVal,2)], meshY, meshX);
      
        cylinder=zeros(size(maskVal,1),size(maskVal,2),'like',maskVal);
        cylinder(index) = maskVal(:, :, layerIndex);

        warpedImage(:, :, layerIndex) = cylinder;
    end
end