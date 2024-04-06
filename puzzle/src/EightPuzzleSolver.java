import java.util.*;

public class EightPuzzleSolver{

    // ham tinh so o ko dung vi tri
    private static int tinhtoanheritic(int[][] board, int[][] goal) {
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != goal[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    // ham kiem tra trang thai hien tai co phai trang thai dich hay khong
    private static boolean check(int[][] board, int[][] goal) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != goal[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // lopb de bieu dien trang thai cua bang
    static class State implements Comparable<State> {
        int[][] board;
        int heuristic;
        int moves;
        State parent;

        State(int[][] board, int[][] goal, int moves, State parent) {
            this.board = board;
            this.heuristic = tinhtoanheritic(board, goal);
            this.moves = moves;
            this.parent = parent;
        }

        @Override
        public int compareTo(State other) {
            return this.heuristic + this.moves - (other.heuristic + other.moves);
        }
    }

    // ham in ra cac trang thai cua bang
    private static void printBoard(int[][] board) {
        for (int[] row : board) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    //ham giai
    public static void solve(int[][] initial, int[][] goal) {
        PriorityQueue<State> pq = new PriorityQueue<>();
        Set<String> visited = new HashSet<>();

        State initialState = new State(initial, goal, 0, null);
        pq.add(initialState);

        while (!pq.isEmpty()) {
            State current = pq.poll();
            visited.add(Arrays.deepToString(current.board));

            if (check(current.board, goal)) {
                // in ra duong di tu trang thai ban dau den trang thai dich
                List<State> path = new ArrayList<>();
                while (current != null) {
                    path.add(current);
                    current = current.parent;
                }
                Collections.reverse(path);
                for (State state : path) {
                    printBoard(state.board);
                    System.out.println();
                }
                return;
            }

            int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            int zeroRow = 0, zeroCol = 0;

            // Tìm vị trí của ô trống
            outerloop:
            for (int i = 0; i < current.board.length; i++) {
                for (int j = 0; j < current.board[i].length; j++) {
                    if (current.board[i][j] == 0) {
                        zeroRow = i;
                        zeroCol = j;
                        break outerloop;
                    }
                }
            }

            // Di chuyển ô trống và thêm trạng thái mới vào hàng đợi ưu tiên
            for (int[] dir : directions) {
                int newRow = zeroRow + dir[0];
                int newCol = zeroCol + dir[1];
                if (newRow >= 0 && newRow < current.board.length && newCol >= 0 && newCol < current.board[newRow].length) {
                    int[][] newBoard = new int[current.board.length][current.board[0].length];
                    for (int i = 0; i < current.board.length; i++) {
                        newBoard[i] = current.board[i].clone();
                    }
                    int temp = newBoard[newRow][newCol];
                    newBoard[newRow][newCol] = 0;
                    newBoard[zeroRow][zeroCol] = temp;
                    if (!visited.contains(Arrays.deepToString(newBoard))) {
                        pq.offer(new State(newBoard, goal, current.moves + 1, current));
                    }
                }
            }
        }
    }
}