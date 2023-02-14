package battleship;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class Battleship {

    public static final List<String> ships = Arrays.asList("Aircraft Carrier",
            "Battleship", "Submarine", "Cruiser", "Destroyer");
    public static final List<Integer> shipSize = Arrays.asList(5, 4, 3, 3, 2);

    public void play() {
        Player player1 = new Player();
        Player player2 = new Player();
        System.out.println("Player 1, place your ships on the game field");
        player1.print("private");
        player1.setPole();
        promptEnterKey();
        System.out.println("Player 2, place your ships on the game field");
        player2.print("private");
        player2.setPole();
        promptEnterKey();
        int step = 0;
        while (!player1.gameWinCondition || !player2.gameWinCondition) {
            if ( step % 2 == 0 ) {
                print2Poles(player1, player2);
                System.out.println("\nPlayer 1, it's your turn:");
                if (move(player1, player2)) player1.counter--;
            } else {
                print2Poles(player2, player1);
                System.out.println("\nPlayer 2, it's your turn:");
                if (move(player2, player1)) player2.counter--;
            }
            step++;
            promptEnterKey();
        }
    }
    private static void print2Poles(Player player1, Player player2) {
        player2.print("public");
        System.out.println("---------------------");
        player1.print("private");
    }

    private static boolean move(Player player1, Player player2) {
        return player1.shooting(player2);
    }

    private static void promptEnterKey() {
        System.out.println("Press Enter and pass the move to another player");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}