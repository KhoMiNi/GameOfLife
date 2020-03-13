package ConwaysGameOfLife;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class GameField extends JPanel {
    private int fieldSizeY, fieldSizeX, pointSize;

    int getFieldSizeY() {
        return fieldSizeY;
    }

    int getFieldSizeX() {
        return fieldSizeX;
    }

    int getPointSize() {
        return pointSize;
    }

    private long turn;
    private boolean[][] lifeGeneration, nextGeneration, previousGeneration;
    private Random random = new Random();

    boolean isGoing() {
        return isGoing;
    }

    void setGoing(boolean going) {
        this.isGoing = going;
    }

    private volatile boolean isGoing;

    GameField() {
        this.fieldSizeX = 50;
        this.pointSize = 8;
        this.lifeGeneration = new boolean[fieldSizeX][fieldSizeX];
        this.nextGeneration = new boolean[fieldSizeX][fieldSizeX];
        this.previousGeneration = new boolean[fieldSizeX][fieldSizeX];
        this.setBackground(Color.white);
        //this.isGoing = true;
    }

    GameField(int life_size_y,int life_size_x, int point_radius) {
        this.fieldSizeX = life_size_x;
        this.fieldSizeY = life_size_y;
        this.pointSize = point_radius;
        this.lifeGeneration = new boolean[fieldSizeX][fieldSizeY];
        this.nextGeneration = new boolean[fieldSizeX][fieldSizeY];
        this.previousGeneration = new boolean[fieldSizeX][fieldSizeY];
        this.setBackground(Color.white);
        //this.isGoing = true;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                if (lifeGeneration[x][y]) {
                    g.fillOval(x * pointSize, y * pointSize, pointSize, pointSize);
                }
            }
        }
    }

    void processOfLife() {
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                int count = countNeighbors(x, y);
                nextGeneration[x][y] = lifeGeneration[x][y];
                // if are 3 live neighbors around empty cells - the cell becomes alive
                nextGeneration[x][y] = (count == 3) ? true : nextGeneration[x][y];
                // if cell has less than 2 or greater than 3 neighbors - it will be die
                nextGeneration[x][y] = ((count < 2) || (count > 3)) ? false : nextGeneration[x][y];
            }
        }
        this.turn++;
        if(checkLoop()) this.isGoing = false;
        for (int x = 0; x < fieldSizeX; x++) {
            System.arraycopy(lifeGeneration[x], 0, previousGeneration[x], 0, fieldSizeY);
            System.arraycopy(nextGeneration[x], 0, lifeGeneration[x], 0, fieldSizeY);

        }
    }

    private int countNeighbors(int x, int y) {
        int count = 0;
        for (int dx = -1; dx < 2; dx++) {
            for (int dy = -1; dy < 2; dy++) {
                int nX = x + dx;
                int nY = y + dy;
                nX = (nX < 0) ? fieldSizeX - 1 : nX;
                nY = (nY < 0) ? fieldSizeY - 1 : nY;
                nX = (nX > fieldSizeX - 1) ? 0 : nX;
                nY = (nY > fieldSizeY - 1) ? 0 : nY;
                count += (lifeGeneration[nX][nY]) ? 1 : 0;
            }
        }
        if (lifeGeneration[x][y]) {
            count--;
        }
        return count;
    }

    void fillField() {
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                lifeGeneration[x][y] = random.nextBoolean();
            }
        }
    }

    private boolean checkLoop() {
        return Arrays.deepEquals(this.previousGeneration, this.nextGeneration);
    }

    long getTurn() {
        return this.turn;
    }

    void setTurn(long turn) {
        this.turn = turn;
    }
}
