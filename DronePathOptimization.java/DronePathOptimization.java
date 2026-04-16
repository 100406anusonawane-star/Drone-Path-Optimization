import java.util.*;

class Node {
    int row, col;
    Node parent;

    Node(int r, int c, Node p) {
        row = r;
        col = c;
        parent = p;
    }
}

public class DronePathOptimization {

    static int[] drow = {-1, 0, 1, 0};
    static int[] dcol = {0, 1, 0, -1};

    public static List<Node> shortestPath(int[][] grid, int sr, int sc, int dr, int dc) {
        int n = grid.length;
        int m = grid[0].length;

        boolean[][] vis = new boolean[n][m];
        Queue<Node> q = new LinkedList<>();

        q.add(new Node(sr, sc, null));
        vis[sr][sc] = true;

        while (!q.isEmpty()) {
            Node curr = q.poll();

            if (curr.row == dr && curr.col == dc) {
                return buildPath(curr);
            }

            for (int i = 0; i < 4; i++) {
                int nr = curr.row + drow[i];
                int nc = curr.col + dcol[i];

                if (nr >= 0 && nc >= 0 && nr < n && nc < m &&
                        grid[nr][nc] == 0 && !vis[nr][nc]) {

                    vis[nr][nc] = true;
                    q.add(new Node(nr, nc, curr));
                }
            }
        }

        return new ArrayList<>(); // no path
    }

    public static List<Node> buildPath(Node end) {
        List<Node> path = new ArrayList<>();
        Node curr = end;

        while (curr != null) {
            path.add(curr);
            curr = curr.parent;
        }

        Collections.reverse(path);
        return path;
    }

    public static void printGrid(int[][] grid) {
        System.out.println("Grid:");
        for (int[] row : grid) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter rows and columns: ");
        int n = sc.nextInt();
        int m = sc.nextInt();

        int[][] grid = new int[n][m];

        System.out.println("Enter grid (0 = free, 1 = obstacle):");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                grid[i][j] = sc.nextInt();
            }
        }

        System.out.print("Enter source (row col): ");
        int sr = sc.nextInt();
        int scCol = sc.nextInt();

        System.out.print("Enter destination (row col): ");
        int dr = sc.nextInt();
        int dc = sc.nextInt();

        printGrid(grid);

        List<Node> path = shortestPath(grid, sr, scCol, dr, dc);

        if (path.isEmpty()) {
            System.out.println("No path found!");
        } else {
            System.out.println("Shortest Path Length: " + (path.size() - 1));
            System.out.println("Path:");
            for (Node node : path) {
                System.out.print("(" + node.row + "," + node.col + ") ");
            }
        }

        sc.close();
    }
}