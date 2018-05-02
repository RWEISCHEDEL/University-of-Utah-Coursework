function outputValue = NCC(patch1, patch2)
%format shortEng;
Nx = size(patch1, 1);
Ny = size(patch1, 2);

numerator = 0.0;
denominatorA = 0.0;
denominatorB = 0.0;

for i = 1:1:Nx
    for j = 1:1:Ny
        numerator = numerator + (patch1(i,j) * patch2(i,j));
        
        denominatorA = denominatorA + (patch1(i,j)^2);
        
        denominatorB = denominatorB + (patch2(i,j)^2);
        
    end
end

denominator = sqrt(denominatorA) * sqrt(denominatorB);
outputValue = numerator / denominator;

end