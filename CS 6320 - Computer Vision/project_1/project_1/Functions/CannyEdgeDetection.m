imageMatrix1 = imread('lineDetect1.bmp');
imageMatrix2 = imread('lineDetect2.bmp');
imageMatrix3 = imread('lineDetect3.bmp');

outputImage1 = edgeDetection(imageMatrix1);
outputImage2 = edgeDetection(imageMatrix1);
outputImage3 = edgeDetection(imageMatrix1);

imwrite(outputImage1, 'Outputs/cannyedgedetection1.png', 'png');
imwrite(outputImage2, 'Outputs/cannyedgedetection2.png', 'png');
imwrite(outputImage3, 'Outputs/cannyedgedetection3.png', 'png');

figure(1);  
subplot(3,2,1); imagesc(imageMatrix1);
subplot(3,2,2); imagesc(outputImage1);
subplot(3,2,3); imagesc(imageMatrix2);
subplot(3,2,4); imagesc(outputImage2);
subplot(3,2,5); imagesc(imageMatrix3);
subplot(3,2,6); imagesc(outputImage3);

function outputImage = edgeDetection(imageMatrix)

LINE_SET = {};
EDGE_SET = {};
ITERS = 0;
TOTAL_NO_ITERS = 10000;
MAX_PAIR_DISTANCE = 100;
MIN_POINTLINE_DISTANCE = 2;
MIN_LINE_PIXEL_NUM = 50;

cannyEdges = edge(rgb2gray(imageMatrix),'Canny', 0.1);

sizeX = size(imageMatrix,1);
sizeY = size(imageMatrix,2);

for i = 1:1:sizeX
    for j = 1:1:sizeY
        
        if(cannyEdges(i,j) == 1)
            EDGE_SET{end + 1} = [i j];
        end
        
    end
end

while(ITERS ~= TOTAL_NO_ITERS)
    
    ITERS = ITERS + 1;
    
    %disp(ITERS)
    
    edgeSize = size(EDGE_SET);
    
    randP = randi([1 edgeSize(2)]);
    
    pPoint = EDGE_SET(randP);
    
    pPointArray = pPoint{1,1};
    px = pPointArray(1);
    py = pPointArray(2);
    
    dist = MAX_PAIR_DISTANCE + 1;
    randQ = 0;
    
    while(dist > MAX_PAIR_DISTANCE)
        randQ = randi([1 edgeSize(2)]);
        qPoint = EDGE_SET(randQ);
        qPointArray = qPoint{1,1};
        cqx = qPointArray(1);
        cqy = qPointArray(2);
        dist = pdist([px, py; cqx, cqy], 'euclidean');
    end
    
    qPoint = EDGE_SET(randQ);
    qPointArray = qPoint{1,1};
    
    INPUT_SET = {};
    
    i = 1;
    edgeS = edgeSize(2);
    
    while(i < edgeS)
        
        point = EDGE_SET(i);
        pointArray = point{1,1};

        x1 = pPointArray(1);
        y1 = pPointArray(2);
        x2 = qPointArray(1);
        y2 = qPointArray(2);
        x0 = pointArray(1);
        y0 = pointArray(2);
        
        numerator = abs((y2 - y1)*x0 - (x2 - x1)*y0 + (x2*y1) - (y2*x1));
        denominator = sqrt((y2 - y1)^2 + (x2 - x1)^2);
        
        dist = numerator / denominator;
        
        if(dist <= MIN_POINTLINE_DISTANCE)
            INPUT_SET{end + 1} = pointArray;
            EDGE_SET(i) = [];
        end
        
        edgeS = edgeS - 1;
        i = i + 1;
        
    end
    
    inputSize = size(INPUT_SET);
    
    if(inputSize(2) >= MIN_LINE_PIXEL_NUM)
        
        LINE_SET{end + 1} = INPUT_SET;
        
    end
    
    
end

lineSize = size(LINE_SET);
newImage = uint8(zeros(sizeX, sizeY,3));

for(w = 1:1:lineSize(2))
    
    randR = randi([50 200]);
    randG = randi([50 200]);
    randB = randi([50 200]);
    
    currLine = LINE_SET(w);
    
    currentL = currLine{1,1};
    
    currLineSize = size(currentL);
    
    for(q = 1:1:currLineSize(2))
        pPoint = currLine(1);
        pointArray = pPoint{1,1};
        finalArray = pointArray(q);
        points = finalArray{1, 1};
        x = points(1);
        y = points(2);
        newImage(x, y, 1) = randR;
        newImage(x, y, 2) = randG;
        newImage(x, y, 3) = randB;
    end
    
end

outputImage = newImage;

end






