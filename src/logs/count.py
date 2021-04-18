#get file object reference to the file
filename = "C:\\Users\\smith\\Downloads\\BundleLogs-1618778958203\\var\\log\\web.stdout.log"
file = open(filename, "r")

#read content of file to string
data = file.read()

#get number of occurrences of the substring in the string
occurrences = data.count("Inside Controller.java, attempting to assemble file")

print('Number of occurrences of the word :', occurrences)
