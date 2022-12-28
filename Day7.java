import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.function.BiFunction;

public class Day7 {

    static abstract class Node {
        abstract int totalSize ();
        abstract int reduce (int z, BiFunction<Integer, Node, Integer> f);
    }

    static class Directory extends Node {
        HashMap<String, Node> entries = new HashMap<>();
        Directory parent;

        void addFile (String name, int size) {
            var file = new File();
            file.size = size;
            entries.put(name, file);
        }

        void addDirectory (String name) {
            var directory = new Directory();
            directory.parent = this;
            entries.put(name, directory);
        }

        int totalSize () {
            var total = 0;
            for (var node : entries.values()) {
                total += node.totalSize();
            }
            return total;
        }

        int reduce (int z, BiFunction<Integer, Node, Integer> f) {
            z = f.apply(z, this);
            for (var node : entries.values()) {
                z = node.reduce(z, f);
            }
            return z;
        }
    }

    static class File extends Node {
        int size;
        int totalSize () { return size; }
        int reduce (int z, BiFunction<Integer, Node, Integer> f) {
            return f.apply(z, this);
        }
    }

    public static void main (String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("input7.txt"));
        var root = new Directory();
        var cwd = root;
        for (var line : lines) {
            if (line.startsWith("$ cd ")) {
                var name = line.substring(5);
                if (name.equals("/")) cwd = root;
                else if (name.equals("..")) cwd = cwd.parent;
                else cwd = (Directory)cwd.entries.get(name);
            }
            else if (line.equals("$ ls")) {}
            else if (line.startsWith("dir ")) {
                var name = line.substring(4);
                cwd.addDirectory(name);
            }
            else {
                var parts = line.split(" ");
                cwd.addFile(parts[1], Integer.parseInt(parts[0]));
            }
        }
        System.out.println(root.reduce(0, (z, node) -> {
            if (node instanceof Directory) {
                var size = ((Directory)node).totalSize();
                if (size <= 100000) {
                    z += size;
                }
            }
            return z;
        }));

        var used = root.totalSize();
        var free = 70000000 - used;
        var needed = 30000000 - free;
        System.out.println(root.reduce(used, (z, node) -> {
            if (node instanceof Directory) {
                var size = ((Directory)node).totalSize();
                if (size < needed) return z;
                if (size < z) return size;
            }
            return z;
        }));
    }
}

