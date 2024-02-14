public class Cell {
    /**
     * this is the Cell class. it is used to create new Cell objects and create Cell methods
     */
    private int x;
    private int y;
    private int size;
    private int row;
    private MooreRules rules;
    private int column;
    private CellState cellState;

    /**
     * @param x is the x coordinate value of the Cell object
     * @param y is the y coordinate value of the Cell object
     * @param size is the size of the Cell
     * @param row is the row of the array that the Cell object will be placed in
     * @param column is the column of the array that the Cell object will be placed in
     * @param cellState is the state of the Cell object (ALIVE, DEAD, WILL_DIE, or WILL_REVIVE)
     * @param rules is the set of rules applied to the Cell object
     * this constructor creates a new Cell with these parameters
     */
    public Cell(int x, int y, int size, int row, int column, CellState cellState, MooreRules rules) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.row = row;
        this.column = column;
        this.cellState = cellState;
        this.rules = rules;
    }

    /**
     * @param cells is used to get countLiveNeighbors for the cell array
     * the method determines if it will change state upon completion of its next evolution
     * if so, assigns itself the corresponding intermediate state
     */
    public void applyRules(Cell[][] cells) {
        int liveNeighbors = countLiveNeighbors(cells);
        cellState = rules.applyRules(cellState, liveNeighbors);
    }

    /**
     * if in an intermediate state, completes the transition to its next state
     */
    public void evolve() {
        if (cellState == CellState.WILL_REVIVE) {
            cellState = CellState.ALIVE;
        } else if (cellState == CellState.WILL_DIE) {
            cellState = CellState.DEAD;
        }
    }

    /**
     * creates a rectangle for the Cell at the parameter values, checks the cellState
     * if ALIVE, it will fill it in black, if it's DEAD, fill it in white
     */
    public void display() {
        if (CellState.ALIVE == cellState) {
            Main.app.fill(255, 191, 217);
        } else if (CellState.DEAD == cellState) {
            Main.app.fill(255);
        }
        Main.app.rect(x, y, size, size);
    }

    /**
     * toggles the cellState between ALIVE and DEAD
     */
    public void handleClick() {
        if (cellState == CellState.ALIVE) {
            cellState = CellState.DEAD;
        } else {
            cellState = CellState.ALIVE;
        }
    }

    /**
     * @return the amount of live neighbors
     * @param cells array is iterated through
     * uses for loops in  to iterate through the surrounding cells and checks if they are alive
     */
    private int countLiveNeighbors(Cell[][] cells) {
        int liveNeighbors = 0;
        for (int r = this.row - 1; r < this.row + 2; r++) {
            for (int c = this.column - 1; c < this.column + 2; c++) {
                if (cells[r][c].cellState == CellState.ALIVE || cells[r][c].cellState == CellState.WILL_DIE) {
                    liveNeighbors++;
                }
            }
        }
        if (this.cellState == CellState.ALIVE) {
            liveNeighbors--;
        }
        return liveNeighbors;
    }
}