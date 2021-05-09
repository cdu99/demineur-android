package fr.uge.demineur;

import java.io.Serializable;
import java.util.Random;

import static fr.uge.demineur.Cell.BOMB;
import static fr.uge.demineur.Cell.EMPTY;

public class Grid implements Serializable {
    private final Cell[][] cells;
    private final int size;

    public Grid(int size) {
        this.size = size;
        cells = new Cell[size][size];
        createGrid();
    }

    private void createGrid() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new Cell(EMPTY, i, j);
            }
        }
    }

    // n nombre de bombes
    public void initializeGrid(int n) {
        // On met les bombes
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            int x = rand.nextInt(size);
            int y = rand.nextInt(size);

            if (cells[x][y].getValue() == EMPTY) {
                cells[x][y].setValue(BOMB);
            } else {
                i--;
            }
        }

        // On met les chiffres
        for (int j = 0; j < size; j++) {
            for (int k = 0; k < size; k++) {
                if (cells[j][k].getValue() != BOMB) {
                    int cptBomb = 0;
                    // On regarde toutes les cases aux alentours pour checker si y'a des bombes
                    if (isInGrid(j - 1, k) && cells[j - 1][k] != null && cells[j - 1][k].getValue() == BOMB) {
                        cptBomb++;
                    }
                    if (isInGrid(j + 1, k) && cells[j + 1][k] != null && cells[j + 1][k].getValue() == BOMB) {
                        cptBomb++;
                    }
                    if (isInGrid(j, k - 1) && cells[j][k - 1] != null && cells[j][k - 1].getValue() == BOMB) {
                        cptBomb++;
                    }
                    if (isInGrid(j, k + 1) && cells[j][k + 1] != null && cells[j][k + 1].getValue() == BOMB) {
                        cptBomb++;
                    }
                    if (isInGrid(j + 1, k + 1) && cells[j + 1][k + 1] != null && cells[j + 1][k + 1].getValue() == BOMB) {
                        cptBomb++;
                    }
                    if (isInGrid(j - 1, k - 1) && cells[j - 1][k - 1] != null && cells[j - 1][k - 1].getValue() == BOMB) {
                        cptBomb++;
                    }
                    if (isInGrid(j - 1, k + 1) && cells[j - 1][k + 1] != null && cells[j - 1][k + 1].getValue() == BOMB) {
                        cptBomb++;
                    }
                    if (isInGrid(j + 1, k - 1) && cells[j + 1][k - 1] != null && cells[j + 1][k - 1].getValue() == BOMB) {
                        cptBomb++;
                    }
                    // Si y'a des bombes autours on ajoute le nombre de bombes trouvées
                    if (cptBomb > 0) {
                        cells[j][k].setValue(cptBomb);
                    }
                }
            }
        }
    }

    // Vérifie si une position est valide dans la grille
    private boolean isInGrid(int x, int y) {
        if (x < 0 || y < 0) {
            return false;
        }
        if (x >= size || y >= size) {
            return false;
        }
        return true;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int getSize() {
        return size;
    }

    public void openAllBombs() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (cells[i][j].getValue() == BOMB) {
                    cells[i][j].setOpen(true);
                }
            }
        }
    }
}
