/*  Cannibals and Missionaries Graphics
    Name: Ethan Chen
    Date Started: February 7, 2020

*/

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class CMGraphicsWImages extends JPanel implements ActionListener {

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

    BufferedImage cannImg;
    BufferedImage missImg;
    BufferedImage boatImg;
    BufferedImage riverImg;

    public static void main(String[] args) {

        CMGraphicsWImages c = new CMGraphicsWImages();

        JFrame f = new JFrame();
        f.setTitle("Cannibals and Missionaries");
        f.setSize(600, 750);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
        f.add(c);
        f.setVisible(true);
    }

    public CMGraphicsWImages() {

        tm = new Timer(30, this);
        tm.setInitialDelay(1000);
        tm.start();
    }

    public void paintComponent(Graphics g) {


        // solve game (only does the first time through
        if (!doneOnce) {

            try {
                cannImg = ImageIO.read(new File("/Users/echen20/IdeaProjects/BreadthDepthFirst/src/cannibal.jpg"));
                cannImg = resize(cannImg, 75, 75);

                missImg = ImageIO.read(new File("/Users/echen20/IdeaProjects/BreadthDepthFirst/src/missionary.png"));
                missImg = resize(missImg, 75, 75);

                boatImg = ImageIO.read(new File("/Users/echen20/IdeaProjects/BreadthDepthFirst/src/boat.jpg"));
                boatImg = resize(boatImg, 75, 75);

                riverImg = ImageIO.read(new File("/Users/echen20/IdeaProjects/BreadthDepthFirst/src/kissclipart-river-art-png-clipart-clip-art-0b7ba5fa4ecc52e2.jpg"));
                riverImg = resize(riverImg, 100, 750);

            } catch (IOException e) {
                e.printStackTrace();
            }

            CannibalsAndMissionaries cm = new CannibalsAndMissionaries(0, 3); // same graphics for bfs or dfs
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

        // river through the middle
        g.setColor(Color.BLUE);
        g.drawImage(riverImg, 275, 0, null);
        g.setColor(Color.BLACK);


        // reposition people
        for (int x = 0; x < 3; x++) {
            if (movingMiss[x]) {
                g.drawImage(missImg, currX, 25 + x * ySpacing, null);
                // draws boat, in here so it only draws when others are moving too

                g.drawImage(boatImg, currX, 25 + 6 * ySpacing, null);

            } else {
                if (missionaries[x] == 0) {
                    g.drawImage(missImg, 400, 25 + x * ySpacing, null);
                } else {
                    g.drawImage(missImg, 100, 25 + x * ySpacing, null);
                }
            }
        }

        g.setColor(Color.RED);

        for (int x = 3; x < 6; x++) {
            if (movingCann[x - 3]) {
                g.drawImage(cannImg, currX, 25 + x * ySpacing, null);
                // draws boat, in here so it only draws when others are moving too

                g.drawImage(boatImg, currX, 25 + 6 * ySpacing, null);
            } else {
                if (cannibals[x - 3] == 0) {
                    g.drawImage(cannImg, 400, 25 + x * ySpacing, null);
                } else {
                    g.drawImage(cannImg, 100, 25 + x * ySpacing, null);
                }
            }
        }

        // Move the correct people
        if (inMotion) {
            if (solution[currentIndex + 1][2] == 1) { // movement
                currX -= 5;
            } else {
                currX += 5;
            }
            if (currX == nextX) { // finishes moving
                inMotion = false;
                currentIndex++; // moves to next block
                for (int x = 0; x < 3; x++) {
                    if (movingCann[x] == true) { // switches cannibal location of those that moved
                        cannibals[x] = Math.abs(1 - cannibals[x]); // fancy 1 => 0 or 0 => 1
                    }
                }
                for (int x = 0; x < 3; x++) {
                    if (movingMiss[x] == true) { // switches missionary location of those that moved
                        missionaries[x] = Math.abs(1 - missionaries[x]);
                    }
                }
            }
        }
        if (!inMotion) { // right after it finishes moving
            int missionaryOffset = solution[currentIndex + 1][0] - solution[currentIndex][0];
            int cannibalOffset = solution[currentIndex + 1][1] - solution[currentIndex][1];
            movingMiss = new boolean[3];
            movingCann = new boolean[3];

            int counter = 0;
            for (int x = 0; x < 3; x++) {
                if (missionaries[x] == 1 && missionaryOffset < 0 && counter > missionaryOffset) { // finds two on the correct side that can move
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

            if (solution[currentIndex + 1][2] == 1) { // sets initial based on which side, consistent for all moving objects
                currX = 400;
                nextX = 100;
            } else {
                currX = 100;
                nextX = 400;
            }
            inMotion = true;

        }
    }

    //taken from stack overflow to rezsize image
    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
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