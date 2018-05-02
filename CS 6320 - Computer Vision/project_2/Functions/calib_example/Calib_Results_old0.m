% Intrinsic and Extrinsic Camera Parameters
%
% This script file can be directly executed under Matlab to recover the camera intrinsic and extrinsic parameters.
% IMPORTANT: This file contains neither the structure of the calibration objects nor the image coordinates of the calibration points.
%            All those complementary variables are saved in the complete matlab data file Calib_Results.mat.
% For more information regarding the calibration model visit http://www.vision.caltech.edu/bouguetj/calib_doc/


%-- Focal length:
fc = [ 657.395393648997242 ; 657.763147050215821 ];

%-- Principal point:
cc = [ 302.983742014508721 ; 242.616264254903314 ];

%-- Skew coefficient:
alpha_c = 0.000000000000000;

%-- Distortion coefficients:
kc = [ -0.255839056668703 ; 0.127576412090142 ; -0.000208105182939 ; 0.000033482691356 ; 0.000000000000000 ];

%-- Focal length uncertainty:
fc_error = [ 0.346911875706950 ; 0.371112458661809 ];

%-- Principal point uncertainty:
cc_error = [ 0.705464054221914 ; 0.645527712780053 ];

%-- Skew coefficient uncertainty:
alpha_c_error = 0.000000000000000;

%-- Distortion coefficients uncertainty:
kc_error = [ 0.002706689674287 ; 0.010758237817318 ; 0.000145789166904 ; 0.000144007722061 ; 0.000000000000000 ];

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
omc_1 = [ 1.654779e+00 ; 1.651918e+00 ; -6.699925e-01 ];
Tc_1  = [ -1.775774e+02 ; -8.374079e+01 ; 8.529832e+02 ];
omc_error_1 = [ 8.237491e-04 ; 1.064560e-03 ; 1.360699e-03 ];
Tc_error_1  = [ 9.161030e-01 ; 8.447700e-01 ; 4.641404e-01 ];

%-- Image #2:
omc_2 = [ 1.849011e+00 ; 1.900560e+00 ; -3.971212e-01 ];
Tc_2  = [ -1.549645e+02 ; -1.593546e+02 ; 7.576049e+02 ];
omc_error_2 = [ 8.655041e-04 ; 1.057837e-03 ; 1.645356e-03 ];
Tc_error_2  = [ 8.179974e-01 ; 7.488549e-01 ; 4.562456e-01 ];

%-- Image #3:
omc_3 = [ 1.742391e+00 ; 2.077563e+00 ; -5.052450e-01 ];
Tc_3  = [ -1.252425e+02 ; -1.746277e+02 ; 7.754806e+02 ];
omc_error_3 = [ 7.921065e-04 ; 1.120867e-03 ; 1.700925e-03 ];
Tc_error_3  = [ 8.361745e-01 ; 7.663271e-01 ; 4.385832e-01 ];

%-- Image #4:
omc_4 = [ 1.827858e+00 ; 2.116776e+00 ; -1.103193e+00 ];
Tc_4  = [ -6.443339e+01 ; -1.548702e+02 ; 7.791044e+02 ];
omc_error_4 = [ 7.109841e-04 ; 1.161242e-03 ; 1.592719e-03 ];
Tc_error_4  = [ 8.427321e-01 ; 7.648947e-01 ; 3.533143e-01 ];

%-- Image #5:
omc_5 = [ 1.079052e+00 ; 1.922500e+00 ; -2.527477e-01 ];
Tc_5  = [ -9.224641e+01 ; -2.291555e+02 ; 7.366550e+02 ];
omc_error_5 = [ 6.944831e-04 ; 1.082856e-03 ; 1.219279e-03 ];
Tc_error_5  = [ 8.027148e-01 ; 7.298435e-01 ; 4.319026e-01 ];

%-- Image #6:
omc_6 = [ -1.701812e+00 ; -1.929291e+00 ; -7.914702e-01 ];
Tc_6  = [ -1.489024e+02 ; -7.964767e+01 ; 4.449783e+02 ];
omc_error_6 = [ 6.674802e-04 ; 1.081016e-03 ; 1.464362e-03 ];
Tc_error_6  = [ 4.810538e-01 ; 4.512359e-01 ; 3.694875e-01 ];

%-- Image #7:
omc_7 = [ 1.996748e+00 ; 1.931472e+00 ; 1.310634e+00 ];
Tc_7  = [ -8.293240e+01 ; -7.773531e+01 ; 4.401759e+02 ];
omc_error_7 = [ 1.278362e-03 ; 6.563942e-04 ; 1.535911e-03 ];
Tc_error_7  = [ 4.832973e-01 ; 4.409768e-01 ; 3.901101e-01 ];

%-- Image #8:
omc_8 = [ 1.961458e+00 ; 1.824261e+00 ; 1.326197e+00 ];
Tc_8  = [ -1.701121e+02 ; -1.035605e+02 ; 4.620733e+02 ];
omc_error_8 = [ 1.220037e-03 ; 6.695324e-04 ; 1.473027e-03 ];
Tc_error_8  = [ 5.283969e-01 ; 4.790945e-01 ; 4.394754e-01 ];

%-- Image #9:
omc_9 = [ -1.363691e+00 ; -1.980542e+00 ; 3.210319e-01 ];
Tc_9  = [ -1.878782e+00 ; -2.251587e+02 ; 7.286465e+02 ];
omc_error_9 = [ 8.318218e-04 ; 1.068291e-03 ; 1.376511e-03 ];
Tc_error_9  = [ 7.919953e-01 ; 7.188158e-01 ; 4.491842e-01 ];

%-- Image #10:
omc_10 = [ -1.513265e+00 ; -2.086817e+00 ; 1.882465e-01 ];
Tc_10  = [ -2.960792e+01 ; -3.004308e+02 ; 8.601618e+02 ];
omc_error_10 = [ 1.014557e-03 ; 1.214632e-03 ; 1.830495e-03 ];
Tc_error_10  = [ 9.517851e-01 ; 8.544744e-01 ; 5.961768e-01 ];

%-- Image #11:
omc_11 = [ -1.793085e+00 ; -2.064817e+00 ; -4.799214e-01 ];
Tc_11  = [ -1.510537e+02 ; -2.353637e+02 ; 7.047465e+02 ];
omc_error_11 = [ 9.101245e-04 ; 1.146143e-03 ; 1.970019e-03 ];
Tc_error_11  = [ 7.802690e-01 ; 7.317395e-01 ; 5.897815e-01 ];

%-- Image #12:
omc_12 = [ -1.839113e+00 ; -2.087345e+00 ; -5.155437e-01 ];
Tc_12  = [ -1.334803e+02 ; -1.772297e+02 ; 6.049746e+02 ];
omc_error_12 = [ 7.758651e-04 ; 1.101385e-03 ; 1.817448e-03 ];
Tc_error_12  = [ 6.645811e-01 ; 6.187300e-01 ; 4.931219e-01 ];

%-- Image #13:
omc_13 = [ -1.919019e+00 ; -2.116536e+00 ; -5.941699e-01 ];
Tc_13  = [ -1.326919e+02 ; -1.435601e+02 ; 5.448016e+02 ];
omc_error_13 = [ 7.237312e-04 ; 1.090139e-03 ; 1.786810e-03 ];
Tc_error_13  = [ 5.967565e-01 ; 5.538371e-01 ; 4.475423e-01 ];

%-- Image #14:
omc_14 = [ -1.954383e+00 ; -2.124577e+00 ; -5.844155e-01 ];
Tc_14  = [ -1.235975e+02 ; -1.371428e+02 ; 4.909029e+02 ];
omc_error_14 = [ 6.811548e-04 ; 1.068394e-03 ; 1.749259e-03 ];
Tc_error_14  = [ 5.384990e-01 ; 4.985605e-01 ; 4.016646e-01 ];

%-- Image #15:
omc_15 = [ -2.110763e+00 ; -2.253834e+00 ; -4.948458e-01 ];
Tc_15  = [ -1.991404e+02 ; -1.345094e+02 ; 4.750400e+02 ];
omc_error_15 = [ 7.861861e-04 ; 1.000604e-03 ; 1.906498e-03 ];
Tc_error_15  = [ 5.281412e-01 ; 4.944591e-01 ; 4.329016e-01 ];

%-- Image #16:
omc_16 = [ 1.886909e+00 ; 2.336194e+00 ; -1.735757e-01 ];
Tc_16  = [ -1.593431e+01 ; -1.703338e+02 ; 6.955668e+02 ];
omc_error_16 = [ 1.080868e-03 ; 1.141775e-03 ; 2.373491e-03 ];
Tc_error_16  = [ 7.512690e-01 ; 6.820890e-01 ; 5.126739e-01 ];

%-- Image #17:
omc_17 = [ -1.612908e+00 ; -1.953394e+00 ; -3.473542e-01 ];
Tc_17  = [ -1.352328e+02 ; -1.389560e+02 ; 4.901887e+02 ];
omc_error_17 = [ 6.730108e-04 ; 1.029433e-03 ; 1.450974e-03 ];
Tc_error_17  = [ 5.315844e-01 ; 4.945585e-01 ; 3.560029e-01 ];

%-- Image #18:
omc_18 = [ -1.341750e+00 ; -1.692559e+00 ; -2.970118e-01 ];
Tc_18  = [ -1.853693e+02 ; -1.577998e+02 ; 4.412934e+02 ];
omc_error_18 = [ 7.723999e-04 ; 1.000083e-03 ; 1.145505e-03 ];
Tc_error_18  = [ 4.832921e-01 ; 4.508079e-01 ; 3.458155e-01 ];

%-- Image #19:
omc_19 = [ -1.925985e+00 ; -1.837926e+00 ; -1.440322e+00 ];
Tc_19  = [ -1.065657e+02 ; -7.957186e+01 ; 3.341594e+02 ];
omc_error_19 = [ 6.643736e-04 ; 1.171549e-03 ; 1.484621e-03 ];
Tc_error_19  = [ 3.750238e-01 ; 3.440403e-01 ; 3.243329e-01 ];

%-- Image #20:
omc_20 = [ 1.896147e+00 ; 1.593137e+00 ; 1.471912e+00 ];
Tc_20  = [ -1.438344e+02 ; -8.803623e+01 ; 3.961774e+02 ];
omc_error_20 = [ 1.237531e-03 ; 6.846320e-04 ; 1.333991e-03 ];
Tc_error_20  = [ 4.577417e-01 ; 4.100511e-01 ; 3.923654e-01 ];

