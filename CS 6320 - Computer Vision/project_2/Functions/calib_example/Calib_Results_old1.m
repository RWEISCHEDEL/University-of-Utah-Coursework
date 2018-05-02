% Intrinsic and Extrinsic Camera Parameters
%
% This script file can be directly executed under Matlab to recover the camera intrinsic and extrinsic parameters.
% IMPORTANT: This file contains neither the structure of the calibration objects nor the image coordinates of the calibration points.
%            All those complementary variables are saved in the complete matlab data file Calib_Results.mat.
% For more information regarding the calibration model visit http://www.vision.caltech.edu/bouguetj/calib_doc/


%-- Focal length:
fc = [ 657.462902987407233 ; 657.946729179114413 ];

%-- Principal point:
cc = [ 303.136648967949213 ; 242.569349480213276 ];

%-- Skew coefficient:
alpha_c = 0.000000000000000;

%-- Distortion coefficients:
kc = [ -0.254025425598056 ; 0.121433037265364 ; -0.000208645992505 ; 0.000019419182850 ; 0.000000000000000 ];

%-- Focal length uncertainty:
fc_error = [ 0.318188663814737 ; 0.340463141961926 ];

%-- Principal point uncertainty:
cc_error = [ 0.646820773483095 ; 0.592184120026702 ];

%-- Skew coefficient uncertainty:
alpha_c_error = 0.000000000000000;

%-- Distortion coefficients uncertainty:
kc_error = [ 0.002482739126438 ; 0.009863684458820 ; 0.000133841011262 ; 0.000132174861671 ; 0.000000000000000 ];

%-- Image size:
nx = 640;
ny = 480;


%-- Various other variables (may be ignored if you do not use the Matlab Calibration Toolbox):
%-- Those variables are used to control which intrinsic parameters should be optimized

n_ima = 20;						% Number of calibration images
est_fc = [ 1 ; 1 ];					% Estimation indicator of the two focal variables
est_aspect_ratio = 1;				% Estimation indicator of the aspect ratio fc(2)/fc(1)
center_optim = 1;					% Estimation indicator of the principal point
est_alpha = 0;						% Estimation indicator of the skew coefficient
est_dist = [ 1 ; 1 ; 1 ; 1 ; 0 ];	% Estimation indicator of the distortion coefficients


%-- Extrinsic parameters:
%-- The rotation (omc_kk) and the translation (Tc_kk) vectors for every calibration image and their uncertainties

%-- Image #1:
omc_1 = [ 1.654927e+00 ; 1.651990e+00 ; -6.694056e-01 ];
Tc_1  = [ -1.777722e+02 ; -8.387717e+01 ; 8.531470e+02 ];
omc_error_1 = [ 7.556282e-04 ; 9.761973e-04 ; 1.248197e-03 ];
Tc_error_1  = [ 8.399644e-01 ; 7.749026e-01 ; 4.258635e-01 ];

%-- Image #2:
omc_2 = [ 1.849122e+00 ; 1.900689e+00 ; -3.966399e-01 ];
Tc_2  = [ -1.551106e+02 ; -1.594755e+02 ; 7.576537e+02 ];
omc_error_2 = [ 7.937137e-04 ; 9.699436e-04 ; 1.509095e-03 ];
Tc_error_2  = [ 7.499155e-01 ; 6.867926e-01 ; 4.185276e-01 ];

%-- Image #3:
omc_3 = [ 1.742596e+00 ; 2.077768e+00 ; -5.049764e-01 ];
Tc_3  = [ -1.253217e+02 ; -1.747120e+02 ; 7.755769e+02 ];
omc_error_3 = [ 7.264974e-04 ; 1.027759e-03 ; 1.560224e-03 ];
Tc_error_3  = [ 7.666142e-01 ; 7.028454e-01 ; 4.023068e-01 ];

%-- Image #4:
omc_4 = [ 1.828127e+00 ; 2.116859e+00 ; -1.103002e+00 ];
Tc_4  = [ -6.453637e+01 ; -1.549583e+02 ; 7.791379e+02 ];
omc_error_4 = [ 6.521044e-04 ; 1.064806e-03 ; 1.460894e-03 ];
Tc_error_4  = [ 7.726218e-01 ; 7.014739e-01 ; 3.240764e-01 ];

%-- Image #5:
omc_5 = [ 1.079364e+00 ; 1.922483e+00 ; -2.531027e-01 ];
Tc_5  = [ -9.239877e+01 ; -2.290576e+02 ; 7.368643e+02 ];
omc_error_5 = [ 6.368614e-04 ; 9.927289e-04 ; 1.118389e-03 ];
Tc_error_5  = [ 7.360466e-01 ; 6.694891e-01 ; 3.960949e-01 ];

%-- Image #6:
omc_6 = [ -1.701347e+00 ; -1.929109e+00 ; -7.917737e-01 ];
Tc_6  = [ -1.489510e+02 ; -7.969974e+01 ; 4.447636e+02 ];
omc_error_6 = [ 6.120456e-04 ; 9.909005e-04 ; 1.342096e-03 ];
Tc_error_6  = [ 4.407948e-01 ; 4.136449e-01 ; 3.386548e-01 ];

%-- Image #7:
omc_7 = [ 1.996584e+00 ; 1.931515e+00 ; 1.311022e+00 ];
Tc_7  = [ -8.303029e+01 ; -7.776441e+01 ; 4.401634e+02 ];
omc_error_7 = [ 1.171879e-03 ; 6.020200e-04 ; 1.408372e-03 ];
Tc_error_7  = [ 4.430624e-01 ; 4.044123e-01 ; 3.577743e-01 ];

%-- Image #8:
omc_8 = [ 1.961144e+00 ; 1.824220e+00 ; 1.326777e+00 ];
Tc_8  = [ -1.701871e+02 ; -1.035782e+02 ; 4.620017e+02 ];
omc_error_8 = [ 1.118755e-03 ; 6.142452e-04 ; 1.350931e-03 ];
Tc_error_8  = [ 4.843088e-01 ; 4.393015e-01 ; 4.030044e-01 ];

%-- Image #9:
omc_9 = [ -1.363620e+00 ; -1.980445e+00 ; 3.210462e-01 ];
Tc_9  = [ -1.953735e+00 ; -2.251604e+02 ; 7.284425e+02 ];
omc_error_9 = [ 7.627067e-04 ; 9.794494e-04 ; 1.262195e-03 ];
Tc_error_9  = [ 7.258479e-01 ; 6.590049e-01 ; 4.118179e-01 ];

%-- Image #10:
omc_10 = [ -1.513021e+00 ; -2.086648e+00 ; 1.888887e-01 ];
Tc_10  = [ -2.964531e+01 ; -3.004827e+02 ; 8.600792e+02 ];
omc_error_10 = [ 9.303619e-04 ; 1.113507e-03 ; 1.677759e-03 ];
Tc_error_10  = [ 8.724449e-01 ; 7.834915e-01 ; 5.466111e-01 ];

%-- Image #11:
omc_11 = [ -1.793031e+00 ; -2.064967e+00 ; -4.800574e-01 ];
Tc_11  = [ -1.511241e+02 ; -2.354825e+02 ; 7.046449e+02 ];
omc_error_11 = [ 8.348858e-04 ; 1.051077e-03 ; 1.806837e-03 ];
Tc_error_11  = [ 7.152120e-01 ; 6.709859e-01 ; 5.408608e-01 ];

%-- Image #12:
omc_12 = [ -1.838801e+00 ; -2.087239e+00 ; -5.158068e-01 ];
Tc_12  = [ -1.335494e+02 ; -1.773138e+02 ; 6.048337e+02 ];
omc_error_12 = [ 7.113804e-04 ; 1.009613e-03 ; 1.665873e-03 ];
Tc_error_12  = [ 6.091251e-01 ; 5.673267e-01 ; 4.521094e-01 ];

%-- Image #13:
omc_13 = [ -1.918716e+00 ; -2.116506e+00 ; -5.945301e-01 ];
Tc_13  = [ -1.327533e+02 ; -1.436466e+02 ; 5.446943e+02 ];
omc_error_13 = [ 6.635598e-04 ; 9.992846e-04 ; 1.637761e-03 ];
Tc_error_13  = [ 5.469901e-01 ; 5.078463e-01 ; 4.103313e-01 ];

%-- Image #14:
omc_14 = [ -1.954108e+00 ; -2.124529e+00 ; -5.849552e-01 ];
Tc_14  = [ -1.236530e+02 ; -1.372114e+02 ; 4.907438e+02 ];
omc_error_14 = [ 6.245083e-04 ; 9.794655e-04 ; 1.603350e-03 ];
Tc_error_14  = [ 4.935251e-01 ; 4.570917e-01 ; 3.682275e-01 ];

%-- Image #15:
omc_15 = [ -2.110566e+00 ; -2.253735e+00 ; -4.956802e-01 ];
Tc_15  = [ -1.992011e+02 ; -1.345711e+02 ; 4.748874e+02 ];
omc_error_15 = [ 7.212010e-04 ; 9.179455e-04 ; 1.748455e-03 ];
Tc_error_15  = [ 4.839447e-01 ; 4.533435e-01 ; 3.970590e-01 ];

%-- Image #16:
omc_16 = [ 1.887030e+00 ; 2.336243e+00 ; -1.738237e-01 ];
Tc_16  = [ -1.600945e+01 ; -1.703999e+02 ; 6.956756e+02 ];
omc_error_16 = [ 9.919284e-04 ; 1.047979e-03 ; 2.180147e-03 ];
Tc_error_16  = [ 6.888377e-01 ; 6.256237e-01 ; 4.702827e-01 ];

%-- Image #17:
omc_17 = [ -1.612583e+00 ; -1.953293e+00 ; -3.477426e-01 ];
Tc_17  = [ -1.352755e+02 ; -1.389982e+02 ; 4.899671e+02 ];
omc_error_17 = [ 6.169671e-04 ; 9.435882e-04 ; 1.330107e-03 ];
Tc_error_17  = [ 4.870975e-01 ; 4.533525e-01 ; 3.263081e-01 ];

%-- Image #18:
omc_18 = [ -1.341592e+00 ; -1.692585e+00 ; -2.972891e-01 ];
Tc_18  = [ -1.854393e+02 ; -1.578335e+02 ; 4.410359e+02 ];
omc_error_18 = [ 7.083409e-04 ; 9.165536e-04 ; 1.050340e-03 ];
Tc_error_18  = [ 4.428011e-01 ; 4.132089e-01 ; 3.171109e-01 ];

%-- Image #19:
omc_19 = [ -1.925664e+00 ; -1.837985e+00 ; -1.440613e+00 ];
Tc_19  = [ -1.066231e+02 ; -7.960069e+01 ; 3.340900e+02 ];
omc_error_19 = [ 6.093758e-04 ; 1.074328e-03 ; 1.361187e-03 ];
Tc_error_19  = [ 3.437375e-01 ; 3.154383e-01 ; 2.973880e-01 ];

%-- Image #20:
omc_20 = [ 1.895969e+00 ; 1.593180e+00 ; 1.471967e+00 ];
Tc_20  = [ -1.439592e+02 ; -8.800889e+01 ; 3.963770e+02 ];
omc_error_20 = [ 1.134725e-03 ; 6.280014e-04 ; 1.223604e-03 ];
Tc_error_20  = [ 4.197913e-01 ; 3.762218e-01 ; 3.599803e-01 ];

