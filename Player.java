package battleship;

import java.util.Arrays;
import java.util.Scanner;

public class Player {
    public String[][] gamefield = new String[10][10];
    private static final Scanner scanner = new Scanner(System.in);
    public String[][] privateGamefield = new String[10][10];
    public int counter = 17;
    public boolean gameWinCondition = false;
    public Player() {
        this.initialize();
    }


    public boolean shooting(Player player) {
        System.out.println("\nTake a shot\n");
        while (true) {
            String coords = scanner.next();
            int x = coords.charAt(0) - 65;
            int y = Integer.parseInt(coords.substring(1)) - 1;
            if (isCoordCorrect(x, y)) {
                if (player.gamefield[x][y].equals("~")) {
                    player.gamefield[x][y] = "M";
                    player.privateGamefield[x][y] = "M";
                    System.out.println("\nYou missed!\n");
                    return false;
                } else {
                    player.gamefield[x][y] = "X";
                    player.privateGamefield[x][y] = "X";
                    if (!isSomethingNearby(x, y)) {
                        if (this.counter == 1 || this.counter == 0) {
                            System.out.println("You sank the last ship. You won. Congratulations!");
                            this.gameWinCondition = true;
                        } else {
                            System.out.println("You sank a ship! Specify a new target:");
                        }
                        return true;
                    }
                    System.out.println("\nYou hit a ship!\n");
                    return true;
                }
            } else {
                System.out.println("\nError! You entered the wrong coordinates! Try again:\n");
            }
        }
    }

    public void setPole() {
        for (int i = 0; i < Battleship.ships.size(); i++) {
            System.out.println("\nEnter the coordinates of the " + Battleship.ships.get(i)
                    + " (" + Battleship.shipSize.get(i) + " cells):\n");
            userInput(i);
            print(this.gamefield);
        }
    }

    private void userInput(int i) {
        boolean flag = true;
        int[] x = new int[2];
        int[] y = new int[2];
        while (flag) {
            String[] coords = scanner.nextLine().split(" ");
            x[0] = Integer.parseInt(coords[0].substring(1)) - 1;
            x[1] = Integer.parseInt(coords[1].substring(1)) - 1;
            y[0] = coords[0].charAt(0) - 65;
            y[1] = coords[1].charAt(0) - 65;
            if (isLocationCorrect(Battleship.shipSize.get(i), x, y)) {
                flag = false;
            } else {
                System.out.println("\nError! Wrong ship location! Try again:\n");
                continue;
            }
            if (!isSomethingNearby(x, y)) {
                flag = false;
            } else {
                flag = true;
                System.out.println("\nError! You placed it too close to another one. Try again:\n");
            }
        }
        int xDiff = Math.abs(x[0] - x[1]) + 1;
        int yDiff = Math.abs(y[0] - y[1]) + 1;
        int xValue = Math.min(x[0], x[1]);
        int yValue = Math.min(y[0], y[1]);
        for (int j = yValue; j < yValue + yDiff; j++) {
            for (int k = xValue; k < xValue + xDiff; k++) {
                this.gamefield[j][k] = "O";
            }
        }
    }

    private void initialize() {
        for (String[] strings : gamefield) {
            Arrays.fill(strings, "~");
        }
        for (String[] strings : privateGamefield) {
            Arrays.fill(strings, "~");
        }
    }

    private static void print(String[][] field) {
        System.out.print(" ");
        int x = 65;
        for (int i = 1; i < 11; i++) {
            System.out.print(" " + i);
        }
        System.out.println();
        for (String[] strings : field) {
            System.out.print((char) x + " ");
            x++;
            for (String string : strings) {
                System.out.print(string + " ");
            }
            System.out.println();
        }
    }

    public void print(String pole) {
        switch (pole) {
            case "private" -> print(this.gamefield);
            case "public" -> print(this.privateGamefield);
        }

    }

    private boolean isSomethingNearby(int x, int y) {
        int xMax = x;
        int yMax = y;
        int yMin = y;
        int xMin = x;
        if (x == 0) xMin = 1;
        if (x == 9) xMax = 8;
        if (y == 0) yMin = 1;
        if (y == 9) yMax = 8;
        return iterate(xMax, yMax, yMin, xMin);
    }

    private boolean isSomethingNearby(int[] x, int[] y) {
        int minValueX = Math.min(x[0], x[1]);
        int maxValueX = Math.max(x[0], x[1]);
        int minValueY = Math.min(y[0], y[1]);
        int maxValueY = Math.max(y[0], y[1]);

        if (minValueX == 0) minValueX = 1;
        if (minValueY == 0) minValueY = 1;
        if (maxValueX == 9) maxValueX = 8;
        if (maxValueY == 9) maxValueY = 8;

        return iterate(maxValueY, maxValueX, minValueX, minValueY);
    }

    private boolean iterate(int xMax, int yMax, int yMin, int xMin) {
        for (int i = xMin - 1; i <= xMax + 1; i++) {
            for (int j = yMin - 1; j <= yMax + 1; j++) {
                if (this.gamefield[i][j].equals("O")) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isLocationCorrect(int shipsize, int[] x, int[] y) {

        return Math.abs(x[0] - x[1]) == shipsize - 1 && y[0] - y[1] == 0
                || Math.abs(y[0] - y[1]) == shipsize - 1 && x[0] - x[1] == 0;
    }

    private static boolean isCoordCorrect(int x, int y) {
        return x >= 0 && x <=9 && y >= 0 && y <= 9;
    }
}