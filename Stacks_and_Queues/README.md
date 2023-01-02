Expandable array stack and expandable queue stack implementations by Aaron Floreani. <br /> <br />
Stacks and queues are linear types of data structures. Stacks follow the LIFO (Last-In-First-Out) order, while a queue follows FIFO (First Out In First Out) order. In this implementation each stack and queue both start as arrays with a capacity of 16. Then, when they fill up, the contents are copied to a new array that is twice the size. If the contents of the array get too small (1/4 of the capacity), then it shrinks to half the size.  <br />