package com.example.SudokuSpring;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class SudokuController {

    private int[][] sudokuGrid;
    private double difficulty;

    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("index");
    }

    @PostMapping("/setDifficulty/{difficulty}")
    public ModelAndView edit(
            @PathVariable("difficulty") double difficulty) {
        this.sudokuGrid = null;
        this.difficulty = difficulty;
        return new ModelAndView("redirect:/sudoku");
    }

    @GetMapping("/sudoku")
    public ModelAndView getSudokuGrid() {
        if (sudokuGrid == null) {
            generateSudokuGrid();
        }

        return new ModelAndView("sudoku", "sudokuGrid", sudokuGrid);
    }

    @PostMapping("/sudoku/update")
    public void updateSudokuGrid(@RequestBody int[][] updatedGrid) {
        this.sudokuGrid = updatedGrid;
    }

    @GetMapping("/sudoku/check")
    public ModelAndView checkSudokuGrid() {
        if (Checker.sudokuChecker(sudokuGrid)) {
            return new ModelAndView("solved");
        } else {
            return new ModelAndView("unsolved");
        }
    }

    @PostMapping("/restart")
    public ModelAndView restartSudokuGrid() {
        sudokuGrid = null;
        this.difficulty = 2;
        return new ModelAndView("redirect:/");
    }

    private void generateSudokuGrid() {
        SudokuGenerator sudokuGenerator = new SudokuGenerator();
        sudokuGenerator.setDifficulty(difficulty);
        sudokuGrid = sudokuGenerator.getOriginalGridNumbers();
    }
}
