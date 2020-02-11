/*  Cannibals and Missionaries Graphics
    Name: Ethan Chen
    Date Started: February 7, 2020

*/

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CMGraphics extends JPanel implements ActionListener {

    private Timer tm;
    private boolean inMotion = false;
    int[][] solution; // gives solution arrays
    int currentIndex = 0;
    int ySpacing = 100;
    boolean[] movingMiss = new boolean[3];
    boolean[] movingCann = new boolean[3];
    boolean doneOnce = false;

    int[] cannibals; // array that controls which side cannibals and missionaries are on
    int[] missionaries;

    int currX;
    int nextX;


    public static void main(String[] args) {

        CMGraphics c = new CMGraphics();

        JFrame f = new JFrame();
        f.setTitle("Cannibals and Missionaries");
        f.setSize(600, 725);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
        f.add(c);
        f.setVisible(true);
    }

    public CMGraphics() {

        tm = new Timer(30, this);
        tm.setInitialDelay(1000);
        tm.start();
    }

    public void paintComponent(Graphics g) {

        // solve game
        if (!doneOnce) {
            CannibalsAndMissionaries cm = new CannibalsAndMissionaries(0, 3);
            solution = new int[100][3];

            int counter = 0;
            for (RiverState rn : cm.getSolution()) {
                for (int y = 0; y < 3; y++) {
                    solution[counter][y] = rn.stateArray[y];
                }
                counter++;
            }
            cannibals = new int[]{1, 1, 1};
            missionaries = new int[]{1, 1, 1};
            doneOnce = true;
        }

        // change graphics settings
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g.setColor(Color.BLACK);
        float thickness = 4;
        g2.setStroke(new BasicStroke(thickness));

        g.setColor(Color.BLUE);
        g.drawRect(275, 0, 50, 725);
        g.setColor(Color.BLACK);


        // reposition people
        for (int x = 0; x < 3; x++) {
            if (movingMiss[x]) {
                g.drawRect(currX, 25 + x * ySpacing, 50, 50);
                // draws boat, in here so it only draws when others are moving too
                g.setColor(Color.BLUE);
                g.drawRect(currX, 25 + 6 * ySpacing, 50, 50);
                g.setColor(Color.BLACK);
            } else {
                if(missionaries[x] == 0) {
                    g.drawRect(400, 25 + x * ySpacing, 50, 50);
                } else {
                    g.drawRect(100, 25 + x * ySpacing, 50, 50);
                }
            }
        }

        g.setColor(Color.RED);

        for (int x = 3; x < 6; x++) {
            if (movingCann[x-3]) {
                g.drawRect(currX, 25 + x * ySpacing, 50, 50);

                // draws boat, in here so it only draws when others are moving too
                g.setColor(Color.BLUE);
                g.drawRect(currX, 25 + 6 * ySpacing, 50, 50);
                g.setColor(Color.RED);
            } else {
                if(cannibals[x-3] == 0) {
                    g.drawRect(400, 25 + x * ySpacing, 50, 50);
                } else {
                    g.drawRect(100, 25 + x * ySpacing, 50, 50);
                }
            }
        }




        // Move the correct people
        if (inMotion) {
            if (solution[currentIndex + 1][2] == 1) {
                currX -= 5;
            } else {
                currX += 5;
            }
            if (currX == nextX) {
                inMotion = false;
                currentIndex++;
                for(int x = 0; x<3; x++) {
                    if(movingCann[x] == true) {
                        cannibals[x] = Math.abs(1-cannibals[x]);
                    }
                }
                for(int x = 0; x<3; x++) {
                    if(movingMiss[x] == true) {
                        missionaries[x] = Math.abs(1-missionaries[x]);
                    }
                }
            }
        }
        if (!inMotion) {
            int missionaryOffset = solution[currentIndex + 1][0] - solution[currentIndex][0];
            int cannibalOffset = solution[currentIndex + 1][1] - solution[currentIndex][1];
            movingMiss = new boolean[3];
            movingCann = new boolean[3];

            int counter = 0;
            for (int x = 0; x < 3; x++) {
                if (missionaries[x] == 1 && missionaryOffset < 0 && counter > missionaryOffset) {
                    movingMiss[x] = true;
                    counter--;
                } else if (missionaries[x] == 0 && missionaryOffset > 0 && counter < missionaryOffset) {
                    movingMiss[x] = true;
                    counter++;
                }
            }

            counter = 0;
            for (int x = 0; x < 3; x++) {
                if (cannibals[x] == 1 && cannibalOffset < 0 && counter > cannibalOffset) {
                    movingCann[x] = true;
                    counter--;
                } else if (cannibals[x] == 0 && cannibalOffset > 0 && counter < cannibalOffset) {
                    movingCann[x] = true;
                    counter++;
                }
            }

            if (solution[currentIndex + 1][2] == 1) {
                currX = 400;
                nextX = 100;
            } else {
                currX = 100;
                nextX = 400;
            }
            inMotion = true;

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Check if finished
        if (currentIndex > 0) {
            if (solution[currentIndex - 1][0] != 0 || solution[currentIndex - 1][1] != 0 || solution[currentIndex - 1][2] != 0) {
                repaint();
            }
        } else {
            repaint();
        }
    }
}