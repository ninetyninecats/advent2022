import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Day5 {
    static class Move {
        int count;
        int fromStack;
        int toStack;
    }

    static Move parseMove (String line) {
        var parts = line.split(" ");
        var move = new Move();
        move.count = Integer.parseInt(parts[1]);
        move.fromStack = Integer.parseInt(parts[3]);
        move.toStack = Integer.parseInt(parts[5]);
        return move;
    }

    static String topCrates (ArrayList<ArrayList<Character>> crates) {
        var sb = new StringBuilder();
        for (var stack : crates) sb.append(stack.get(0));
        return sb.toString();
    }

    public static void main (String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("input5.txt"));
        var crates = new ArrayList<ArrayList<Character>>();
        var moves = new ArrayList<Move>();
        var stacks = true;

        for (var line : lines) {
            if (line.equals("")) stacks = false;
            else if (stacks) {
                for (int ii = 1, cc = 0; ii < line.length(); ii += 4, cc += 1) {
                    if (crates.size() <= cc) crates.add(new ArrayList<Character>());
                    var c = line.charAt(ii);
                    if (c != ' ') {
                        crates.get(cc).add(c);
                    } 
                }
            }
            else {
                moves.add(parseMove(line));
            }
        }

        for (var move : moves) {
            var fromStack = crates.get(move.fromStack - 1);
            var toStack = crates.get(move.toStack - 1);
            for (int ii = 0 ; ii < move.count ; ii ++) {
                var top = fromStack.get(0);
                fromStack.remove(0);
                toStack.add(ii, top);
            }
        }

        System.out.println(topCrates(crates));
    }
}
