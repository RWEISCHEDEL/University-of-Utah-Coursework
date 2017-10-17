import numpy as np
import random as rand

def readInputFile(file):

	inputFile = open(file, "r")
	labels = []
	matrix = []

	for line in inputFile:
		values = line.split()
		labels.append(int(values[0]))
		temp = [0] * 69
		del values[0]
		for value in values:
			v = value.split(":")
			temp[int(v[0]) - 1] = float(v[1])

		matrix.append(temp)

	inputFile.close()

	outputMatrix = np.matrix(matrix)
	
	outputs = [outputMatrix, labels]

	return outputs


def simplePerceptron(matrix, labels, lRate, weight, b):
	#b = rand.uniform(-0.01, 0.01)
	# print 'B Value', b
	#weight = [None] * 69 

	#for i in range(0, len(weight)):
		#randVal = rand.uniform(-0.01, 0.01)
		#weight[i] = randVal

	#weight = np.matrix(weight)


	t_updates = 0

	for example, index in zip(matrix, labels):

		wT = np.transpose(weight)
		exampleMatrix = np.matrix(example)
		test = float(exampleMatrix.dot(wT))
		test = (test + b) * labels[index]
		if test < 0:
			nY = lRate * labels[index]
			tempMatrix = nY * exampleMatrix
			weight = np.add(weight, tempMatrix)
			b += nY
			t_updates += 1

	outputs = [weight, b, t_updates]

	return outputs


def dynamicPerceptron(matrix, labels, lRate, weight, b):
	t_updates = 0
	t_step = 0

	for example, index in zip(matrix, labels):
		wT = np.transpose(weight)
		exampleMatrix = np.matrix(example)
		test = float(exampleMatrix.dot(wT))
		test = (test + b) * labels[index]

		dlRate = lRate / (1 + t_step)

		if test < 0:
			nY = dlRate * labels[index]
			tempMatrix = nY * exampleMatrix
			weight = np.add(weight, tempMatrix)
			b += nY
			t_step += 1
			t_updates += 1

	outputs = [weight, b, t_updates, t_step]

	return outputs


def marginPerceptron(matrix, labels, lRate, marginVal, weight, b):
	t_updates = 0
	t_step = 0

	for example, index in zip(matrix, labels):
		wT = np.transpose(weight)
		exampleMatrix = np.matrix(example)
		test = float(exampleMatrix.dot(wT))
		test = (test + b) * labels[index]

		dlRate = lRate / (1 + t_step)

		if test < marginVal:
			nY = dlRate * labels[index]
			tempMatrix = nY * exampleMatrix
			weight = np.add(weight, tempMatrix)
			b += nY
			t_updates += 1

	outputs = [weight, b, t_updates, t_step]

	return outputs


def averagedPerceptron(matrix, labels, lRate, weight, b):

	t_updates = 0

	a_weight = [0] * 69
	a_bias =0

	for example, index in zip(matrix, labels):
		wT = np.transpose(weight)
		exampleMatrix = np.matrix(example)
		test = float(exampleMatrix.dot(wT))
		test = (test + b) * labels[index]

		if test < 0:
			nY = lRate * labels[index]
			tempMatrix = nY * exampleMatrix
			weight = np.add(weight, tempMatrix)
			b += nY
			t_updates += 1

		a_weight = np.add(a_weight, weight)
		a_bias += b

	outputs = [a_weight, a_bias, t_updates]

	return outputs


def aggressivePerceptron(matrix, labels, lRate, marginVal, weight, b):

	t_updates = 0

	for example, index in zip(matrix, labels):
		wT = np.transpose(weight)
		exampleMatrix = np.matrix(example)

		mod = float(exampleMatrix.dot(wT) + b)
		test = mod * labels[index]

		transposeMatrix = float(exampleMatrix.dot(np.transpose(exampleMatrix)))


		dlRate = float(marginVal - (labels[index] * mod) / float(transposeMatrix + 1))

		if test <= marginVal:
			#print(dlRate)
			#print(labels[index])
			nY = dlRate * labels[index]
			#print(nY)
			#print(exampleMatrix)
			tempMatrix = nY * exampleMatrix
			weight = np.add(weight, tempMatrix)
			b += nY
			t_updates += 1

	outputs = [weight, b, t_updates]

	return outputs

def testAccuracy(matrix, labels, weight, b):

	missed = 0

	for example, index in zip(matrix, labels):
		wT= np.transpose(weight)
		exampleMatrix = np.matrix(example)
		test = float(exampleMatrix.dot(wT))
		test = (test + b) * labels[index]

		if test < 0:
			missed += 1
	#print "Missed",missed
	missed = float(missed)
	length = float(len(labels))
	# print "Length", length
	a_val = (length - missed) / length
	# print a_val
	return a_val

def combineFiles(file1, file2, file3, file4):

	outputMatrix = np.vstack([file1[0], file2[0]])

	outputMatrix = np.vstack([outputMatrix, file3[0]])

	outputMatrix = np.vstack([outputMatrix, file4[0]])

	outputLabels = np.append(file1[1], file2[1])

	outputLabels = np.append(outputLabels, file3[1])

	outputLabels = np.append(outputLabels, file4[1])

	output = [outputMatrix, outputLabels]

	return output


def experiment1():

	training0 = readInputFile("./Dataset/CVSplits/training00.data")
	training1 = readInputFile("./Dataset/CVSplits/training01.data")
	training2 = readInputFile("./Dataset/CVSplits/training02.data")
	training3 = readInputFile("./Dataset/CVSplits/training03.data")
	training4 = readInputFile("./Dataset/CVSplits/training04.data")

	crossVal0 = combineFiles(training1, training2, training3, training4)

	crossVal1 = combineFiles(training0, training2, training3, training4)

	crossVal2 = combineFiles(training0, training1, training3, training4)

	crossVal3 = combineFiles(training0, training1, training2, training4)

	crossVal4 = combineFiles(training0, training1, training2, training3)

	allTraining = [training0, crossVal0, training1, crossVal1, training2, crossVal2, training3, crossVal3, training4, crossVal4]

	# First Iterations 

	hParameters = [1, 0.1, 0.01]

	epochVal = 10

	bestSimpleAcc = 0
	bestSimpleHP = 0

	print("\n")
	print("Begin The Experiments!")
	print("Experiment 1: Find Best Hyper-Parameter")
	print("Find Best Hyper-Parameter Using Simple Perceptron")
	print("\n")

	for hP in hParameters:

		totalAcc = 0;
		total = 0;

		for i in range(5):

			b = rand.uniform(-0.01, 0.01)

			weight = [None] * 69 

			for r in range(0, len(weight)):
				randVal = rand.uniform(-0.01, 0.01)
				weight[r] = randVal

			weight = np.matrix(weight)

			for j in range(epochVal):

				output = simplePerceptron(allTraining[2 * i + 1][0], allTraining[2 * i + 1][1], hP, weight, b)
				acc = testAccuracy(allTraining[2 * i][0], allTraining[2 * i][1], output[0], output[1])

				weight = output[0]
				b = output[1]

			totalAcc += acc
			total += 1


		averageAcc = totalAcc/total

		if averageAcc > bestSimpleAcc:
			bestSimpleAcc = averageAcc
			bestSimpleHP = hP
		
		print 'Current Simple Hyper-Parameter Value:', hP
		print 'Average Accuracy:', averageAcc

	print("\n")
	print 'Best Simple Hyper-Parameter Value:', bestSimpleHP
	print 'Best Accuracy:', bestSimpleAcc
	print("\n")


	bestDynAcc = 0
	bestDynHP = 0

	print("Find Best Hyper-Parameter Using Dynamic Perceptron")

	for hP in hParameters:

		totalAcc = 0
		total = 0

		for i in range(5):

			b = rand.uniform(-0.01, 0.01)

			weight = [None] * 69 

			for r in range(0, len(weight)):
				randVal = rand.uniform(-0.01, 0.01)
				weight[r] = randVal

			weight = np.matrix(weight)

			for j in range(epochVal):

				output = dynamicPerceptron(allTraining[2 * i + 1][0], allTraining[2 * i + 1][1], hP, weight, b)
				acc = testAccuracy(allTraining[2 * i][0], allTraining[2 * i][1], output[0], output[1])

				weight = output[0]
				b = output[1]

			totalAcc += acc
			total += 1


		averageAcc = totalAcc/total

		if averageAcc > bestDynAcc:
			bestDynAcc = averageAcc
			bestDynHP = hP
		
		print 'Current Dynamic Hyper-Parameter Value:', hP
		print 'Average Accuracy:', averageAcc

	print("\n")
	print 'Best Dynamic Hyper-Parameter Value:', bestDynHP
	print 'Best Accuracy:', bestDynAcc
	print("\n")



	bestMarginAcc = 0
	bestMarginHP = 0
	bestMarginMargin = 0

	print("Find Best Hyper-Parameter Using Margin Perceptron")

	for hP in hParameters:

		totalAcc = 0
		total = 0

		for m in hParameters:

			for i in range(5):

				b = rand.uniform(-0.01, 0.01)

				weight = [None] * 69 

				for r in range(0, len(weight)):
					randVal = rand.uniform(-0.01, 0.01)
					weight[r] = randVal

				weight = np.matrix(weight)

				for j in range(epochVal):

					output = marginPerceptron(allTraining[2 * i + 1][0], allTraining[2 * i + 1][1], hP, m, weight, b)
					acc = testAccuracy(allTraining[2 * i][0], allTraining[2 * i][1], output[0], output[1])

					weight = output[0]
					b = output[1]

				totalAcc += acc
				total += 1

			averageAcc = totalAcc/total


			if averageAcc > bestMarginAcc:
				bestMarginAcc = averageAcc
				bestMarginHP = hP
				bestMarginMargin = m

			print 'Current Margin Hyper-Parameter Value:', hP
			print 'Current Margin Margin Value:', m
			print 'Average Accuracy:', averageAcc

	print("\n")
	print 'Best Margin Hyper-Parameter Value:', bestMarginHP
	print 'Best Margin:', bestMarginMargin
	print 'Best Accuracy:', bestMarginAcc
	print("\n")


	bestAvgAcc = 0
	bestAvgHP = 0

	print("Find Best Hyper-Parameter Using Average Perceptron")

	for hP in hParameters:

		totalAcc = 0
		total = 0

		for i in range(5):

			b = rand.uniform(-0.01, 0.01)

			weight = [None] * 69 

			for r in range(0, len(weight)):
				randVal = rand.uniform(-0.01, 0.01)
				weight[r] = randVal

			weight = np.matrix(weight)

			for j in range(epochVal):

				output = averagedPerceptron(allTraining[2 * i + 1][0], allTraining[2 * i + 1][1], hP, weight, b)
				acc = testAccuracy(allTraining[2 * i][0], allTraining[2 * i][1], output[0], output[1])

				weight = output[0]
				b = output[1]

			totalAcc += acc
			total += 1


		averageAcc = totalAcc/total

		if averageAcc > bestAvgAcc:
			bestAvgAcc = averageAcc
			bestAvgHP = hP
		
		print 'Current Average Hyper-Parameter Value:', hP
		print 'Average Accuracy:', averageAcc

	print("\n")
	print 'Best Average Hyper-Parameter Value:', bestAvgHP
	print 'Best Accuracy:', bestAvgAcc
	print("\n")


	bestAggressiveAcc = 0
	bestAggressiveHP = 0
	bestAggressiveMargin = 0

	print("Find Best Hyper-Parameter Using Aggressive Perceptron")

	for hP in hParameters:

		totalAcc = 0
		total = 0

		for m in hParameters:

			for i in range(5):

				b = rand.uniform(-0.01, 0.01)

				weight = [None] * 69 

				for r in range(0, len(weight)):
					randVal = rand.uniform(-0.01, 0.01)
					weight[r] = randVal

				weight = np.matrix(weight)

				for j in range(epochVal):

					output = aggressivePerceptron(allTraining[2 * i + 1][0], allTraining[2 * i + 1][1], hP, m, weight, b)
					acc = testAccuracy(allTraining[2 * i][0], allTraining[2 * i][1], output[0], output[1])

					weight = output[0]
					b = output[1]

				totalAcc += acc
				total += 1

			averageAcc = totalAcc/total


			if averageAcc > bestAggressiveAcc:
				bestAggressiveAcc = averageAcc
				bestAggressiveHP = hP
				bestAggressiveMargin = m

			print 'Current Aggressive Hyper-Parameter Value:', hP
			print 'Current Aggressive Margin Value:', m
			print 'Average Accuracy:', averageAcc

	print("\n")
	print 'Best Aggressive Hyper-Parameter Value:', bestAggressiveHP
	print 'Best Margin:', bestAggressiveMargin
	print 'Best Accuracy:', bestAggressiveAcc
	print("\n")

	outputList = [bestSimpleHP, bestDynHP, bestMarginHP, bestMarginMargin, bestAvgHP, bestAggressiveHP, bestAggressiveMargin]

	return outputList

def experiment2(exp1Output):

	outputTrain = readInputFile("./Dataset/phishing.dev")

	outputTest = readInputFile("./Dataset/phishing.test")

	bestOverallVariantAcc = 0
	bestOverallVariant = ""
	
	print("Begin Experiment 2: Find Best Variant")

	epochVal = 20

	print("Find Best Simple Perceptron Epoch Value")

	bestSimpleEpoch = -1;
	bestSimpleEpochAcc = 0;
	simpleUpdates = 0

	b = rand.uniform(-0.01, 0.01)

	weight = [None] * 69 

	for r in range(0, len(weight)):
		randVal = rand.uniform(-0.01, 0.01)
		weight[r] = randVal

	weight = np.matrix(weight)

	for i in range(epochVal):
		output = simplePerceptron(outputTrain[0], outputTrain[1], exp1Output[0], weight, b)
		acc = testAccuracy(outputTest[0], outputTest[1], output[0], output[1])

		weight = output[0]
		b = output[1]
			
		epoch = i + 1;
			
		if acc > bestSimpleEpochAcc:
			bestSimpleEpoch = epoch
			bestSimpleEpochAcc = acc

		print 'Simple Perceptron Epoch Accuracy:', acc
		print 'Simple Perceptron Epoch:', epoch

	print("\n")
	print 'Best Simple Epoch Value:', bestSimpleEpoch
	print 'Best Accuracy:', bestSimpleEpochAcc
	print("\n")

	if bestSimpleEpochAcc > bestOverallVariantAcc:
		bestOverallVariantAcc = bestSimpleEpochAcc
		bestOverallVariant = "Simple"
		bestOverallEpoch = bestSimpleEpoch


	print("Find Best Dynamic Perceptron Epoch Value")

	bestDynEpoch = -1;
	bestDynEpochAcc = 0;
	dynUpdates = 0

	b = rand.uniform(-0.01, 0.01)

	weight = [None] * 69 

	for r in range(0, len(weight)):
		randVal = rand.uniform(-0.01, 0.01)
		weight[r] = randVal

	weight = np.matrix(weight)

	for i in range(epochVal):
		output = dynamicPerceptron(outputTrain[0], outputTrain[1], exp1Output[1], weight, b)
		dynUpdates += output[2]
		acc = testAccuracy(outputTest[0], outputTest[1], output[0], output[1])

		weight = output[0]
		b = output[1]
			
		epoch = i + 1;
			
		if acc > bestDynEpochAcc:
			bestDynEpoch = epoch
			bestDynEpochAcc = acc

		print 'Dynamic Perceptron Epoch Accuracy:', acc
		print 'Dynamic Perceptron Epoch:', epoch

	print("\n")
	print 'Best Dynamic Epoch Value:', bestDynEpoch
	print 'Best Accuracy:', bestDynEpochAcc
	print 'Total Training Updates:', dynUpdates
	print("\n")

	if bestDynEpochAcc > bestOverallVariantAcc:
		bestOverallVariantAcc = bestDynEpochAcc
		bestOverallVariant = "Dynamic"
		bestOverallEpoch = bestDynEpoch


	print("Find Best Margin Perceptron Epoch Value")

	bestMarginEpoch = -1;
	bestMarginEpochAcc = 0;
	marginUpdates = 0

	b = rand.uniform(-0.01, 0.01)

	weight = [None] * 69 

	for r in range(0, len(weight)):
		randVal = rand.uniform(-0.01, 0.01)
		weight[r] = randVal

	weight = np.matrix(weight)

	for i in range(epochVal):
		output = marginPerceptron(outputTrain[0], outputTrain[1], exp1Output[2], exp1Output[3], weight, b)
		marginUpdates += output[2]
		acc = testAccuracy(outputTest[0], outputTest[1], output[0], output[1])

		weight = output[0]
		b = output[1]
			
		epoch = i + 1;
			
		if acc > bestMarginEpochAcc:
			bestMarginEpoch = epoch
			bestMarginEpochAcc = acc

		print 'Margin Perceptron Epoch Accuracy:', acc
		print 'Margin Perceptron Epoch:', epoch

	print("\n")
	print 'Best Margin Epoch Value:', bestMarginEpoch
	print 'Best Accuracy:', bestMarginEpochAcc
	print 'Total Training Updates:', marginUpdates
	print("\n")

	if bestMarginEpochAcc > bestOverallVariantAcc:
		bestOverallVariantAcc = bestMarginEpochAcc
		bestOverallVariant = "Margin"
		bestOverallEpoch = bestMarginEpoch


	print("Find Best Average Perceptron Epoch Value")

	bestAvgEpoch = -1;
	bestAvgEpochAcc = 0;
	avgUpdates = 0

	b = rand.uniform(-0.01, 0.01)

	weight = [None] * 69 

	for r in range(0, len(weight)):
		randVal = rand.uniform(-0.01, 0.01)
		weight[r] = randVal

	weight = np.matrix(weight)

	for i in range(epochVal):
		output = averagedPerceptron(outputTrain[0], outputTrain[1], exp1Output[4], weight, b)
		avgUpdates += output[2]
		acc = testAccuracy(outputTest[0], outputTest[1], output[0], output[1])

		weight = output[0]
		b = output[1]
			
		epoch = i + 1;
			
		if acc > bestAvgEpochAcc:
			bestAvgEpoch = epoch
			bestAvgEpochAcc = acc

		print 'Average Perceptron Epoch Accuracy:', acc
		print 'Average Perceptron Epoch:', epoch

	print("\n")
	print 'Best Average Epoch Value:', bestAvgEpoch
	print 'Best Accuracy:', bestAvgEpochAcc
	print 'Total Training Updates:', avgUpdates
	print("\n")

	if bestAvgEpochAcc > bestOverallVariantAcc:
		bestOverallVariantAcc = bestAvgEpochAcc
		bestOverallVariant = "Average"
		bestOverallEpoch = bestAvgEpoch



	print("Find Best Aggressive Perceptron Epoch Value")

	bestAggEpoch = -1;
	bestAggEpochAcc = 0;
	aggUpdates = 0

	b = rand.uniform(-0.01, 0.01)

	weight = [None] * 69 

	for r in range(0, len(weight)):
		randVal = rand.uniform(-0.01, 0.01)
		weight[r] = randVal

	weight = np.matrix(weight)

	for i in range(epochVal):
		output = aggressivePerceptron(outputTrain[0], outputTrain[1], exp1Output[5], exp1Output[6], weight, b)
		aggUpdates += output[2]
		acc = testAccuracy(outputTest[0], outputTest[1], output[0], output[1])

		weight = output[0]
		b = output[1]
			
		epoch = i + 1;
			
		if acc > bestAggEpochAcc:
			bestAggEpoch = epoch
			bestAggEpochAcc = acc

		print 'Aggressive Perceptron Epoch Accuracy:', acc
		print 'Aggressive Perceptron Epoch:', epoch

	print("\n")
	print 'Best Aggressive Epoch Value:', bestAggEpoch
	print 'Best Accuracy:', bestAggEpochAcc
	print 'Total Training Updates:', aggUpdates
	print("\n")

	if bestAggEpochAcc > bestOverallVariantAcc:
		bestOverallVariantAcc = bestAggEpochAcc
		bestOverallVariant = "Aggressive"
		bestOverallEpoch = bestAggEpoch

	outputList = [bestOverallEpoch, bestOverallVariantAcc, bestOverallVariant]

	return outputList


def experiment3(exp1Output, exp2Output):

	outputTrain = readInputFile("./Dataset/phishing.train")

	outputTest = readInputFile("./Dataset/phishing.test")

	b = rand.uniform(-0.01, 0.01)

	weight = [None] * 69 

	for r in range(0, len(weight)):
		randVal = rand.uniform(-0.01, 0.01)
		weight[r] = randVal

	weight = np.matrix(weight)

	print 'Best Variant:', exp2Output[2]

	output = []

	if exp2Output[2] == "Simple":
		for i in range(exp2Output[0]):
			print "Simple HyperParameter:", exp1Output[0]
			print "Simple Epoch Count:", exp2Output[0]
			output = simplePerceptron(outputTrain[0], outputTrain[1], exp1Output[0], weight, b)
	elif exp2Output[2] == "Dynamic":
		for i in range(exp2Output[0]):
			print "Dynamic HyperParameter:", exp1Output[1]
			print "Dynamic Epoch Count:", exp2Output[0]
			output = dynamicPerceptron(outputTrain[0], outputTrain[1], exp1Output[1], weight, b)
	elif exp2Output[2] == "Margin":
		for i in range(exp2Output[0]):
			print "Margin HyperParameter:", exp1Output[2]
			print "Margin Margin:", exp1Output[3]
			print "Margin Epoch Count:", exp2Output[0]
			output = marginPerceptron(outputTrain[0], outputTrain[1], exp1Output[2], exp1Output[3], weight, b)
	elif exp2Output[2] == "Average":
		for i in range(exp2Output[0]):
			print "Average HyperParameter:", exp1Output[4]
			print "Average Epoch Count:", exp2Output[0]
			output = averagedPerceptron(outputTrain[0], outputTrain[1], exp1Output[4], weight, b)
	else:
		for i in range(exp2Output[0]):
			print "Aggressive HyperParameter:", exp1Output[5]
			print "Aggressive Margin:", exp1Output[6]
			print "Aggressive Epoch Count:", exp2Output[0]
			output = aggressivePerceptron(outputTrain[0], outputTrain[1], exp1Output[5], exp1Output[6], weight, b)

	acc = testAccuracy(outputTest[0], outputTest[1], output[0], output[1])
	
	print 'Best Variant Accuracy:', acc









exp1Output = experiment1()

exp2Output = experiment2(exp1Output)

experiment3(exp1Output, exp2Output)




