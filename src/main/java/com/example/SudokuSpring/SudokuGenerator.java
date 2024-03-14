package com.example.SudokuSpring;

import java.util.Random;
public class SudokuGenerator {

    private final int[][] grid;
    private int[][] originalGridNumbers;
    private final boolean isFilled;
    private static final int SIZE = 9;

    private double difficulty;  //1.5, 2, 3

    public SudokuGenerator() {
        grid = new int[SIZE][SIZE];
        isFilled = originalGridNumbers != null;
        this.difficulty = 2;
    }

    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }

    public void generate() {
        fillDiagonalBoxes();
        fillRemaining(0, 3);
        removeCells();
        if (!isFilled) {
            fillTheOriginalNumbers();
        }
    }

    private void fillDiagonalBoxes() {
        for (int i = 0; i < SIZE; i += 3) {
            fillBox(i, i);
        }
    }

    private void fillBox(int row, int col) {
        Random random = new Random();
        int num;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                do {
                    num = random.nextInt(SIZE) + 1;
                } while (!isValid(row, col, num));
                grid[row + i][col + j] = num;
            }
        }
    }

    private boolean isValid(int row, int col, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (grid[row][i] == num || grid[i][col] == num) {
                return false;
            }
        }
        int boxRow = row - row % 3;
        int boxCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[boxRow + i][boxCol + j] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean fillRemaining(int row, int col) {
        if (col >= SIZE && row < SIZE - 1) {
            row++;
            col = 0;
        }
        if (row >= SIZE && col >= SIZE) {
            return true;
        }
        if (row < 3) {
            if (col < 3) {
                col = 3;
            }
        } else if (row < SIZE - 3) {
            if (col == (row / 3) * 3) {
                col += 3;
            }
        } else {
            if (col == SIZE - 3) {
                row++;
                col = 0;
                if (row >= SIZE) {
                    return true;
                }
            }
        }
        for (int num = 1; num <= SIZE; num++) {
            if (isValid(row, col, num)) {
                grid[row][col] = num;
                if (fillRemaining(row, col + 1)) {
                    return true;
                }
                grid[row][col] = 0;
            }
        }
        return false;
    }
    private void removeCells() {
        Random random = new Random();
        double cellsToRemove = SIZE * SIZE / difficulty;
        while (cellsToRemove > 0) {
            int row = random.nextInt(SIZE);
            int col = random.nextInt(SIZE);
            if (grid[row][col] != 0) {
                grid[row][col] = 0;
                cellsToRemove--;
            }
        }
    }

    private void fillTheOriginalNumbers() {
        originalGridNumbers = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(grid[i], 0, originalGridNumbers[i], 0, SIZE);
        }
    }
    public int[][] getOriginalGridNumbers() {
        generate();
        return originalGridNumbers;
    }
}
