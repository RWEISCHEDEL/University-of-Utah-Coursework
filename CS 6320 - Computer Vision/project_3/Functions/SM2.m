function SM2(leftImg, rightImg, lambda)

% Read Images and Set to Grayscale
left = imread(leftImg);
right = imread(rightImg);

% left = double(rgb2gray(left));
% right = double(rgb2gray(right));
left = rgb2gray(left);
right = rgb2gray(right);

% Pairwise Factor Node Dependencies
pwFactorNodes = cell(1, ((size(left,2) - 1) * size(left, 1)) + ((size(left,1) - 1) * size(left, 2)));
pwFactorNodesStrings = cell(1, ((size(left,2) - 1) * size(left, 1)) + ((size(left,1) - 1) * size(left, 2)));
%pwFactorNodes = {};
%pwFactorNodesStrings = {};
pwFNCount = 1;
variableCount = 1;

% Set up Variable Dependencies
% variableValues = uint8(zeros(size(left, 1), size(left, 2), 3));
variableValues = zeros(size(left, 1), size(left, 2));
variableIndex = cell(1, size(left, 1) * size(left, 2));
variables = cell(size(left, 1), size(left, 2));
varBelief = cell(size(left, 1), size(left, 2));
oldValues = zeros(size(left, 1), size(left, 2));

for y = 1:1:size(left, 1)
    for x = 1:1:size(left,2)
        varBelief{y,x} = zeros(50,1); 
    end
end

% Create Variables and Pairwise Factor Nodes
for y = 1:1:size(left, 1)
    
    disp(y);
    
    for x = 1:1:size(left,2)
        
        % Handle Each of The Unique Image Location Challanges
        if (y == 1 && x == 1) || (y == 1 && x == size(left,2)) || (y == size(left,1) && x == 1) || (y == size(left,1) && x == size(left,2))
            
            % Corner Cases
            if (y == 1 && x == 1)
                
                rightString = [num2str(y)  ','  num2str(x + 0.5)];
                bottomString = [num2str(y + 0.5)  ','  num2str(x)];
                
                pwFactorNodes{1, pwFNCount} = containers.Map({variableCount}, {zeros(50,1)});
                variables{y,x} = containers.Map({pwFNCount, -1}, {zeros(50,1), pwFNCount});
                pwFactorNodesStrings{1, pwFNCount} = bottomString;
                pwFNCount = pwFNCount + 1;
                
                pwFactorNodes{1, pwFNCount} = containers.Map({variableCount}, {zeros(50,1)});
                variables{y,x}(pwFNCount) = zeros(50,1);
                pwFactorNodesStrings{1, pwFNCount} = rightString;
                currList = variables{y,x}(-1);
                variables{y,x}(-1) = [currList, pwFNCount];
                pwFNCount = pwFNCount + 1;
                
                variableIndex{1, variableCount} = [y x];
                
                variableCount = variableCount + 1;
                
            elseif (y == 1 && x == size(left,2))
                
                leftString = [num2str(y)  ','  num2str(x - 0.5)];
                bottomString = [num2str(y + 0.5)  ','  num2str(x)];
                
                pwFactorNodes{1, pwFNCount} = containers.Map({variableCount}, {zeros(50,1)});
                variables{y,x} = containers.Map({pwFNCount, -1}, {zeros(50,1), pwFNCount});
                pwFactorNodesStrings{1, pwFNCount} = bottomString;
                pwFNCount = pwFNCount + 1;
                
                indexVal = find(strcmp(pwFactorNodesStrings, leftString));
                pwFactorNodes{1, indexVal}(variableCount) = zeros(50,1);
                variables{y,x}(indexVal) = zeros(50,1);
                currList = variables{y,x}(-1);
                variables{y,x}(-1) = [currList indexVal];
                
                variableIndex{1, variableCount} = [y x];
                
                variableCount = variableCount + 1;
                
            elseif (y == size(left,1) && x == 1)
                
                topString = [num2str(y - 0.5)  ','  num2str(x)];
                rightString = [num2str(y)  ','  num2str(x + 0.5)];
                
                pwFactorNodes{1, pwFNCount} = containers.Map({variableCount}, {zeros(50,1)});
                variables{y,x} = containers.Map({pwFNCount, -1}, {zeros(50,1), pwFNCount});
                pwFactorNodesStrings{1, pwFNCount} = rightString;
                pwFNCount = pwFNCount + 1;
                
                indexVal = find(strcmp(pwFactorNodesStrings, topString));
                pwFactorNodes{1, indexVal}(variableCount) = zeros(50,1);
                variables{y,x}(indexVal) = zeros(50,1);
                currList = variables{y,x}(-1);
                variables{y,x}(-1) = [currList indexVal];
                
                variableIndex{1, variableCount} = [y x];
                
                variableCount = variableCount + 1;
                
                
            else
                
                topString = [num2str(y - 0.5)  ','  num2str(x)];
                leftString = [num2str(y)  ','  num2str(x - 0.5)];
                
                indexVal = find(strcmp(pwFactorNodesStrings, topString));
                pwFactorNodes{1, indexVal}(variableCount) = zeros(50,1);
                variables{y,x} = containers.Map({indexVal, -1}, {zeros(50,1), indexVal});
                
                indexVal = find(strcmp(pwFactorNodesStrings, leftString));
                pwFactorNodes{1, indexVal}(variableCount) = zeros(50,1);
                variables{y,x}(indexVal) = zeros(50,1);
                currList = variables{y,x}(-1);
                variables{y,x}(-1) = [currList indexVal];
                
                variableIndex{1, variableCount} = [y x];
                
                variableCount = variableCount + 1;
                
            end
            
        elseif y == 1 || x == 1 || y == size(left,1) || x == size(left,2)
            
            % Edge Cases
            if y == 1
                
                leftString = [num2str(y)  ','  num2str(x - 0.5)];
                rightString = [num2str(y)  ','  num2str(x + 0.5)];
                bottomString = [num2str(y + 0.5)  ','  num2str(x)];
                
                pwFactorNodes{1, pwFNCount} = containers.Map({variableCount}, {zeros(50,1)});
                variables{y,x} = containers.Map({pwFNCount, -1}, {zeros(50,1), pwFNCount});
                pwFactorNodesStrings{1, pwFNCount} = bottomString;
                pwFNCount = pwFNCount + 1;
                
                indexVal = find(strcmp(pwFactorNodesStrings, leftString));
                pwFactorNodes{1, indexVal}(variableCount) = zeros(50,1);
                variables{y,x}(indexVal) = zeros(50,1);
                currList = variables{y,x}(-1);
                variables{y,x}(-1) = [currList indexVal];
                
                pwFactorNodes{1, pwFNCount} = containers.Map({variableCount}, {zeros(50,1)});
                variables{y,x}(pwFNCount) = zeros(50,1);
                currList = variables{y,x}(-1);
                variables{y,x}(-1) = [currList pwFNCount];
                pwFactorNodesStrings{1, pwFNCount} = rightString;
                pwFNCount = pwFNCount + 1;
                
                variableIndex{1, variableCount} = [y x];
                
                variableCount = variableCount + 1;
                
            elseif x == 1
                
                topString = [num2str(y - 0.5)  ','  num2str(x)];
                rightString = [num2str(y)  ','  num2str(x + 0.5)];
                bottomString = [num2str(y + 0.5)  ','  num2str(x)];
                
                indexVal = find(strcmp(pwFactorNodesStrings, topString));
                pwFactorNodes{1, indexVal}(variableCount) = zeros(50,1);
                variables{y,x} = containers.Map({indexVal, -1}, {zeros(50,1), indexVal});
                
                pwFactorNodes{1, pwFNCount} = containers.Map({variableCount}, {zeros(50,1)});
                variables{y,x}(pwFNCount) = zeros(50,1);
                currList = variables{y,x}(-1);
                variables{y,x}(-1) = [currList pwFNCount];
                pwFactorNodesStrings{1, pwFNCount} = bottomString;
                pwFNCount = pwFNCount + 1;
                
                pwFactorNodes{1, pwFNCount} = containers.Map({variableCount}, {zeros(50,1)});
                variables{y,x}(pwFNCount) = zeros(50,1);
                currList = variables{y,x}(-1);
                variables{y,x}(-1) = [currList pwFNCount];
                pwFactorNodesStrings{1, pwFNCount} = rightString;
                pwFNCount = pwFNCount + 1;
                
                variableIndex{1, variableCount} = [y x];
                
                variableCount = variableCount + 1;
                
            elseif y == size(left,1)
                
                topString = [num2str(y - 0.5)  ','  num2str(x)];
                leftString = [num2str(y)  ','  num2str(x - 0.5)];
                rightString = [num2str(y)  ','  num2str(x  + 0.5)];
                
                indexVal = find(strcmp(pwFactorNodesStrings, topString));
                pwFactorNodes{1, indexVal}(variableCount) = zeros(50,1);
                variables{y,x} = containers.Map({indexVal, -1}, {zeros(50,1), indexVal});
                
                indexVal = find(strcmp(pwFactorNodesStrings, leftString));
                pwFactorNodes{1, indexVal}(variableCount) = zeros(50,1);
                variables{y,x}(indexVal) = zeros(50,1);
                currList = variables{y,x}(-1);
                variables{y,x}(-1) = [currList indexVal];
                
                pwFactorNodes{1, pwFNCount} = containers.Map({variableCount}, {zeros(50,1)});
                variables{y,x}(pwFNCount) = zeros(50,1);
                currList = variables{y,x}(-1);
                variables{y,x}(-1) = [currList pwFNCount];
                pwFactorNodesStrings{1, pwFNCount} = rightString;
                pwFNCount = pwFNCount + 1;
                
                variableIndex{1, variableCount} = [y x];
                
                variableCount = variableCount + 1;
                
            else
                
                topString = [num2str(y - 0.5)  ','  num2str(x)];
                leftString = [num2str(y)  ','  num2str(x - 0.5)];
                bottomString = [num2str(y + 0.5)  ','  num2str(x)];
                
                indexVal = find(strcmp(pwFactorNodesStrings, topString));
                pwFactorNodes{1, indexVal}(variableCount) = zeros(50,1);
                variables{y,x} = containers.Map({indexVal, -1}, {zeros(50,1), indexVal});
                
                indexVal = find(strcmp(pwFactorNodesStrings, leftString));
                pwFactorNodes{1, indexVal}(variableCount) = zeros(50,1);
                variables{y,x}(indexVal) = zeros(50,1);
                currList = variables{y,x}(-1);
                variables{y,x}(-1) = [currList indexVal];
                
                pwFactorNodes{1, pwFNCount} = containers.Map({variableCount}, {zeros(50,1)});
                variables{y,x}(pwFNCount) = zeros(50,1);
                currList = variables{y,x}(-1);
                variables{y,x}(-1) = [currList pwFNCount];
                pwFactorNodesStrings{1, pwFNCount} = bottomString;
                pwFNCount = pwFNCount + 1;
                
                variableIndex{1, variableCount} = [y x];
                
                variableCount = variableCount + 1;
                
            end
            
        else
            
            % General Cases
            topString = [num2str(y - 0.5)  ','  num2str(x)];
            bottomString = [num2str(y + 0.5)  ','  num2str(x)];
            leftString = [num2str(y)  ','  num2str(x - 0.5)];
            rightString = [num2str(y)  ','  num2str(x  + 0.5)];
            
            indexVal = find(strcmp(pwFactorNodesStrings, topString));
            pwFactorNodes{1, indexVal}(variableCount) = zeros(50,1);
            variables{y,x} = containers.Map({indexVal, -1}, {zeros(50,1), indexVal});
            
            pwFactorNodes{1, pwFNCount} = containers.Map({variableCount}, {zeros(50,1)});
            variables{y,x}(pwFNCount) = zeros(50,1);
            currList = variables{y,x}(-1);
            variables{y,x}(-1) = [currList pwFNCount];
            pwFactorNodesStrings{1, pwFNCount} = bottomString;
            pwFNCount = pwFNCount + 1;
            
            indexVal = find(strcmp(pwFactorNodesStrings, leftString));
            pwFactorNodes{1, indexVal}(variableCount) = zeros(50,1);
            variables{y,x}(indexVal) = zeros(50,1);
            currList = variables{y,x}(-1);
            variables{y,x}(-1) = [currList indexVal];
            
            
            pwFactorNodes{1, pwFNCount} = containers.Map({variableCount}, {zeros(50,1)});
            variables{y,x}(pwFNCount) = zeros(50,1);
            currList = variables{y,x}(-1);
            variables{y,x}(-1) = [currList pwFNCount];
            pwFactorNodesStrings{1, pwFNCount} = rightString;
            pwFNCount = pwFNCount + 1;
            
            variableIndex{1, variableCount} = [y x];
            
            variableCount = variableCount + 1;
            
        end
    end
end

% Create Cell Matrix For Each Unary Factor Node
unaryFactorNodes = cell(size(left, 1), size(left, 2));

% Build Unary Factor Nodes
for y = 1:1:size(left, 1)
    for x = 1:1:size(left,2)
        msgs = zeros(50,1);
        for d = 1:1:50
            if x - d < 1
                % Check With TA
                msgs(d, 1) = 0;
            else
                msgs(d, 1) = abs(left(y,x) - right(y, x - d));
            end
            
        end
        unaryFactorNodes{y, x} = msgs;
    end
end

lambdaArr = [lambda];

for i = 1:1:size(lambdaArr, 2)
    iterations = 1;
    while iterations ~= 21
        
        % Step 1
        variableCount = 1;
        for y = 1:1:size(left, 1)
            for x = 1:1:size(left,2)
                
                currVar = variables{y, x};
                currBelief = varBelief{y, x};
                
                allFN = currVar(-1);
                
                % Iterate Through Keys
                for j = 1:1:size(allFN, 2)
                    
                    currIndex = allFN(1, j);
                    
                    %currFactor = pwFactorNodes{1, indexVal};
                    currFNMsg = pwFactorNodes{1, currIndex}(variableCount);
                    
                    currVarMsg = currBelief - currFNMsg;
                    
                    % Store Message in Current Variable Map
                    currVar(currIndex) = currVarMsg;
                    
                end
                
                % Unary Factor Node - Might Not Need This!!!!!!!!!!!!!
                %unaryFactorNodes{y,x} = currBelief - unaryFactorNodes{y,x};
                
                variableCount = variableCount + 1;
                
            end
        end
        
        % Step 2
        for f = 1:1:size(pwFactorNodes, 2)
            
            %disp(f);
            
            currFN = pwFactorNodes{1, f};
            allFNKeys = keys(currFN);
            
            if size(allFNKeys, 2) ~= 2
                disp('Yall Got Problems');
            end
            
            firstVarKeyIndex = allFNKeys{1,1};
            secondVarKeyIndex = allFNKeys{1,2};
            
            firstIndex = variableIndex{1, firstVarKeyIndex};
            secondIndex = variableIndex{1, secondVarKeyIndex};
            
            firstVar = variables{firstIndex(1), firstIndex(2)};
            secondVar = variables{secondIndex(1), secondIndex(2)};
            
            firstMsg = firstVar(f);
            
            secondMsg = secondVar(f);
            
            
            for b1 = 1:1:2
                
                createMsg = zeros(50,1);
                
                for p1 = 1:1:50
                    
                    minValue = inf;
                    
                    for p2 = 1:1:50
                        
                        firstPart = 0;
                        
                        if p1 ~= p2
                            firstPart = lambdaArr(i);
                        else
                            firstPart = 0;
                        end
                        
                        answer = 0;
                        
                        if b1 == 1
                            answer = firstPart + secondMsg(p2, 1);
                        else
                            answer = firstPart + firstMsg(p2, 1);
                        end
                        
                        if answer < minValue
                            minValue = answer;
                            if answer == 0
                                break;
                            end
                        end
                        
                    end
                    
                    createMsg(p1, 1) = minValue;
                    
                end
                
                if b1 == 1
                    currFN(firstVarKeyIndex) = createMsg;
                else
                    currFN(secondVarKeyIndex) = createMsg;
                end
                
            end
            
        end
        
        
        % Step 3
        variableCount = 1;
        for y = 1:1:size(left, 1)
            for x = 1:1:size(left,2)
                
                currVar = variables{y,x};
                currBelief = varBelief{y, x};
                
                allVarKeys = currVar(-1);
                
                beliefSum = zeros(50,1);
                
                for v = 1:1:size(allVarKeys, 2)
                    
                    currKey = allVarKeys(v);
                    
                    currFactor = pwFactorNodes{1, currKey};
                    
                    currFNMsg = currFactor(variableCount);
                    
                    beliefSum = beliefSum + currFNMsg;
                    
                end
                
                beliefSum = beliefSum + unaryFactorNodes{y,x};
                
                varBelief{y, x} = beliefSum;
                
                % Step 4
                minValue = inf;
                for mv = 1:1:50
                    if beliefSum(mv, 1) < minValue
                        minValue = mv;
                        if beliefSum(mv, 1) == 0
                            break;
                        end
                    end
                end
                
                variableValues(y,x) = minValue;
                
                variableCount = variableCount + 1;
                
            end
        end
        
        
        
        if iterations ~= 1
            tf = isequal(oldValues, variableValues);
            
            if tf == 1
                disp('Successful Convergance For Lambda:');
                disp(lambda);
                disp('On Iteration Count:');
                disp(iterations);
                disp('Variable Values:')
                disp(variableValues);
                
                figure, imshow(variableValues, [0 50]);
                print('Outputs\successConverge','-dpng');
                
                break;
            else
                oldValues = variableValues;
            end
            
        else
            oldValues = variableValues;
        end
        
        if iterations == 20
            disp('Convergance For Lambda:');
            disp(lambda);
            disp('On Iteration Count 20');
            
            disp('Variable Values:')
            disp(variableValues);
            figure, imshow(variableValues, [0 50]);
            print('Outputs\iteration20','-dpng');
        end
        
        figure, imshow(variableValues, [0 50]);
        print('Outputs\iteration','-dpng');
        iterations = iterations + 1;
        
    end
    
    totalCost = 0;
    
    for y = 1:1:size(left, 1)
        for x = 1:1:size(left,2)
            index = variableValues{y,x,1};
            totalCost = totalCost + unaryFactorNodes{y,x}(index, 1);
            
        end
    end
    
    disp('Total Cost For This Lambda:');
    disp(totalCost);
    
end

end

