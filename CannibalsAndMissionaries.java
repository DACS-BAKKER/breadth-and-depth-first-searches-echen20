import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.Iterator;

public class CannibalsAndMissionaries {

    private RiverState initialState;
    private RiverNode initialNode;
    private RiverNode solutionNode; // holds solution for later traversal
    private Stack<RiverState> riverStack;

    // private class RiverNode, holds reference to where it came from
    private class RiverNode {
        RiverState state;
        int maxPeople;
        RiverNode lastNode;

        public RiverNode(RiverState state, RiverNode lastNode) {
            this.state = state;
            this.lastNode = lastNode;
            this.maxPeople = state.maxPeople;
        }

    }

    // constructor

    public CannibalsAndMissionaries(int method, int people) {
        int[] initialArray = new int[3];
        initialArray[0] = people;
        initialArray[1] = people;
        initialArray[2] = 1;

        initialState = new RiverState(initialArray, people);
        initialNode = new RiverNode(initialState, null);
        if (method == 0) {
            solutionNode = breadthFirst();
        } else if (method == 1) {
            solutionNode = depthFirst();
        }
    }

    // breadth first search, very similar to A* algorithm for 8-Puzzle
    public RiverNode breadthFirst() {
        Queue<RiverNode> riverQueue = new Queue(); // queue that holds nodes
        riverQueue.enqueue(initialNode);
        while (!riverQueue.isEmpty()) {
            RiverNode currNode = riverQueue.dequeue(); // takes off front node
            if (currNode.state.isSolutionState()) { // solution
                return currNode;
            }
            for (RiverState nextState : currNode.state.moves()) {
                if (nextState != null) {
                    if (currNode.lastNode != null) {
                        if (nextState != currNode.lastNode.state) { // checks against last state
                            riverQueue.enqueue(new RiverNode(nextState, currNode)); // adds all next possible states to node
                        }
                    } else {
                        riverQueue.enqueue(new RiverNode(nextState, currNode));
                    }
                }
            }
        }
        return null;
    }

    // depth first search
    public RiverNode depthFirst() { // helper method
        Stack<RiverNode> riverStack = new Stack();
        riverStack.push(initialNode);
        return solveRiverNodeDepthFirstTry2(riverStack);
    }

    private RiverNode solveRiverNodeDepthFirstTry2(Stack<RiverNode> riverStack) {

        RiverState baseState = new RiverState(initialState.stateArray, initialState.maxPeople); // defines a base state
        RiverNode currNode = riverStack.peek();

        boolean firstMove; // checking in case it returns a null pointer exception when you compare to last state
        if (currNode.lastNode == null) {
            firstMove = true;
        } else {
            firstMove = false;
        }

        for (RiverState nextRiverState : currNode.state.moves()) { // goes into each next state and stacks

            if (nextRiverState != null) {

                RiverNode nextNode = new RiverNode(nextRiverState, currNode);

                if (!firstMove) { // needs to check against last state
                    if (!nextRiverState.equals(currNode.lastNode.state) && !nextRiverState.equals(baseState)) {
                        riverStack.push(nextNode);
                        if (nextRiverState.isSolutionState()) {
                            return nextNode;
                        }
                        solutionNode = solveRiverNodeDepthFirstTry2(riverStack); // run recursion on it
                        if (solutionNode != null) {
                            return solutionNode; // returns solution if it is found
                        }
                    }
                } else { // doesn't need to check against last state
                    if (!nextRiverState.equals(baseState)) {
                        riverStack.push(nextNode);
                        if (nextRiverState.isSolutionState()) {
                            return nextNode;
                        }
                        solutionNode = solveRiverNodeDepthFirstTry2(riverStack); // recursion
                        if (solutionNode != null) {
                            return solutionNode;
                        }
                    }
                }

            }
        }
        return null;
    }


    // gives iterable list of states that lead to solution
    public Iterable<RiverState> getSolution() {

        riverStack = new Stack();
        riverStack.push(solutionNode.state);

        while (solutionNode.lastNode != null) { // adds the solution path (which is backwards) to stack to put in correct order
            solutionNode = solutionNode.lastNode;
            riverStack.push(solutionNode.state);
        }

        // iterator class
        class RiverIterator implements Iterator<RiverState> {

            @Override
            public boolean hasNext() {
                if (!riverStack.isEmpty()) {
                    return true;
                }
                return false;
            }


            @Override
            public RiverState next() {
                return riverStack.pop();
            } // pop off stack to attain order

        }

        // iterable class
        class RiverIterable implements Iterable<RiverState> {
            @Override
            public Iterator<RiverState> iterator() {
                return new RiverIterator();
            }
        }

        return new RiverIterable();
    }


    // test client
    public static void main(String[] args) {

        StdOut.println("Hello, would you like breadth or depth first? 0: breadth, 1: depth");
        int x = StdIn.readInt();
        StdOut.println("How many people would you like to do this for? (<= 3)");
        int y = StdIn.readInt();

        Stopwatch timer = new Stopwatch();
        CannibalsAndMissionaries cam = new CannibalsAndMissionaries(x, y);
        StdOut.println("This process took " + timer.elapsedTime() + " seconds");
        StdOut.println();

        for (RiverState rs : cam.getSolution()) {
            StdOut.println(rs);
            StdOut.println();
        }
    }


    // my first try at doing a depth first search (realized later how inefficient it is), NOT USED IN CODE
    private RiverNode solveRiverNodeDepthFirstTry1(Stack<RiverNode> riverStack) { // recursive depth first search
        RiverNode solutionNode;
        RiverNode currNode = riverStack.pop(); // uses top item of stack
        RiverNode oneMissionary = new RiverNode(currNode.state.nextState(0), currNode); // all next possible states
        RiverNode twoMissionaries = new RiverNode(currNode.state.nextState(1), currNode);
        RiverNode oneCannibal = new RiverNode(currNode.state.nextState(2), currNode);
        RiverNode twoCannibals = new RiverNode(currNode.state.nextState(3), currNode);
        RiverNode oneAndOne = new RiverNode(currNode.state.nextState(4), currNode);

        RiverState failedState = new RiverState(new int[]{-1, -1, -1}, 0); // defines a failed state
        RiverState baseState = new RiverState(initialState.stateArray, initialState.maxPeople); // defines a base state

        boolean firstMove;
        if (currNode.lastNode == null) {
            firstMove = true;
        } else {
            firstMove = false;
        }

        // for every set of possible moves (as will be demonstrated on block below:
        if (!firstMove) { // checks if it is the first move. if it is, there is no need to check against the last node state
            if (!oneMissionary.state.equals(failedState) && !oneMissionary.state.equals(baseState) && !oneMissionary.state.equals(currNode.lastNode.state)) { // checks to see if it exists and does not return to beginning
                riverStack.push(oneMissionary);
                if (oneMissionary.state.isSolutionState()) { // if it is the solution, return it
                    return oneMissionary;
                }
                solutionNode = solveRiverNodeDepthFirstTry1(riverStack); // solve that lower set of the search
                if (solutionNode != null) { // if you find a value that isn't null, it must be the solution
                    return solutionNode; // return it
                }
            }
        } else {
            if (!oneMissionary.state.equals(failedState) && !oneMissionary.state.equals(baseState)) {
                riverStack.push(oneMissionary);
                if (oneMissionary.state.isSolutionState()) {
                    return oneMissionary;
                }
                solutionNode = solveRiverNodeDepthFirstTry1(riverStack);
                if (solutionNode != null) {
                    return solutionNode;
                }
            }
        }

        if (!firstMove) {
            if (!twoMissionaries.state.equals(failedState) && !twoMissionaries.state.equals(currNode.lastNode.state) && !twoMissionaries.state.equals(baseState)) {
                riverStack.push(twoMissionaries);
                if (twoMissionaries.state.isSolutionState()) {
                    return twoMissionaries;
                }
                solutionNode = solveRiverNodeDepthFirstTry1(riverStack);
                if (solutionNode != null) {
                    return solutionNode;
                }
            }
        } else {
            if (!twoMissionaries.state.equals(failedState) && !twoMissionaries.state.equals(baseState)) {
                riverStack.push(twoMissionaries);
                if (twoMissionaries.state.isSolutionState()) {
                    return twoMissionaries;
                }
                solutionNode = solveRiverNodeDepthFirstTry1(riverStack);
                if (solutionNode != null) {
                    return solutionNode;
                }
            }
        }

        if (!firstMove) {
            if (!oneCannibal.state.equals(failedState) && !oneCannibal.state.equals(currNode.lastNode.state) && !oneCannibal.state.equals(baseState)) {
                riverStack.push(oneCannibal);
                if (oneCannibal.state.isSolutionState()) {
                    return oneCannibal;
                }
                solutionNode = solveRiverNodeDepthFirstTry1(riverStack);
                if (solutionNode != null) {
                    return solutionNode;
                }
            }
        } else {
            if (!oneCannibal.state.equals(failedState) && !oneCannibal.state.equals(baseState)) {
                riverStack.push(oneCannibal);
                if (oneCannibal.state.isSolutionState()) {
                    return oneCannibal;
                }
                solutionNode = solveRiverNodeDepthFirstTry1(riverStack);
                if (solutionNode != null) {
                    return solutionNode;
                }
            }
        }

        if (!firstMove) {
            if (!twoCannibals.state.equals(failedState) && !twoCannibals.state.equals(currNode.lastNode.state) && !twoCannibals.state.equals(baseState)) {
                riverStack.push(twoCannibals);
                if (twoCannibals.state.isSolutionState()) {
                    return twoCannibals;
                }
                solutionNode = solveRiverNodeDepthFirstTry1(riverStack);
                if (solutionNode != null) {
                    return solutionNode;
                }
            }
        } else {
            if (!twoCannibals.state.equals(failedState) && !twoCannibals.state.equals(baseState)) {
                riverStack.push(twoCannibals);
                if (twoCannibals.state.isSolutionState()) {
                    return twoCannibals;
                }
                solutionNode = solveRiverNodeDepthFirstTry1(riverStack);
                if (solutionNode != null) {
                    return solutionNode;
                }
            }
        }

        if (!firstMove) {
            if (!oneAndOne.state.equals(failedState) && !oneAndOne.state.equals(currNode.lastNode.state) && !oneAndOne.state.equals(baseState)) {
                riverStack.push(oneAndOne);
                if (oneAndOne.state.isSolutionState()) {
                    return oneAndOne;
                }
                solutionNode = solveRiverNodeDepthFirstTry1(riverStack);
                if (solutionNode != null) {
                    return solutionNode;
                }
            }
        } else {
            if (!oneAndOne.state.equals(failedState) && !oneAndOne.state.equals(baseState)) {
                riverStack.push(oneAndOne);
                if (oneAndOne.state.isSolutionState()) {
                    return oneAndOne;
                }
                solutionNode = solveRiverNodeDepthFirstTry1(riverStack);
                if (solutionNode != null) {
                    return solutionNode;
                }
            }
        }

        return null;

    }
}
