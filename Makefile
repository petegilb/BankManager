all: clean build run

build:
	javac *.java

run:
	java Test

clean:
	-rm *.class