# Clean up the directory and prepare for compiling the project
rm -f sources.txt
find ./src -name '*.java' > sources.txt
rm -rf ./compile
mkdir ./compile
# Compile the project
javac -d ./compile -classpath ./compile/mp1.jar @sources.txt
# Start the server
# Use locally
#java -classpath ./compile:./compile/mp1.jar ./src/SocketMultipleServer.java
# Use on VM
java -classpath ./compile:./compile/mp1.jar SocketMultipleServer