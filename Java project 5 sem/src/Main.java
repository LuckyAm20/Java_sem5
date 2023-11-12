import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

class Node {
    char symbol;
    int freq;
    Node left = null, right = null;

    Node(char symbol_, int freq_) {
        symbol = symbol_;
        freq = freq_;
    }

    Node(char symbol_, int freq_, Node left_, Node right_) {
        symbol = symbol_;
        freq = freq_;
        left = left_;
        right = right_;
    }
}

class Huffman {
    public static void encode(Node root, String str, Map<Character, String> huffmanCode) {
        if (root == null) return;

        if (root.left == null && root.right == null) {
            huffmanCode.put(root.symbol, str);
        }

        encode(root.left, str + "0", huffmanCode);
        encode(root.right, str + "1", huffmanCode);
    }

    public static int decode(Node root, int index, StringBuilder sb) {
        if (root == null) return index;

        if (root.left == null && root.right == null) {
            System.out.print(root.symbol);
            return index;
        }

        return (sb.charAt(index + 1) == '0')
                ? decode(root.left, index + 1, sb)
                : decode(root.right, index + 1, sb);
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
        System.out.println("Huffman Codes are:");
        huffmanCode.forEach((key, value) -> System.out.println(key + " " + value));
    }

    public static void printOriginalString(String text) {
        System.out.println("\nOriginal string was:\n" + text);
    }

    public static void printEncodedString(StringBuilder sb) {
        System.out.println("\nEncoded string is:\n" + sb);
    }

    public static void printDecodedString(Node root, StringBuilder sb) {
        System.out.println("\nDecoded string is:\n");
        int index = -1;
        while (index < sb.length() - 2) {
            index = decode(root, index, sb);
        }
    }

    public static void main(String[] args) {
        String text = "I love programming in Java";

        Node root = buildHuffmanTree(text);

        Map<Character, String> huffmanCode = new HashMap<>();
        encode(root, "", huffmanCode);

        printHuffmanCodes(huffmanCode);
        printOriginalString(text);

        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            sb.append(huffmanCode.get(c));
        }

        printEncodedString(sb);

        printDecodedString(root, sb);
    }
}
