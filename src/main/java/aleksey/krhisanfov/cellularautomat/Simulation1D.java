package aleksey.krhisanfov.cellularautomat;

import javafx.scene.input.Clipboard;


//при пустой доске на нажатие степ баг
public class Simulation1D {
    int[] ruleInterpretation;
    int[][] board;
    int width;
    int height;
    int rule;
    int currentLine;

    public Simulation1D(int width, int height) {
        setSizeOfBoard(width, height);
        this.rule = 22;
        this.currentLine = 1;
        this.ruleInterpretation = new int[8];
        setRule(22);
    }

    void setSizeOfBoard(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new int[height][width];
        currentLine = 1;
    }


    void setAlive(int y, int x) {
        board[y][x] = 1;
    }

    String getState(int y, int x) {
        String state = "";
        if (x >= width) {
            x = 0;
        }
        if (x < 0) {
            x = width - 1;
        }
        return String.valueOf(board[y][x]);
    }

    int isAlive(int y, int x) {
        return this.board[y][x];
    }

    public void setRule(int rule) {
        if (rule == 0) {
            ruleInterpretation[0] = 1;
            return;
        }
        this.rule = rule;
        String code = String.format("%8s", Integer.toBinaryString(rule)).replace(' ', '0');
        for (int i = 7; i >= 0; i--) {
            if (code.charAt(i) == '1') {
                ruleInterpretation[7 - i] = 1;
            } else {
                ruleInterpretation[7 - i] = 0;
            }
        }
    }


    String countNeighbors(int y, int x) {
        if (y == 0 ) {
            y = height;
        }
        String counter = "";
        counter += getState(y - 1,x - 1);
        counter += getState(y - 1,x);
        counter += getState(y - 1,x + 1);
        return counter;
    }

    int convertBinaryStringToInt(String str) {
        int ans = 0;
        int degree = 0;
        for (int i = str.length() - 1; i >= 0; i--) {
            int helper = str.charAt(i) - '0';
            ans += helper * Math.pow(2, degree);
            degree++;
        }
        return ans;
    }

    void step() {
        for (int x = 0; x < width; x++) {
            if (currentLine >= height) {
                currentLine = 0;
            }
            String neighborsCode = countNeighbors(currentLine, x);

            if (ruleInterpretation[convertBinaryStringToInt(neighborsCode)] == 1) {
                board[currentLine][x] = 1;
            } else {
                board[currentLine][x] = 0;
            }
        }
        currentLine += 1;
    }

    public static void main(String[] args) {
        Simulation1D simulation1D = new Simulation1D(6, 6);
        simulation1D.setAlive(1,0);
        simulation1D.setAlive(2,0);
        simulation1D.setAlive(3,0);
        simulation1D.setAlive(5,0);
        simulation1D.setRule(129);
        simulation1D.step();
        simulation1D.printBoard();
        System.out.println("----");
        simulation1D.step();
        simulation1D.printBoard();
        System.out.println("----");
        simulation1D.step();
        simulation1D.printBoard();
        System.out.println("----");
    }


    void printBoard() {
        for (int y = 0; y < height; y++) {
            String line = "|";
            for (int x = 0; x < width; x++) {
                if (board[y][x] == 1) {
                    line += "*";
                } else {
                    line += "-";
                }
            }
            line += "|";
            System.out.println(line);
        }
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

    void setDead(int y, int x) {
        this.board[y][x] = 0;
    }

    public void setCurrentLine(int currentLine) {
        this.currentLine = currentLine;
    }
}
