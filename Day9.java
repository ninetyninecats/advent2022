import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Day9 {

    public static class Knot {
        int x, y;

        public Knot (int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        public void move (int dx, int dy) {
            x += dx;
            y += dy;
        }

        public Knot copy () { return new Knot(x, y); }
    
        public void follow (int ox, int oy) {
            int lx = ox - x, ly = oy - y;
            if (Math.abs(lx) <= 1 && Math.abs(ly) <= 1) return;
            if (ly == 0) {
                x += lx / 2;
            }
            else if (lx == 0) {
                y += ly / 2;
            }
            else {
                x += Math.signum(lx);
                y += Math.signum(ly);
            }
        }

        public boolean equals (Object other) {
            return ((Knot)other).x == x && ((Knot)other).y == y;
        }

        public int hashCode () { return x ^ y; }

        public String toString () { return x + "/" + y; }
    }

    static void move (Knot head, Knot tail, int dx, int dy, int count, HashSet<Knot> visited) {
        for ( var ii = 0; ii < count; ii ++) {
            head.move(dx,dy);
            tail.follow(head.x, head.y);
            visited.add(tail.copy());
        }
    }

    static void move (List<Knot> rope, int dx, int dy, int count, HashSet<Knot> visited) {
        for ( var ii = 0; ii < count; ii ++) {
            rope.get(0).move(dx,dy);
            for (var kk = 1; kk < rope.size(); kk ++) {
                var prev = rope.get(kk - 1);
                rope.get(kk).follow(prev.x, prev.y);
            }
            visited.add(rope.get(rope.size() -1).copy());
        }
    }

    interface Op {
        void apply (int dx, int dy, int count);
    }
    
    static void process (List<String> lines, Op op) {
        for (String line : lines) {
            var count = Integer.parseInt(line.substring(2));
            switch (line.charAt(0)) {
                case 'U': op.apply(0, -1, count); break;
                case 'D': op.apply(0, 1, count); break;
                case 'L': op.apply(-1, 0, count); break;
                case 'R': op.apply(1, 0, count); break;
            }
        }

    }
    public static void main (String[] args) throws IOException {
        Knot head = new Knot(0, 0), tail = new Knot(0, 0);
        var visited = new HashSet<Knot>();
        visited.add(tail.copy());
        process(Files.readAllLines(Path.of("input9.txt")), (dx, dy, count) -> {
            move(head, tail, dx, dy, count, visited);
        });
        System.out.println(visited.size());
 
        var visited2 = new HashSet<Knot>();
        var rope = new ArrayList<Knot>();
        for (var kk = 0; kk <= 9; kk ++) rope.add(new Knot(0, 0));
        process(Files.readAllLines(Path.of("input9.txt")), (dx, dy, count) -> {
            move(rope, dx, dy, count, visited2);
        });
        System.out.println(visited2.size());
    }
}