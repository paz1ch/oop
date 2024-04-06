import java.util.*;
public class Main{

    public static void main(String[] args) {
        int[][] initial = {
                {2, 8, 3},
                {1, 6, 4},
                {7, 0, 5}
        };
        int[][] goal = {
                {1, 2, 3},
                {8, 0, 4},
                {7, 6, 5}
        };
        EightPuzzleSolver e = new EightPuzzleSolver();
        e.solve(initial, goal);
    }
}
