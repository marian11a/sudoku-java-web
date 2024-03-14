package com.example.SudokuSpring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Checker {
    public static boolean sudokuChecker (int[][] rc) {
        String numbers = "1 2 3 4 5 6 7 8 9";

        List<Integer> subset = Arrays
                .stream(numbers.split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        Collections.sort(subset);
        List<List<Integer>> boxList = new ArrayList<>();

        if (boxChecker(rc, subset, boxList)) return false;
        if (rowsChecker(rc, subset)) return false;
        if (columnsChecker(rc, subset)) return false;

        System.out.println("Sudoku Solved!");
        return true;
    }

    private static boolean boxChecker(int[][] rc, List<Integer> subset, List<List<Integer>> boxList) {
        for (int i = 0; i < 9; i++) {
            List<Integer> newLists = new ArrayList<>();
            boxList.add(newLists);
        }
        for (int i = 0; i <= 8; i++) {
            for (int j = 0; j <= 8; j++) {
                switch (i) {
                    case 0:
                    case 1:
                    case 2:
                        switch (j) {
                            case 0, 1, 2 -> boxList.get(0).add(rc[i][j]);
                            case 3, 4, 5 -> boxList.get(1).add(rc[i][j]);
                            case 6, 7, 8 -> boxList.get(2).add(rc[i][j]);
                        }
                        break;
                    case 3:
                    case 4:
                    case 5:
                        switch (j) {
                            case 0, 1, 2 -> boxList.get(3).add(rc[i][j]);
                            case 3, 4, 5 -> boxList.get(4).add(rc[i][j]);
                            case 6, 7, 8 -> boxList.get(5).add(rc[i][j]);
                        }
                        break;
                    case 6:
                    case 7:
                    case 8:
                        switch (j) {
                            case 0, 1, 2 -> boxList.get(6).add(rc[i][j]);
                            case 3, 4, 5 -> boxList.get(7).add(rc[i][j]);
                            case 6, 7, 8 -> boxList.get(8).add(rc[i][j]);
                        }
                        break;
                }
            }
        }
        for (int i = 0; i <= 8 ; i++) {
            List<Integer> currentList = boxList.get(i);
            Collections.sort(currentList);
            if (!currentList.equals(subset)) {
                System.out.println("Error! box");
                return true;
            }
        }
        return false;
    }

    private static boolean columnsChecker(int[][] rc, List<Integer> subset) {
        for (int i = 0; i <= 8 ; i++) {
            List<Integer> currentList = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                currentList.add(rc[j][i]);
            }

            Collections.sort(currentList);
            if (!currentList.equals(subset)) {
                System.out.println("Error! column");
                return true;
            }
        }
        return false;
    }

    private static boolean rowsChecker(int[][] rc, List<Integer> subset) {
        for (int i = 0; i <= 8 ; i++) {
            int[] currentArr = rc[i];
            List<Integer> currentList = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                currentList.add(currentArr[j]);
            }

            Collections.sort(currentList);
            if (!currentList.equals(subset)) {
                System.out.println("Error! rows");
                return true;
            }
        }
        return false;
    }
}