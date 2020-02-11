/*  Queue API (from previous project)
    Name: Ethan Chen
    Date Started: October 10, 2019

*/

import edu.princeton.cs.algs4.*;
import java.util.Iterator;


public class Queue<Item> { // FIFO (First-In First-Out) Data Structure

    Node front;

    public Queue() { // instantiates an empty queue

    }

    public Queue(Node initialNode) { // instantiates a queue with a starting node
        front = initialNode;
    }

    public Queue(Item item) { // instantiates a queue given a data value for the starting node
        front = new Node(item);
    }

    public void enqueue(Item item) { // inserts a node at the end of the queue
        Node newNode = new Node(item);

        if (front != null) {
            Node currentNode = front;
            while(currentNode.next != null) {
                currentNode = currentNode.next;
            }
            currentNode.setNext(newNode);
        } else {
            front = newNode;
        }
    }

    public boolean isEmpty() { // returns true if queue is empty, false otherwise
        if (front == null) {
            return true;
        } else {
            return false;
        }
    }

    public Item dequeue() { // removes the top node and returns its value, given that it exists
        Node temp;

        if(!isEmpty()) {
            if(front.next != null) {
                temp = front;
                front = front.next;
                return (Item) temp.data;
            } else {
                temp = front;
                front = null;
                return (Item) temp.data;
            }
        } else {
            return null;
        }
    }

    public int size() { // returns the number of nodes in the queue
        if (!isEmpty()) {
            int sizeCounter = 1;
            Node currentNode = front;
            while (currentNode.next != null) {
                currentNode = currentNode.next;
                sizeCounter++;
            }
            return sizeCounter;
        } else {
            return 0;
        }
    }
    @Override
    public String toString() { // returns the list as a string reading from the front of the queue to the end

        if(!isEmpty()) {
            Node currentNode = front;
            String queueString = "FRONT: " + front.data.toString();
            while(currentNode.next != null) {
                currentNode = currentNode.next;
                queueString = queueString + " => " + currentNode.data.toString();
            }
            return queueString;
        } else {
            return "Empty Queue";
        }
    }

    //Class Runner

    public static void main(String[] args) { // // Interactive Console User Interface to test Queue methods
        Queue firstQueue = new Queue();

        StdOut.println("Welcome to the queue Console Tester");
        StdOut.println();
        StdOut.println("Type 1 to queue up an item; 2 to dequeue the front of the queue, 3 to show the length, 4 to ask if it is empty, or 0 to exit");
        int userInput = StdIn.readInt();
        while(userInput != 0) {
            if(userInput == 1) {
                StdOut.println("What would you like to queue up to the Queue?");
                String input = StdIn.readString();
                firstQueue.enqueue(input);
                StdOut.println("You have added " + input + " to your Queue");
            } else if(userInput == 2) {
                Object popped = firstQueue.dequeue();
                if(popped == null) {
                    StdOut.println("We can't dequeue something that isn't there. Good try.");
                } else {
                    StdOut.println("You have removed " + popped + " from the Queue");
                }
            } else if(userInput == 3) {
                StdOut.println("Your queue is " + firstQueue.size() + " items long");
            } else if(userInput == 4) {
                StdOut.println("Is your queue empty? " + firstQueue.isEmpty());
            }
            StdOut.println();
            StdOut.println("Your current Queue is as follows:");
            StdOut.println(firstQueue.toString());
            StdOut.println();
            StdOut.println("Type 1 to queue up an item; 2 to dequeue the front of the queue, 3 to show the length, 4 to ask if it is empty, or 0 to exit");
            userInput = StdIn.readInt();

        }

        StdOut.println("Thank you for using the queue Console Tester");
        StdOut.println("Your final queue was: " + firstQueue.toString());

    }

}
