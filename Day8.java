import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Day8 {

    static int[] parseRow (String line) {
        var bytes = line.getBytes();
        var nums = new int[bytes.length];
        for (var ii = 0; ii < bytes.length; ii += 1) {
            nums[ii] = bytes[ii] - '0';
        }
        return nums;
    }

    static boolean isVisible(int[][] grid, int tr, int tc, int dr, int dc) {
        int w = grid[0].length, h = grid.length;
        for (int c = tc + dc, r = tr + dr ; c >= 0 && c < w && r >=0 && r < h; c += dc , r += dr) {
            if (grid[r][c] >= grid[tr][tc]) {
                return false;
            }
        }
        return true;
    }

    static boolean isVisible (int[][] grid, int tr, int tc) {
        return isVisible(grid, tr, tc, -1, 0) ||
            isVisible(grid, tr, tc, 1, 0) ||
            isVisible(grid, tr, tc, 0, -1) ||
            isVisible(grid, tr, tc, 0, 1);
    }

    static int viewDistance(int[][] grid, int tr, int tc, int dr, int dc) {
        int w = grid[0].length, h = grid.length;
        var distance = 0;
        for (int c = tc + dc, r = tr + dr ; c >= 0 && c < w && r >=0 && r < h; c += dc , r += dr) {
             distance ++;
            if (grid[r][c] >= grid[tr][tc]) break;
        }
        return distance;
    }

    static int viewScore (int[][] grid, int tr, int tc) {
        return viewDistance(grid, tr, tc, -1, 0) *
            viewDistance(grid, tr, tc, 1, 0) *
            viewDistance(grid, tr, tc, 0, -1) *
            viewDistance(grid, tr, tc, 0, 1);
    }

    public static void main (String[] args) throws IOException {
        var lines = Files.readAllLines(Paths.get("input8.txt"));
        var grid = new int[lines.size()][];
        for (var ii = 0; ii < lines.size(); ii += 1) {
            grid[ii] = parseRow(lines.get(ii));
        }
        System.out.println(grid);

        var visible = 0;
        for (int r = 0 ; r < grid.length ; r++) {
            for (int c = 0 ; c < grid[r].length ; c++) {
                if (isVisible(grid, r, c)) {
                    visible ++;
                }
            }
        }
        System.out.println(visible);

        var max = 0;
        for (int r = 0 ; r < grid.length ; r++) {
            for (int c = 0 ; c < grid[r].length ; c++) {
                var score = viewScore(grid, r, c);
                if (score > max) {
                    max = score;
                }
            }
        }
        System.out.println(max);
    }
}
