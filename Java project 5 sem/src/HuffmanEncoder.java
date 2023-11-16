import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanEncoder {
    public static void generateHuffmanCodes(Node root, String str, Map<Character, String> huffmanCode) {
        if (root == null) return;

        if (root.left == null && root.right == null) {
            huffmanCode.put(root.symbol, str);
        }

        generateHuffmanCodes(root.left, str + "0", huffmanCode);
        generateHuffmanCodes(root.right, str + "1", huffmanCode);
    }
    public static Node buildHuffmanTree(String text) {
        Map<Character, Integer> freq = new HashMap<>();
        for (char c : text.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(l -> l.freq));

        freq.forEach((key, value) -> pq.add(new Node(key, value)));

        while (pq.size() != 1) {
            Node left = pq.poll();
            Node right = pq.poll();

            int sum = (left != null ? left.freq : 0) + (right != null ? right.freq : 0);
            pq.add(new Node('\0', sum, left, right));
        }
        return pq.peek();
    }

    public static void printHuffmanCodes(Map<Character, String> huffmanCode) {
        System.out.println("Huffman Tree:");
        huffmanCode.forEach((key, value) -> {
            if (key == '\n') {
                System.out.println("\\n " + value);
            } else {
                System.out.println(key + " " + value);
            }
        });
    }
}
