function BP()

% Variables Mapping
x1 = containers.Map({'c1', 'c8', 'c9', 'c10'}, {[0 0], [0 0], [0 0], [0 0]});
x2 = containers.Map({'c2', 'c8', 'c9'}, {[0 0], [0 0], [0 0]});
x3 = containers.Map({'c3', 'c8', 'c10'}, {[0 0], [0 0], [0 0]});
x4 = containers.Map({'c4', 'c9', 'c10'}, {[0 0], [0 0], [0 0]});
x5 = containers.Map({'c5', 'c8'}, {[0 0], [0 0]});
x6 = containers.Map({'c6', 'c9'}, {[0 0], [0 0]});
x7 = containers.Map({'c7', 'c10'}, {[0 0], [0 0]});

%Factor Nodes Mapping
c1 = containers.Map({'x1'}, {[0 0]});
c2 = containers.Map({'x2'}, {[0 0]});
c3 = containers.Map({'x3'}, {[0 0]});
c4 = containers.Map({'x4'}, {[0 0]});
c5 = containers.Map({'x5'}, {[0 0]});
c6 = containers.Map({'x6'}, {[0 0]});
c7 = containers.Map({'x7'}, {[0 0]});
c8 = containers.Map({'x1', 'x2', 'x3', 'x5'}, {[0 0], [0 0], [0 0], [0 0]});
c9 = containers.Map({'x1', 'x2', 'x4', 'x6'}, {[0 0], [0 0], [0 0], [0 0]});
c10 = containers.Map({'x1', 'x3', 'x4', 'x7'}, {[0 0], [0 0], [0 0], [0 0]});

% Build Cost Table
allCosts = {[0.0 3.0], [0.0 2.0], [0.0 2.5], [0.0 5.4], [0.0 4.0], [0.2 0.0], [0.7 0.0]};

% Variable Information
variables = {x1 x2 x3 x4 x5 x6 x7};
variablesStrings = {'x1' 'x2' 'x3' 'x4' 'x5' 'x6' 'x7'};
varValues = [0 0 0 0 0 0 0];
varBelief = {[0 0] [0 0] [0 0] [0 0] [0 0] [0 0] [0 0]};

% Factor Nodes
factorNodes = {c1 c2 c3 c4 c5 c6 c7 c8 c9 c10};
factorNodesStrings = {'c1', 'c2', 'c3', 'c4', 'c5', 'c6', 'c7', 'c8', 'c9', 'c10'};

allPossibilites = [0 0 0 0;
                   0 0 0 1; 
                   0 0 1 0; 
                   0 0 1 1; 
                   0 1 0 0; 
                   0 1 0 1;
                   0 1 1 0;
                   0 1 1 1;
                   1 0 0 0;
                   1 0 0 1;
                   1 0 1 0;
                   1 0 1 1;
                   1 1 0 0;
                   1 1 0 1;
                   1 1 1 0;
                   1 1 1 1];
               
allSolutions = [0 1000 1000 0 1000 0 0 1000 1000 0 0 1000 0 1000 1000 0];

threshold = 1;

oldValues = [];

% Add Additional Check For No Changes
while(threshold < 60)
    
    % Step 1 - Message From Variable To Factor Node
    for i = 1:1:size(variables, 2)
        
        % Current Variable We are Looking At
        currVar = variables{1, i};
        currVarString = variablesStrings{1, i};
        currBelief = varBelief{1, i};
        
        %Get Variable Keys
        allVarKeys = keys(currVar);
        
        % Iterate Through Keys
        for j = 1:1:size(allVarKeys, 2)
            
            % Look At current Key And Get Message From Var To FN
            currKey = allVarKeys{1, j};
            
            currVarMsg = currVar(currKey);
            
            % Find FN Map
            indexVal = find(strcmp(factorNodesStrings, currKey));
            
            currFactor = factorNodes{1, indexVal};
            
            currFNMsg = currFactor(currVarString);
            
            currVarMsg = currBelief - currFNMsg;
            
            % Store Message in Current Variable Map
            currVar(currKey) = currVarMsg;
            
        end    
        
    end
    
    % Step 2 - Message From Factor Node To Variables
    
    % Since Factor Nodes 1 - 7 Are Only Connected By One Variable Each
    for k = 1:1:7
        
        currFactor = factorNodes{1, k};
        
        FNKey = keys(currFactor);
        
        key = FNKey{1,1};
        
        indexVal = find(strcmp(variablesStrings, key));
        
        %value = allCosts{1, indexVal};
        
        currFactor(key) = allCosts{1, indexVal};
        
        %result = c1('x1')
        
    end
    
    % Factor Nodes 8 - 10 or CA, CB and CC
    for l = 8:1:10
        
        % Get Current Factor Node
        currFactor = factorNodes{1, l};
        
        currFNString = factorNodesStrings{1, l};
        
        allFNKeys = keys(currFactor);
        
        % Get All Variable Nodes Attached To FN
        currVarString1 = allFNKeys{1,1};
        currVarString2 = allFNKeys{1,2};
        currVarString3 = allFNKeys{1,3};
        currVarString4 = allFNKeys{1,4};
        
        indexVal1 = find(strcmp(variablesStrings, currVarString1));
        indexVal2 = find(strcmp(variablesStrings, currVarString2));
        indexVal3 = find(strcmp(variablesStrings, currVarString3));
        indexVal4 = find(strcmp(variablesStrings, currVarString4));
        
        currVar1 = variables{1, indexVal1};
        currVar2 = variables{1, indexVal2};
        currVar3 = variables{1, indexVal3};
        currVar4 = variables{1, indexVal4};
        
        
        % Iterate Through Keys In Factor Node - aka Variables Attached
        for m = 1:1:size(allFNKeys, 2)
            
            currVarMsg = currFactor(allFNKeys{1, m});
            
            allPossibleiterationSums = [];
            
            % Get Possibility Values For the 4 Variables in Each FN
            for n = 1:1:size(allPossibilites, 1)
                
                firstVar = allPossibilites(n, 1);
                secondVar = allPossibilites(n, 2);
                thirdVar = allPossibilites(n, 3);
                fourthVar = allPossibilites(n, 4); 
                
                if m == 1
                    % Ignore First Var 
                    secondVal = currVar2(currFNString);
                    secondVal = secondVal(secondVar + 1);
                    
                    thirdVal = currVar3(currFNString);
                    thirdVal = thirdVal(thirdVar + 1);
                    
                    fourthVal = currVar4(currFNString);
                    fourthVal = fourthVal(fourthVar + 1);
                    
                    sum = allSolutions(n) + secondVal + thirdVal + fourthVal;
                    
                    allPossibleiterationSums = [allPossibleiterationSums, sum];
                    
                elseif m == 2
                    % Ignore Second Var 
                    firstVal = currVar1(currFNString);
                    firstVal = firstVal(firstVar + 1);
                    
                    thirdVal = currVar3(currFNString);
                    thirdVal = thirdVal(thirdVar + 1);
                    
                    fourthVal = currVar4(currFNString);
                    fourthVal = fourthVal(fourthVar + 1);
                    
                    sum = allSolutions(n) + firstVal + thirdVal + fourthVal;
                    
                    allPossibleiterationSums = [allPossibleiterationSums, sum];
                    
                elseif m == 3
                    % Ignore Third Var 
                    firstVal = currVar1(currFNString);
                    firstVal = firstVal(firstVar + 1);
                    
                    secondVal = currVar2(currFNString);
                    secondVal = secondVal(secondVar + 1);
                    
                    fourthVal = currVar4(currFNString);
                    fourthVal = fourthVal(fourthVar + 1);
                    
                    sum = allSolutions(n) + firstVal + secondVal + fourthVal;
                    
                    allPossibleiterationSums = [allPossibleiterationSums, sum];
                else
                    % Ignore Fourth Var 
                    firstVal = currVar1(currFNString);
                    firstVal = firstVal(firstVar + 1);
                    
                    secondVal = currVar2(currFNString);
                    secondVal = secondVal(secondVar + 1);
                    
                    thirdVal = currVar3(currFNString);
                    thirdVal = thirdVal(thirdVar + 1);
                    
                    sum = allSolutions(n) + firstVal + secondVal + thirdVal;
                    
                    allPossibleiterationSums = [allPossibleiterationSums, sum];
                end
                
                
                
            end
            
            % Possibilites Hunt
            
            zeroIndex = [];
            oneIndex = [];
            for p = 1:1:size(allPossibilites, 1)
                
                if(allPossibilites(p, m) == 0)
                    % Add Zero
                    zeroIndex = [zeroIndex, p];
                else
                    % Add One
                    oneIndex = [oneIndex, p];
                end
                
            end
            
            minZero = intmax('int64');
            minOne = intmax('int64');
            
            % Find Lock 0 Variable Min
            for zeros = 1:1:size(zeroIndex, 2)
                sum = allSolutions(zeroIndex(zeros)) + allPossibleiterationSums(zeroIndex(zeros));
                
                if sum < minZero
                    minZero = sum;
                end
            end
            
            % Find Lock 1 Variable Min
            for ones = 1:1:size(oneIndex, 2)
                sum = allSolutions(oneIndex(ones)) + allPossibleiterationSums(oneIndex(ones));
                
                if sum < minOne
                    minOne = sum;
                end
            end
            
            currFactor(allFNKeys{1, m}) = [minZero minOne];
            
        end
        
    end
    
    
    % Step 3 - Update Belief of All Variables 
    
    for b = 1:1:size(variables, 2)
        currVar = variables{1, b};
        currVarString = variablesStrings{1, b};
        currBelief = varBelief{1, b};
        
        allVarKeys = keys(currVar);
        
        beliefSum = [0 0];
        
        % Iterate Through Keys
        for j = 1:1:size(allVarKeys, 2)
            
            currKey = allVarKeys{1, j};
            
            indexVal = find(strcmp(factorNodesStrings, currKey));
            
            currFactor = factorNodes{1, indexVal};
            
            currFNMsg = currFactor(currVarString);
            
            beliefSum = beliefSum + currFNMsg;
            
            varBelief{1, b} = beliefSum;
            
        end
        
        % Step 4 - Update Values of All Variables
        if beliefSum(1) <= beliefSum(2)
            varValues(b) = 0;
        else
            varValues(b) = 1;
        end
        
    end 
    
    if threshold ~= 1
        
        tf = isequal(oldValues, varValues);
        
        if tf == 1
            disp('Successful Convergance On Threshold Count:');
            disp(threshold);
            disp('Variable Values:')
            disp(varValues);
            break;
        else
            oldValues = varValues;
        end
        
    else
        oldValues = varValues;
    end
    
    % Increment Threshold
    threshold = threshold + 1;  
    
end

if threshold == 60
    disp('Failed To Converge in 60 Iterations');
end







end