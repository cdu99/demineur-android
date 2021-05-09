package fr.uge.demineur;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import static fr.uge.demineur.Cell.BOMB;
import static fr.uge.demineur.Cell.EMPTY;

public class Demineur implements Serializable {
    private final Grid grid;
    private boolean gameOver;
    private boolean flagState;

    public Demineur(int size, int numberOfBombs) {
        this.grid = new Grid(size);
        gameOver = false;
        flagState = false;
        grid.initializeGrid(numberOfBombs);
    }

    public void flagClick() {
        flagState = !flagState;
    }

    public void flagCell(Cell cell) {
        if (!cell.isOpen()) {
            cell.setFlag(!cell.isFlag());
        }
    }

    public boolean flagIndicator() {
        return flagState;
    }

    public void openCell(Cell cell) {
        if (!gameOver && !isGameWon()) {
            if (flagState) {
                flagCell(cell);
            } else {
                getGrid().getCells()[cell.getX()][cell.getY()].setOpen(true);
                if (cell.getValue() == Cell.EMPTY) {
                    Queue<Cell> todo = new LinkedList<>();
                    Set<Cell> toOpen = new HashSet<>();
                    ArrayList<Cell> adjacentCells = new ArrayList<>();
                    todo.add(cell);

                    while (!todo.isEmpty()) {
                        Cell currentCell = todo.remove();
                        int x = currentCell.getX();
                        int y = currentCell.getY();

                        // Toutes les cases adjacentes de currentCell
                        if (isInGrid(x + 1, y)) {
                            adjacentCells.add(getGrid().getCells()[x + 1][y]);
                        }
                        if (isInGrid(x - 1, y)) {
                            adjacentCells.add(getGrid().getCells()[x - 1][y]);
                        }
                        if (isInGrid(x, y + 1)) {
                            adjacentCells.add(getGrid().getCells()[x][y + 1]);
                        }
                        if (isInGrid(x, y - 1)) {
                            adjacentCells.add(getGrid().getCells()[x][y - 1]);
                        }
                        if (isInGrid(x + 1, y - 1)) {
                            adjacentCells.add(getGrid().getCells()[x + 1][y - 1]);
                        }
                        if (isInGrid(x + 1, y + 1)) {
                            adjacentCells.add(getGrid().getCells()[x + 1][y + 1]);
                        }
                        if (isInGrid(x - 1, y + 1)) {
                            adjacentCells.add(getGrid().getCells()[x - 1][y + 1]);
                        }
                        if (isInGrid(x - 1, y - 1)) {
                            adjacentCells.add(getGrid().getCells()[x - 1][y - 1]);
                        }

                        for (Cell adjacentCell : adjacentCells) {
                            if (adjacentCell.getValue() == EMPTY) {
                                if (!toOpen.contains(adjacentCell)) {
                                    if (!todo.contains(adjacentCell)) {
                                        todo.add(adjacentCell);
                                    }
                                }
                            } else {
                                toOpen.add(adjacentCell);
                            }
                        }
                        toOpen.add(currentCell);
                    }

                    for (Cell cellToOpen : toOpen) {
                        cellToOpen.setOpen(true);
                    }
                } else if (cell.getValue() == BOMB) {
                    gameOver = true;
                }
            }
        }
    }

    // Vérifie si une position est valide dans la grille
    private boolean isInGrid(int x, int y) {
        if (x < 0 || y < 0) {
            return false;
        }
        if (x >= grid.getSize() || y >= grid.getSize()) {
            return false;
        }
        return true;
    }

    public Grid getGrid() {
        return grid;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isGameWon() {
        Cell[][] cells = grid.getCells();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells.length; j++) {
                if (cells[i][j].getValue() != BOMB && !cells[i][j].isOpen()
                        && cells[i][j].getValue() != EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }
}
