/*  RiverState Class
    Name: Ethan Chen
    Date Started: February 3, 2020

*/


import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class RiverState { // holds the river state array

    // idea for this fumbled across when looking at the wikipedia page description of the problem
    int[] stateArray; // represents all 3 in one object in order {missionaries, cannibals, river side} with initial state {3, 3, 1} (for 3 people) and goal {0, 0, 0}
    int maxPeople; // total num of cannibals or missionaries, in most cases 3

    // constructor
    public RiverState(int[] state, int maxPeople) {
        this.stateArray = state;
        this.maxPeople = maxPeople;
    }

    // is the solution state?
    public boolean isSolutionState() {
        for (int x = 0; x < stateArray.length; x++) {
            if (stateArray[x] != 0) {
                return false;
            }
        }
        return true;
    }


    // an iterable list of the next moves (doesn't check for collisions)
    public Iterable<RiverState> moves() {

        // iterator class

        class RiverIterator implements Iterator<RiverState> {

            int move = 0;

            @Override
            public boolean hasNext() {
                if (move > 4) {
                    return false;
                }
                return true;
            }


            // goes through all possible moves, very similar to nextBoard from 8 puzzle
            // successful riverStates (ones where there are never more cannibals than missionaries):
            // all missionaries on one side or cannibals = missionaries
            // cannibals or missionaries can never exceed maxPeople or go below 0, both fails
            @Override
            public RiverState next() {

                int[] nextState = new int[3];
                for (int x = 0; x < stateArray.length; x++) {
                    nextState[x] = stateArray[x];
                }

                if (move == 0) {
                    if (stateArray[2] == 0) { // boat on other side
                        nextState[0] += 1; // move people back to initial side
                        nextState[2] += 1;
                    } else { // boat on end side
                        nextState[0] -= 1; // move people to other side
                        nextState[2] -= 1;
                    }
                    if (nextState[0] <= maxPeople && nextState[0] >= 0 && nextState[1] <= maxPeople && nextState[1] >= 0) { // checks all conditions listed above

                        if (nextState[0] == maxPeople || nextState[0] == 0 || nextState[0] == nextState[1]) {
                            move++;
                            return new RiverState(nextState, maxPeople); // returns if all possible, moves to next move
                        }

                    }
                    for (int x = 0; x < stateArray.length; x++) {
                        nextState[x] = stateArray[x]; // reset the stateArray so it can be used for the next move
                    }
                    move++;
                }

                // PROCESS SAME AS ABOVE, JUST FOR DIFFERENT MOVE
                if (move == 1) {
                    if (stateArray[2] == 0) {
                        nextState[0] += 2;
                        nextState[2] += 1;
                    } else {
                        nextState[0] -= 2;
                        nextState[2] -= 1;
                    }
                    if (nextState[0] <= maxPeople && nextState[0] >= 0 && nextState[1] <= maxPeople && nextState[1] >= 0) {

                        if (nextState[0] == maxPeople || nextState[0] == 0 || nextState[0] == nextState[1]) {
                            move++;
                            return new RiverState(nextState, maxPeople);
                        }

                    }
                    for (int x = 0; x < stateArray.length; x++) {
                        nextState[x] = stateArray[x];
                    }
                    move++;
                }

                if (move == 2) {
                    if (stateArray[2] == 0) {
                        nextState[1] += 1;
                        nextState[2] += 1;
                    } else {
                        nextState[1] -= 1;
                        nextState[2] -= 1;
                    }
                    if (nextState[0] <= maxPeople && nextState[0] >= 0 && nextState[1] <= maxPeople && nextState[1] >= 0) {

                        if (nextState[0] == maxPeople || nextState[0] == 0 || nextState[0] == nextState[1]) {
                            move++;
                            return new RiverState(nextState, maxPeople);
                        }

                    }
                    for (int x = 0; x < stateArray.length; x++) {
                        nextState[x] = stateArray[x];
                    }
                    move++;
                }

                if (move == 3) {
                    if (stateArray[2] == 0) {
                        nextState[1] += 2;
                        nextState[2] += 1;
                    } else {
                        nextState[1] -= 2;
                        nextState[2] -= 1;
                    }
                    if (nextState[0] <= maxPeople && nextState[0] >= 0 && nextState[1] <= maxPeople && nextState[1] >= 0) {

                        if (nextState[0] == maxPeople || nextState[0] == 0 || nextState[0] == nextState[1]) {
                            move++;
                            return new RiverState(nextState, maxPeople);
                        }

                    }
                    for (int x = 0; x < stateArray.length; x++) {
                        nextState[x] = stateArray[x];
                    }
                    move++;
                }

                if (move == 4) {
                    if (stateArray[2] == 0) {
                        nextState[0] += 1;
                        nextState[1] += 1;
                        nextState[2] += 1;
                    } else {
                        nextState[0] -= 1;
                        nextState[1] -= 1;
                        nextState[2] -= 1;
                    }
                    if (nextState[0] <= maxPeople && nextState[0] >= 0 && nextState[1] <= maxPeople && nextState[1] >= 0) {

                        if (nextState[0] == maxPeople || nextState[0] == 0 || nextState[0] == nextState[1]) {
                            move++;
                            return new RiverState(nextState, maxPeople);
                        }

                    }
                    move++;
                }

                return null;
            }

        }

        // iterable class
        class RiverIterable implements Iterable<RiverState> {
            @Override
            public Iterator<RiverState> iterator() {
                return new RiverIterator();
            }
        }

        return new

                RiverIterable();
    }

    // compares state arrays to declare equivalence
    public boolean equals(RiverState comparedState) {
        for (int x = 0; x < 3; x++) {
            if (stateArray[x] != comparedState.stateArray[x]) {
                return false;
            }
        }
        return true;
    }

    // toString method
    @Override
    public String toString() {
        String riverString = "";
        riverString += "Missionaries: " + stateArray[0] + "\n";
        riverString += "Cannibals: " + stateArray[1] + "\n";
        riverString += "Boat side: " + stateArray[2] + "\n";
        return riverString;
    }


    // for the depth first search, returns the next possible move depending on parameter value
    //NOT USING THIS, USED IN DEPTHFIRSTSEARCHTRY1 BUT NEVER UTILIZED IN CLEANED CODE
    public RiverState nextState(int move) {
        int[] nextState = new int[3];
        for (int x = 0; x < stateArray.length; x++) {
            nextState[x] = stateArray[x];
        }

        if (move == 0) {
            if (stateArray[2] == 0) {
                nextState[0] += 1;
                nextState[2] += 1;
            } else {
                nextState[0] -= 1;
                nextState[2] -= 1;
            }
            if (nextState[0] <= maxPeople && nextState[0] >= 0 && nextState[1] <= maxPeople) {

                if (nextState[0] == maxPeople || nextState[0] == 0 || nextState[0] == nextState[1]) {
                    return new RiverState(nextState, maxPeople);
                }

            }
        }

        if (move == 1) {
            if (stateArray[2] == 0) {
                nextState[0] += 2;
                nextState[2] += 1;
            } else {
                nextState[0] -= 2;
                nextState[2] -= 1;
            }
            if (nextState[0] <= maxPeople && nextState[0] >= 0 && nextState[1] <= maxPeople) {

                if (nextState[0] == maxPeople || nextState[0] == 0 || nextState[0] == nextState[1]) {
                    return new RiverState(nextState, maxPeople);
                }

            }
        }

        if (move == 2) {
            if (stateArray[2] == 0) {
                nextState[1] += 1;
                nextState[2] += 1;
            } else {
                nextState[1] -= 1;
                nextState[2] -= 1;
            }
            if (nextState[0] <= maxPeople && nextState[0] >= 0 && nextState[1] <= maxPeople) {

                if (nextState[0] == maxPeople || nextState[0] == 0 || nextState[0] == nextState[1]) {
                    return new RiverState(nextState, maxPeople);
                }

            }
        }

        if (move == 3) {
            if (stateArray[2] == 0) {
                nextState[1] += 2;
                nextState[2] += 1;
            } else {
                nextState[1] -= 2;
                nextState[2] -= 1;
            }
            if (nextState[0] <= maxPeople && nextState[0] >= 0 && nextState[1] <= maxPeople) {

                if (nextState[0] == maxPeople || nextState[0] == 0 || nextState[0] == nextState[1]) {
                    return new RiverState(nextState, maxPeople);
                }

            }
        }

        if (move == 4) {
            if (stateArray[2] == 0) {
                nextState[0] += 1;
                nextState[1] += 1;
                nextState[2] += 1;
            } else {
                nextState[0] -= 1;
                nextState[1] -= 1;
                nextState[2] -= 1;
            }
            if (nextState[0] <= maxPeople && nextState[0] >= 0 && nextState[1] <= maxPeople) {

                if (nextState[0] == maxPeople || nextState[0] == 0 || nextState[0] == nextState[1]) {
                    return new RiverState(nextState, maxPeople);
                }

            }
        }

        return new RiverState(new int[]{-1, -1, -1}, maxPeople);
    }


}
