package aleksey.krhisanfov.cellularautomat;

import javafx.scene.input.Clipboard;

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
    }


    void setAlive(int x, int y) {
        board[y][x] = 1;
    }

    String getState(int x, int y) {
        String state = "";
        if (x >= width) {
            x = 0;
        }
        if (x < 0) {
            x = width - 1;
        }
        return String.valueOf(board[y][x]);
    }

    void setRule(int rule) {
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


    String countNeighbors(int x, int y) {
        String counter = "";
        counter += getState(x - 1,y - 1);
        counter += getState(x,y - 1);
        counter += getState(x + 1,y - 1);
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
            String neighborsCode = countNeighbors(x, currentLine);

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


}
