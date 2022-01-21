package aleksey.krhisanfov.cellularautomat;

public class Simulation2D {
    private int width;
    private int height;
    private int[][] board;


    public Simulation2D(int width, int height) {
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

    public void setState(int y, int x, int drawMode) {
        if (x >= width || x < 0) {
            return;
        }
        if (y >= height || y < 0) {
            return;
        }
        if (drawMode == 1) {
            setAlive(y,x);
        } else {
            setDead(y,x);
        }
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


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    public void clearBoard() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (this.board[y][x] == 1) {
                    this.board[y][x] = 0;
                }
            }
        }
    }
}
