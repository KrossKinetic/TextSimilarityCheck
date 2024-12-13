# TextSimilarityCheck
TextSimilarityCheck is a simple program written in Java for comparing text files and checking if they were written by the same author (by using cosine similarity).
It represents the following skills:

1. Hash Tables    
2. Cosine Similarity    
3. File I/O    
4. Text Processing    
5. Algorithm Implementation    
6. Data Structures (ArrayList, HashMap, etc.) 

## Original Question
In published literature (and college essays), there will oftentimes come up the issue of ghost-writing -- hiring someone to write a piece for you and then putting your own name on it to take credit. Most notably, this has been studied with Shakespeare’s works, which were under scrutiny for being too large in quantity for one person to write alone. In order to test this, there’s a rather simple method used to calculate the similarity of two texts, relying on the frequencies of certain words used.

In this assignment, you will be creating a program which iterates through a folder of text files and uses this method to determine whether or not each text’s author is the same as the author of another text. The basic outline of the method is that the program will read each file, tally up the number of occurrences of each word (within a certain range of frequency, explained below) using a hash table, and then use the cosine method (see below) between each pair of hash tables to determine whether or not they have the same author. This will then be presented to the user at the end of the sequence.

## Disclaimer
This repository contains projects completed as part of CSE214 at Stony Brook University. It is intended solely for personal learning and demonstration. Any use of this code for academic submissions or coursework is a violation of academic integrity policies. 

This code is provided "as is" for educational and demonstrative purposes only. Reuse for academic coursework or submissions is strictly prohibited.
