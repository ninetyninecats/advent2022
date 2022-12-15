import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day6 {

    static boolean isMarker (String input, int start, int length) {
        for (var ss = 0 ; ss < length-1 ; ss ++) {
            for (var ee = ss + 1 ; ee < length ; ee ++) {
                if (input.charAt(start+ss) == input.charAt(start+ee)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    static int findMarker (String input, int length) {
        for (var ii = 0 ; ii < input.length() - length ; ii ++) {
            if (isMarker(input, ii, length)) return ii + length;
        }
        return -1;
    }

    public static void main (String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("input6.txt"));
        System.out.println(findMarker(lines.get(0), 4));
        System.out.println(findMarker(lines.get(0), 14));
    }
    }
