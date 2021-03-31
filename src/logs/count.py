#get file object reference to the file
file = open("web.stdout.log", "r")

#read content of file to string
data = file.read()

#get number of occurrences of the substring in the string
occurrences = data.count("Inside Controller.java, attempting to assemble file")

print('Number of occurrences of the word :', occurrences)
