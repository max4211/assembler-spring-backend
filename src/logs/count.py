import re
import statistics
import matplotlib.pyplot as plt


# CONSTANTS
FILENAME = "C:\\Users\\smith\\Downloads\\BundleLogs-1619384191142\\var\\log\\web.stdout.log"
START_ASSEMBLY = "Inside Controller.java, attempting to assemble file"
FILE_LENGTH = "Assembling file of length"
CUSTOM_REGEX = "Loaded \d custom instructions"

# FUNTIONS
def countAssembles():
    file = open(FILENAME, "r")
    data = file.read()
    occurrences = data.count(START_ASSEMBLY)
    print('Total Assembles :', occurrences)

def printLineSummary(lineCounts):
    """Using log with assembly file line and count, compute metrics"""
    totalLines = sum(lineCounts)
    totalFiles = len(lineCounts)
    mean = totalLines / totalFiles
    median = statistics.median(lineCounts)
    print(f"Total Lines: {totalLines}\tTotal Files: {totalFiles}\tMean Lines: {round(mean, 2)}\tMedian Lines: {median}")
    
    fig = plt.figure(figsize =(10, 7))
    # Creating plot
    plt.boxplot(lineCounts)
    # show plot
    plt.show()

def printCustomSummary(customCounts):
    """Using log with assembly file line and count, compute metrics"""
    totalCustom = sum(customCounts)
    totalFiles = len(customCounts)
    mean = totalCustom / totalFiles
    median = statistics.median(customCounts)
    print(f"Total Custom: {totalCustom}\tTotal Files: {totalFiles}\tAverage Custom: {round(mean, 2)}\tMedian Custom: {median}")

def countFileLength():
    file = open(FILENAME, "r")
    lines = file.readlines()
    lineCounts =[]
    for line in lines:
        if FILE_LENGTH in line:
            substring = line[line.index(FILE_LENGTH):]
            words = substring.split()
            countString = words[len(words)-1]
            countInt = int(countString)
            lineCounts.append(countInt)

    # end for
    printLineSummary(lineCounts)

def countCustomInstructions():
    file = open(FILENAME, "r")
    lines = file.readlines()
    customCounts =[]
    for line in lines:
        if bool(re.search(CUSTOM_REGEX, line)):
            substring = line[line.index("Loaded"):]
            words = substring.split()
            countString = words[1]
            countInt = int(countString)
            customCounts.append(countInt)

    # end for
    printCustomSummary(customCounts)

countAssembles()
countFileLength()
countCustomInstructions()