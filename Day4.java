import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class Day4 {

    static class Assignment {
        int lower, higher;
        Assignment (int lower, int higher) {
            this.lower = lower;
            this.higher = higher;
        }
        boolean contains (Assignment o) {
            return lower <= o.lower && higher >= o.higher;
        }
        boolean overlaps (Assignment o) {
            return (lower <= o.lower && higher >= o.lower) || (lower <= o.higher && higher >= o.higher);
        }
        public String toString () {
            return lower + " - " + higher;
        }
    }
    
    static Assignment parse (String part) {
        var nums = part.split("-");
        return new Assignment(Integer.parseInt(nums[0]), Integer.parseInt(nums[1]));
    }

    static boolean check (String line, BiPredicate<Assignment, Assignment> op) {
        var parts = line.split(",");
        var a1 = parse(parts[0]);
        var a2 = parse(parts[1]);
        return op.test(a1, a2) || op.test(a2, a1);
    }

    static int count (List<String> lines, Predicate<String> op) {
        return (int)lines.stream().filter(op).count();
    }

    public static void main (String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("input4.txt"));
        System.out.println(count(lines, line -> check(line, (a1, a2) -> a1.contains(a2))));
        System.out.println(count(lines, line -> check(line, (a1, a2) -> a1.overlaps(a2))));
    }
}
