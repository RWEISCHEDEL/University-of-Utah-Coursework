function [panorama] = processImageSet(fileName)

    % Get and parse the paths for each of the 11 datasets.
    datasetList = {'ucsb4', 'family_house', 'glacier4', 'yellowstone2', 'GrandCanyon1', 'yellowstone5', 'yellowstone4', 'west_campus1', 'redrock', 'intersection', 'GrandCanyon2'};
    
    if isnumeric(fileName)
        whichFile = fileName;
        filePath = 'Inputs';
    else
        
        if strcmp(fileName(end), '/')
            fileName = fileName(1 : end - 1);
        end
        
        [filePath, datasetName, ~] = fileparts(fileName);
        disp(['path ', filePath, ' dataset ', datasetName])
        whichFile = find(strcmp(datasetList, datasetName));
        
    end
    
    % Image Set Parameters
    size_bound = 400.0;
    displayFull360 = [0,0,0,0,0,0,0,0,0,0,0];
    unorderedList = [0,1,0,0,0,0,0,1,0,0,0];
    focus = [595,400,2000,1000,1000,1000,1000,1000,2000,2000,2000];
    
    full360Mode = displayFull360(whichFile);
    focusVal = focus(whichFile);

    disp(['Creating Panorama Image For ', datasetList{whichFile}]);
    
    set = imageSet(fullfile(filePath, datasetList{whichFile}));
    img = read(set, 1);
    size_1 = size(img, 1);
    
    if size_1 > size_bound
        img = imresize(img, size_bound / size_1);
    end
    
    images = zeros(size(img, 1), size(img, 2), size(img, 3), set.Count, 'like', img);
    t=cputime;
    
    for i = 1 : set.Count
        
        extractedImage = read(set, i);
        
        if size_1 > size_bound
            images(:,:,:,i) = imresize(extractedImage, size_bound / size_1);
        else
            images(:,:,:,i) = extractedImage;
        end
        
    end
    
    disp(['Resizing Image: ',int2str(cputime - t),' sec']);

    if unorderedList(whichFile)
        
        t = cputime;
        disp('Collecting Unordered Images: ');
        images = imorder(images);
        disp([int2str(cputime-t), ' sec']);
        
    end

    panorama = createPanorama(images, focusVal, full360Mode);
    
    imwrite(panorama, ['./Outputs/', datasetList{whichFile}, '.jpg']);
    
    if unorderedList(whichFile)
        imwrite(panorama, ['./Outputs/', datasetList{whichFile}, ' - unordered.jpg']);
    end