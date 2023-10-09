
# makkefile begins

# define a variable for compiler flags (JFLAGS)
# define a variable for the compiler (JC)  
# define a variable for the Java Virtual Machine (JVM)

# define a variable for a parameter. When you run make, you could use:
# make run FILE="Algo.csv"

JFLAGS = -g
JC = javac
JVM= java 
FILE1=
FILE2=

.SUFFIXES: .java .class

.java.class: 
	$(JC) $(JFLAGS) Plagarism_Detector.java

CLASSES = \
	Plagarism_Detector.java

MAIN = Plagarism_Detector

default: classes

classes: $(CLASSES:.java=.class)

run: $(MAIN).class
	$(JVM) $(MAIN) $(FILE1) $(FILE2)

clean:
	$(RM) *.class

testPlagiarism:
	@echo "Testing plagiarism test cases in ../data/plagiarismXX"
	@for file in ../data/plagiarism*; do echo "Testing $$file"; ./$(TARGET) $$file/1.txt $$file/2.txt;done

testNonPlagiarism:
	@echo "Testing non-plagiarism test cases in ../data/okayXX"
	@for file in ../data/okay*; do echo "Testing $$file"; ./$(TARGET) $$file/1.txt $$file/2.txt;done

test: testPlagiarism testNonPlagiarism
