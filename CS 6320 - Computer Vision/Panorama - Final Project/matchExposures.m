function [matchedImage] = matchExposures(images, transforms, performLoop)

numberImages = size(images, 4);

gammaList = ones(numberImages, 1);

for i = 2 : numberImages
    gammaList(i) = matchImagePair(images(:, :, :, i - 1), images(:, :, :, i), transforms(:, :, i));
end

if performLoop
    
    logGammaList = log(gammaList);
    logGammaList(1) = [];
    
    A = eye(nImgs - 2);
    A = [A; -ones(1, numberImages - 2)];
    
    updatedLogGammaList = A \ logGammaList;
    updatedLogGammaList = [0; updatedLogGammaList];
    finalGammas = exp(updatedLogGammaList);
    
    accGammaList = ones(nImgs, 1);
    
    for i = 2 : numberImages - 1
        accGammaList(i) = accGammaList(i - 1) * finalGammas(i);
    end
    
else
    
    accGammaList = ones(numberImages, 1);
    
    for i = 2 : numberImages
        accGammaList(i) = accGammaList(i - 1) * gammaList(i);
    end
    
end


matchedImage = zeros(size(images), 'uint8');

for i = 1 : numberImages
    matchedImage(:, :, :, i) = gammaCorrection(images(:, :, :, i), accGammaList(i));
end

end

%% Match pairs of images
function [gammaVal] = matchImagePair(image1, image2, transformVal)

numberIterations = 1000;
alphaVal = 1;
sampleRatioVal = 0.01;
outlierThresholdVal = 1.0;

height = size(image1, 1);
width = size(image1, 2);

labImage1 = rgb2lab(image1);
labImage2 = rgb2lab(image2);

k = 1;
numberPixels = numel(image1);
numberSamples = round(numberPixels * sampleRatioVal);
samples = zeros(numberSamples, 2);

while true
    
    pixel2 = [randi([1 height]); randi([1 width]); 1];
    pixel1 = transformVal * pixel2;
    pixel1 = pixel1 ./ pixel1(3);
    
    if pixel1(1) >= 1 && pixel1(1) < height && pixel1(2) >= 1 && pixel1(2) < width
        
        i = floor(pixel1(2));
        a = pixel1(2) - i;
        j = floor(pixel1(1));
        b = pixel1(1) - j;
        
        sample1 = (1 - a) * (1 - b) * labImage1(j, i, 1) + a * (1 - b) * labImage1(j, i + 1, 1) + a * b * labImage1(j + 1, i + 1, 1) + (1 - a) * b * labImage1(j + 1, i, 1);
        
        sample2 = labImage2(pixel2(1), pixel2(2), 1);
        
        if sample1 > outlierThresholdVal && sample2 > outlierThresholdVal
            
            samples(k, 1) = sample1 / 100;
            samples(k, 2) = sample2 / 100;
            k = k + 1;
            
            if k > numberSamples
                break;
            end
            
        end
    end
end


gammaVal = 1;

for i = 1 : numberIterations
    gammaVal = gammaVal - alphaVal * sum((samples(:, 2) .^ gammaVal - samples(:, 1)) .* log(samples(:, 2)) .* (samples(:, 2) .^ gammaVal)) / numberSamples;
end

end

%% Perform Gamma Correction
function [gammaImage] = gammaCorrection(image, gammaVal)

labImage = rgb2lab(image);
labImage(:, :, 1) = (labImage(:, :, 1) / 100) .^ gammaVal * 100;
gammaImage = lab2rgb(labImage, 'OutputType', 'uint8');

end
