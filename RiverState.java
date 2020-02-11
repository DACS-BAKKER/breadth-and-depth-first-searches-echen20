/*  RiverState Class
    Name: Ethan Chen
    Date Started: February 3, 2020

*/


import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class RiverState { // holds the river state array

    // idea for this fumbled across when looking at the wikipedia page description of the problem
    int[] stateArray; // represents all 3 in one object in order {missionaries, cannibals, river side} with initial state {1, 1, 1} and goal {0, 0, 0}
    int maxPeople;

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

    // for the depth first search, returns the next possible move depending on parameter value
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


    // for breadth-first-search, returns an iterable list of the next moves (doesn't check for collisions)
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
            @Override
            public RiverState next() {

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
                            move++;
                            return new RiverState(nextState, maxPeople);
                        }

                    }
                    for (int x = 0; x < stateArray.length; x++) {
                        nextState[x] = stateArray[x];
                    }
                    move++;
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
                    if (nextState[0] <= maxPeople && nextState[0] >= 0 && nextState[1] <= maxPeople) {

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
                    if (nextState[0] <= maxPeople && nextState[0] >= 0 && nextState[1] <= maxPeople) {

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
                    if (nextState[0] <= maxPeople && nextState[0] >= 0 && nextState[1] <= maxPeople) {

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
        for(int x = 0; x<3; x++) {
            if(stateArray[x] != comparedState.stateArray[x]) {
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

}
