import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
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
    }

   // "12-34" 
    static Assignment parse (String part) {
        var nums = part.split("-");
        return new Assignment(Integer.parseInt(nums[0]), Integer.parseInt(nums[1]));
    }

    static boolean overlaps (String line) {
        var parts = line.split(",");
        var a1 = parse(parts[0]);
        var a2 = parse(parts[1]);

        return a1.contains(a2) || a2.contains(a1);
    }

    static int count (List<String> lines, Predicate<String> op) {
        return (int)lines.stream().filter(op).count();
    }

    public static void main (String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("input4.txt"));
        System.out.println(count(lines, line -> overlaps(line)));
    }
}
