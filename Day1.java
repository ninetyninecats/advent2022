import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;

public class Day1 {

    public static void main (String[] args) throws IOException {
        var elves = new ArrayList<Integer>();
        int calories = 0;
        for (String line : Files.readAllLines(Path.of("input1.txt"))) {
            if (line.equals("")) {
                elves.add(calories);
                calories = 0;
            } else {
                calories += Integer.parseInt(line);
            }
        }
        if (calories > 0) elves.add(calories);
        System.out.println(Collections.max(elves));

        elves.sort((o1, o2) -> {
            if (o1 > o2) return -1;
            else if (o2 > o1) return 1;
            else return 0;
        });
        System.out.println(elves.subList(0 ,3).stream().reduce(0, (acc, elf) -> {
            return acc + elf;
        }));
    }
}