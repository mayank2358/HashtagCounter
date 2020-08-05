# HashtagCounter
The goal of this project is to implement a system to find the n most popular hashtags appeared on social media such as Facebook or Twitter. For the scope of this project hashtags are taken as an input file. Basic idea for the implementation is to use a max priority structure to find out the most popular hashtags.

The project uses the following data structure. 
1. Max Fibonacci heap: Use to keep track of the frequencies of Hashtags. 
2. Hash table(Hash Map in java) : Key for the hash table is hashtag and value is pointer to the corresponding node in the Fibonacci heap. 

 The project is written in JAVA. I have implemented Fibonacci Heap in java and stored the address of all the nodes in a Hash Map (Built in Data Structure). I have written the project using JAVA without any external in build data structure.  Max Fibonacci heap is required because it has better theoretical bounds for increase key operation.
 
A Fibonacci heap is a data structure for priority queue operations, consisting of a collection of heap-ordered trees. It has a better amortized running time than many other priority queue data structures including the binary heap and binomial heap. Michael L. Fredman and Robert E. Tarjan developed Fibonacci heaps in 1984 and published them in a scientific journal in 1987. They named Fibonacci heaps after the Fibonacci numbers, which are used in their running time analysis.[1]


Fibonacci Max Heap
Amortised Complexity
Space	O(1)
Search	O(1)
Insert	O(log n)
Delete	O(1)
Find Max	O(1)
Delete Max	O(log n)
Increase Key	O(1)
Merge	O(1)
 
WORKING ENVIRONMENT

HARDWARE REQUIREMENT
Hard Disk space: 4 GB minimum Memory: 512 MB
CPU: x86
OPERATING SYSTEM
LINUX/UNIX/MAC OS(If using other OS make command wont work) 
COMPILER
Javac


