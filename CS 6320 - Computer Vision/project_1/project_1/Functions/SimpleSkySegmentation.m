imageMatrix1 = imread('detectSky1.bmp');
imageMatrix2 = imread('detectSky2.bmp');
imageMatrix3 = imread('detectSky3.bmp');

outputImage1 = segmentation(imageMatrix1);
outputImage2 = segmentation(imageMatrix2);
outputImage3 = segmentation(imageMatrix3);

imwrite(outputImage1, 'Outputs/simpleskydetection1.png', 'png');
imwrite(outputImage2, 'Outputs/simpleskydetection2.png', 'png');
imwrite(outputImage3, 'Outputs/simpleskydetection3.png', 'png');

figure(2);  
subplot(3,2,1); imagesc(imageMatrix1);
subplot(3,2,2); imagesc(outputImage1);
subplot(3,2,3); imagesc(imageMatrix2);
subplot(3,2,4); imagesc(outputImage2);
subplot(3,2,5); imagesc(imageMatrix3);
subplot(3,2,6); imagesc(outputImage3);

function outputImage = segmentation(imageMatrix)
R_MIN = 0;
R_MAX = 100;
G_MIN = 1;
G_MAX = 150;
B_MIN = 100;
B_MAX = 255;
sizeX = size(imageMatrix,1);
sizeY = size(imageMatrix,2);
outputImage = zeros(sizeX, sizeY);
for i = 1:1:sizeX
    for j = 1:1:sizeY
        redValue = imageMatrix(i,j,1);
        greenValue = imageMatrix(i,j,2);
        blueValue = imageMatrix(i,j,3);
        
        isSky = true;
        
        if(redValue < R_MIN || redValue > R_MAX)
            isSky = false;
        end
        
        if(greenValue < G_MIN || greenValue > G_MAX)
            isSky = false;
        end
        
        if(blueValue < B_MIN || blueValue > B_MAX)
            isSky = false;
        end
        
        if(isSky == true)
            outputImage(i,j,1) = 255;
            outputImage(i,j,2) = 255;
            outputImage(i,j,3) = 255;
        else
            outputImage(i,j,1) = 0;
            outputImage(i,j,2) = 0;
            outputImage(i,j,3) = 0;
        end
        
    end
end
end