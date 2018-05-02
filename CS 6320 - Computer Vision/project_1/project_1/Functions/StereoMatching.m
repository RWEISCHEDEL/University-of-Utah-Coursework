left1 = imread('left1.png');
left2 = imread('left2.png');
left3 = imread('left3.bmp');

right1 = imread('right1.png');
right2 = imread('right2.png');
right3 = imread('right3.bmp');

outputImage1 = stereoMatch(left1, right1);
outputImage2 = stereoMatch(left2, right2);
outputImage3 = stereoMatch(left3, right3);

imwrite(outputImage1, 'Outputs/stereomatching1.png', 'png');
imwrite(outputImage2, 'Outputs/stereomatching2.png', 'png');
imwrite(outputImage3, 'Outputs/stereomatching3.png', 'png');

colormap(gray);

% image(outputImage1);
% figure
% colormap(gray);
% image(outputImage2);
% figure
% colormap(gray);
% image(outputImage3);
figure(3);  
subplot(3,3,1); imagesc(left1);
subplot(3,3,2); imagesc(right1);
subplot(3,3,3); imagesc(outputImage1);
subplot(3,3,4); imagesc(left2);
subplot(3,3,5); imagesc(right2);
subplot(3,3,6); imagesc(outputImage2);
subplot(3,3,7); imagesc(left3);
subplot(3,3,8); imagesc(right3);
subplot(3,3,9); imagesc(outputImage3);


function outputImage = stereoMatch(left, right)

DISPARITY_RANGE = 50;
WIN_SIZE = 5;
EXTEND = (WIN_SIZE - 1) / 2;

Nx = size(left, 1);
Ny = size(left, 2);

ileft = double(rgb2gray(left));
iright = double(rgb2gray(right));
colormap(gray);

DISPARITY = uint8(zeros(Nx, Ny, 3));

for y = 1:1:Nx
    for x = 1:1:Ny
        
        bestDisparity = 0;
        bestNCC = 0; % Lowest NCC Score
        
        for myDisp = 1:1:DISPARITY_RANGE
            
            if(y - EXTEND >= 1 && y + EXTEND <= Nx && x - EXTEND >= 1 && x + EXTEND <= Ny && x - myDisp - EXTEND >= 1 && x - myDisp + EXTEND <= Ny)
                
                Patch1 = ileft(y - EXTEND:y + EXTEND, x - EXTEND:x + EXTEND);
                Patch2 = iright(y - EXTEND:y + EXTEND, x - myDisp - EXTEND:x - myDisp + EXTEND);
                currNCC = NCC(Patch1, Patch2);
                
                if(currNCC > bestNCC)
                    bestNCC = currNCC;
                    bestDisparity = myDisp;
                end
            end
            
        end
        %disp(bestDisparity)
        DISPARITY(y,x, 1) = bestDisparity * 5;
        DISPARITY(y,x, 2) = bestDisparity * 5;
        DISPARITY(y,x, 3) = bestDisparity * 5;
        
    end
    
end

% Not needed...
outputImage = DISPARITY;

end
