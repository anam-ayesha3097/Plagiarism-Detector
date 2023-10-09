Plagiarism Detector - Anam Ayesha Shaikh

To detect plagiarism within the files with the best case time complexity.

To implement plagiarism detection I have implemented KMP(Knuth-Morris-Pratt) Algorithm a string matching algorithm to achieve minimum time-complexity.


KMP algorithm is used to find a "Pattern" in a "Text". This algorithm campares character by character from left to right. 
But whenever a mismatch occurs, it uses a preprocessed table called "Prefix Table"(LPS table) to skip characters comparison while matching.

So whenever there is a mismatch we don't need to start both i and j index. We just check for the LPS table value of the jth index and move 
j to the index specified in the table. Index i continues to move and thus the pattern matching is done when we reach to the last element in the table.

KMP in this project PlagiarismDetector is implemented on words by pre-processing the words in the file.

Pre-processing is done by removing all the special characters, numbers and stop words removal.
Also, all the characters are made to lower-case value.

**Steps to run the PlagiarismDetector using MakeFile:

Note: Plagiarism Detector JAVA File is named as PlagiarismDetector_40205690.java cause we cannot start the name of the class with a number in JAVA.

1.   Make sure to have make installed on your system.
2.   Navigate to the directory from command prompt and execute MakeFile by passing two parameters as FILE1 and FILE2.

make command example:

>make FILE1="file_path1" 
	 FILE2="file_path2" run

**Steps to run the PlagiarismDetector using IDE:

1. Setup the dataset file1 and file2 paths in IDE Run Configurations (Can setup relative paths)
2. Run the Plagiarism Detector


3. Ouput 0 - Not Plagiarised

   Output 1- Plagiarised

KMP Algorithm achieves a linear timecomplexity of O(n+m) in both in worst and best case. 

This tool correctly analyze the files of size (<=500 KB) of time not more than 5 seconds where the maximum time taken to check 460 KB file was 1.022 seconds.






