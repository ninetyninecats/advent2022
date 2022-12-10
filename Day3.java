import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;

public class Day3 {

    static char findDuplicate (String c1, String c2) {
        for (int ii = 0; ii < c1.length(); ii ++) {
            var c = c1.charAt(ii);
            if (c2.indexOf(c) >= 0) return c;
        }
        return 0;
    }

    static char findTriplicate (String c1, String c2, String c3) {
        for (int ii = 0; ii < c1.length(); ii ++) {
            var c = c1.charAt(ii);
            if (c2.indexOf(c) >= 0 && c3.indexOf(c) >= 0) return c;
        }
        return 0;
    }

    static char findDuplicate (String line) {
        return findDuplicate(line.substring(0, line.length()/2), line.substring(line.length()/2));
    }
 
    static int priority (char c) {
        if (c > 64 && c <91) {
            return  c - 38;
        }
        else if (c > 96 && c < 123) {
            return c - 96;
        }
        return 0;
    }

    static int convert (List<String> lines, Function<String, Integer> op) {
        return lines.stream().map(op).reduce(0, (a, b) -> a+b);
    }

    public static void main (String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("input3.txt"));
        System.out.println(convert(lines, line -> priority(findDuplicate(line))));
        var total = 0;
        for (int ii = 0; ii < lines.size(); ii += 3) {
            var badge = findTriplicate(lines.get(ii), lines.get(ii + 1), lines.get(ii + 2));
            var p = priority(badge);
            total += p;
        }
        System.out.println(total);
    }
}
