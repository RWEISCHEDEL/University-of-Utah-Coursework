% Q3 - Image Based Location with Vocab Tree

function VT

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



for file = files'
    clusterCount = matrix(1, 1000);
    filePath = strcat('Assignment2_DATA/Assignment2_DATA/query/', + file.name);
    queryImageOrder = [queryBaseImgOrder, file.name];
    [image, descriptors] = sift(filePath);
    queryDescriptors = [queryDescriptors; descriptors];

end
