Name: Ethan Chen
Project: Cannibals and Missionaries
Date Completed: February 10, 2020


Project Files:
CannibalsAndMissionaries - contains a completed C&M file solver with breadth and depth first search implementations. It also contains private class RiverNode
RiverState - contains code for RiverState which holds the array for the problem as well as knowing how to find next moves
CMGraphics - my graphics representation of the problem, with red representing cannibals and black representing missionaries, and blue as boat


Extra Files:
Node - A node file
Queue - A queue file
Stack - A stack file


Analysis:
For this particular problem, comparing the average time to complete the Cannibals and Missionaries problem for the breadth-first
vs. depth-first search, what I found was :

(tested for 3 cannibals and 3 missionaries, which is the max solvable)
Depth-first: ~0.05 seconds
Breadth-first: ~2.25 seconds

For this particular problem where it is not possible to exceed 3, it favors a depth-first search more heavily because
there are not to many paths to be explored to find the solution. Whereas the breadth-first search must expand outward and
check every possible path equally, a depth first follows one until it either ends or succeeds. If this problem were solvable with
5 or 6 or even 20 of each, likely it would begin to favor a breadth-first much more, and more reliably because it will always find the shortest
solution whereas a depth first may not.


Notes:
The most difficult part of this project was most certainly the graphics. I have never tackled a graphics program utilizing
movement by myself in the project before, and it required a fair amount of experimentation and painful trial and error. Even
after finally getting the hang of the coding aspect, finding the logic to move very particular blocks to to the other side
proved itself a challenge. After 4 hours the night before it is due, I am proud to say I have finally done it.
