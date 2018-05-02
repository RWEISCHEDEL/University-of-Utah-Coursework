% Q3 - Image Based Location with Bag of Words

function BOW

dataBaseDescriptors = [];
queryDescriptors = [];
dataBaseImgOrder = [];
queryImageOrder = [];

files = dir('D:/Matlab Projects/project_2/Inputs/Assignment2_DATA/Assignment2_DATA/database/*.png');


% Build the Codebase of Descriptors
for file = files'
    
    filePath = strcat('Assignment2_DATA/Assignment2_DATA/database/', + file.name);
    dataBaseImgOrder = [dataBaseImgOrder, file.name];
    [image, descriptors] = sift(filePath);
    dataBaseDescriptors = [dataBaseDescriptors; descriptors];
    
end

% 
[idx,C,sumd,D] = kmeans(dataBaseDescriptors, 1000);

files = dir('D:/Matlab Projects/project_2/Inputs/Assignment2_DATA/Assignment2_DATA/query/*.png');


clusterQCounts = [];
for file = files'
    clusterCount = matrix(1, 1000);
    filePath = strcat('Assignment2_DATA/Assignment2_DATA/query/', + file.name);
    queryImageOrder = [queryBaseImgOrder, file.name];
    [image, descriptors] = sift(filePath);
    queryDescriptors = [queryDescriptors; descriptors];
    
    
    for row = 1:1:size(descriptors, 1)
        int largestCluster = 0;
        int largestDotProduct = 0;
        for c = 1:1:size(C, 1)
            
            value = dot(descriptors(row), C(c));
            
            if largestDotProduct < dot(descriptors(row), C(c))
                largestDotProduct = value;
                largestCluster = c;
            end
            
        end
        
        count = clusterCount(c);
        count = count + 1;
        clusterCount(c) = count;
        
    end
    
    clusterQCount = [clusterQCount; clusterCount];

end

files = dir('D:/Matlab Projects/project_2/Inputs/Assignment2_DATA/Assignment2_DATA/database/*.png');


clusterDCounts = [];
for file = files'
    clusterCount = matrix(1, 1000);
    filePath = strcat('Assignment2_DATA/Assignment2_DATA/database/', + file.name);
    queryImageOrder = [queryBaseImgOrder, file.name];
    [image, descriptors] = sift(filePath);
    queryDescriptors = [queryDescriptors; descriptors];
    
    
    for row = 1:1:size(descriptors, 1)
        int largestCluster = 0;
        int largestDotProduct = 0;
        for c = 1:1:size(C, 1)
            
            value = dot(descriptors(row), C(c));
            
            if largestDotProduct < dot(descriptors(row), C(c))
                largestDotProduct = value;
                largestCluster = c;
            end
            
        end
        
        count = clusterCount(c);
        count = count + 1;
        clusterCount(c) = count;
        
    end
    
    clusterDCount = [clusterCount; clusterCount];

end



