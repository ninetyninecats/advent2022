import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;

public class Day2 {
    enum Move { Rock, Paper, Scissors; }

    static Move Player1 (String line) {
        switch (line.charAt(0)) {
            case 'A': return Move.Rock;
            case 'B': return Move.Paper;
            case 'C': return Move.Scissors;
            default: throw new AssertionError();
        }
    }

    static Move Player2 (String line) {
        switch (line.charAt(2)) {
            case 'X': return Move.Rock;
            case 'Y': return Move.Paper;
            case 'Z': return Move.Scissors;
            default: throw new AssertionError();
        }
    }

    enum Goal { Lose, Draw, Win; }

    static Goal Goal (String line) {
        switch (line.charAt(2)) {
            case 'X': return Goal.Lose;
            case 'Y': return Goal.Draw;
            case 'Z': return Goal.Win;
            default: throw new AssertionError();
        }
    }

    static int Score (Move p1, Move p2) {
        switch (p1) {
            default:
            case Rock:
            switch (p2) {
                default:
                case Rock: return 3 + 1;
                case Paper: return 0 + 1;
                case Scissors: return 6 + 1;
            }
            case Paper:
            switch (p2) {
                default:
                case Rock: return 6 + 2;
                case Paper: return 3 + 2;
                case Scissors: return 0 + 2;
            }
            case Scissors:
            switch (p2) {
                default:
                case Rock: return 0 + 3;
                case Paper: return 6 + 3;
                case Scissors: return 3 + 3;
            }
        }
    }

    static Move PickMove (Move p1, Goal p2) {
        switch (p2) {
            default:
            case Lose:
            switch (p1) {
                default:
                case Rock: return Move.Scissors;
                case Paper: return Move.Rock;
                case Scissors: return Move.Paper;
            }
            case Draw:
            return p1;
            case Win:
            switch (p1) {
                default:
                case Rock: return Move.Paper;
                case Paper: return Move.Scissors;
                case Scissors: return Move.Rock;
            }
        }
    }

    static int Convert (List<String> lines, Function<String, Integer> op) {
        return lines.stream().map(op).reduce(0, (a, b) -> a+b);
    }

    public static void main (String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("input2.txt"));
        System.out.println(Convert(lines, line -> Score(Player2(line), Player1(line))));
        System.out.println(Convert(lines, line -> Score(PickMove(Player1(line), Goal(line)), Player1(line))));
    }
}
