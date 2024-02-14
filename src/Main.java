import processing.core.PApplet;
public class Main extends PApplet {
    /**
     * Main class is a subclass of PApplet
     */
    private final int CELL_SIZE = 20;
    private final int NUM_COLUMNS = 50;
    private final int NUM_ROWS = 25;
    public static Main app;
    private Cell[][] cells;
    private boolean doEvolve;

    public static void main(String[] args) {
        PApplet.main("Main");
    }

    public Main() {
        app = this;
        doEvolve = false;
    }

    public void settings() {
        size(NUM_COLUMNS * CELL_SIZE, NUM_ROWS * CELL_SIZE);
    }

    public void setup() {
        //where we add cell objects to the cells grid (in the Cells 2D array)
        cells = new Cell[NUM_ROWS][NUM_COLUMNS];
        MooreRules rules = new MooreRules(new int[] {3}, new int[] {2, 3});
        CellState randomState;
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[0].length; col++) {
                randomState = CellState.DEAD;
                double rand = Math.random();
                if(rand > 0.5 && row != 0 && col != 0 && row != cells.length-1 && col != cells[0].length -1){
                    randomState = CellState.ALIVE;
                }
                int x = col * CELL_SIZE;
                int y = row * CELL_SIZE;
                cells[row][col] = new Cell(x, y, CELL_SIZE, row, col, randomState, rules);
            }
        }
        frameRate(7);
    }

    public void draw() {
        if (doEvolve){
            applyRules();
            evolve();
        }
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                cells[i][j].display();
            }
        }
    }

    public void mouseClicked() {
        int col = mouseX / CELL_SIZE;
        int row = mouseY / CELL_SIZE;
        cells[row][col].handleClick();
    }

    public void keyPressed() {
        doEvolve = !doEvolve;
    }

    private void applyRules() {
        //iterate over cells and apply rules to each cell
        for (int r = 1; r < cells.length - 1; r++) {
            for (int c = 1; c < cells[0].length - 1; c++) {
                cells[r][c].applyRules(cells);
            }
        }
    }

    private void evolve() {
        for (int r = 0; r < cells.length - 1; r++) {
            for (int c = 0; c < cells[0].length - 1; c++) {
                cells[r][c].evolve();
            }
        }
    }
}