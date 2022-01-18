package aleksey.krhisanfov.cellularautomat;

public class Simulation {
    private int width;
    private int height;
    private int[][] board;

    public Simulation(int width, int height) {
        this.width = width;
        this.height = height;
        board = new int[height][width];
    }

     void setSizeOfBoard(int width, int height) {
        this.width = width;
        this.height = height;
        board = new int[height][width];
    }

    void step() {
        int[][] newBoard = new int[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int neighbours = countNeighbour(y,x);
                if (getState(y,x) == 1) {
                    if (neighbours < 2 || neighbours > 3) {
                        newBoard[y][x] = 0;
                    } else {
                        newBoard[y][x] = 1;
                    }
                } else {
                    if (neighbours == 3) {
                        newBoard[y][x] = 1;
                    }
                }
            }
        }
        this.board = newBoard;
    }

    void setAlive(int y, int x) {
        this.board[y][x] = 1;
    }

    void setDead(int y, int x) {
        this.board[y][x] = 0;
    }

    int getState(int y, int x) {
        if (x >= width || x < 0) {
            return 0;
        }
        if (y >= height || y < 0) {
            return 0;
        }
        return this.board[y][x];
    }

    int countNeighbour(int y, int x) {
        int counter = 0;
        counter += getState(y - 1,x - 1);
        counter += getState(y - 1,x);
        counter += getState(y - 1,x + 1);

        counter += getState(y,x - 1);
        counter += getState(y,x + 1);

        counter += getState(y + 1,x - 1);
        counter += getState(y + 1,x);
        counter += getState(y + 1,x + 1);

        return counter;

    }

    void printBoard() {
        for (int y = 0; y < height; y++) {
            String line = "|";
            for (int x = 0; x < width; x++) {
                if (this.board[y][x] == 1) {
                    line += "1";
                } else {
                    line += "0";
                }
            }
            line += "|";
            System.out.println(line);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Simulation simulation = new Simulation(4, 4);
        simulation.setAlive(1,1);
        simulation.setAlive(1,2);
        simulation.setAlive(1,3);

        simulation.printBoard();
        simulation.step();

        simulation.printBoard();
        simulation.step();

        simulation.printBoard();
        simulation.step();

        simulation.printBoard();
        simulation.step();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
