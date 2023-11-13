import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

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
        huffmanCode.forEach((key, value) -> {
             if (key == '\n')
             {
                System.out.println("\\n " + value);
             } else
             {
                 System.out.println(key + " " + value);
             }
        });
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
        if (args.length < 3) {
            System.out.println("Usage: java Huffman <encode/decode/info> <inputFile> <outputFile>");
            return;}

        String mode = args[0].toLowerCase();
        String inputFile = args[1];
        String outputFile = args[2];


        try {
            if (mode.equals("encode")) {
                String text = readFile(inputFile);
                Node root = buildHuffmanTree(text);
                Map<Character, String> huffmanCode = new HashMap<>();
                encode(root, "", huffmanCode);
                printHuffmanCodes(huffmanCode);
                StringBuilder encodedString = new StringBuilder();
                for (char c : text.toCharArray()) {
                    encodedString.append(huffmanCode.get(c));
                }
                saveToFile(outputFile, huffmanCode, encodedString.toString());
            } else if (mode.equals("decode")) {
                Map<Character, String> huffmanCode = readHuffmanCodeFromFile(inputFile);
                String encodedString = readEncodedStringFromFile(inputFile);
                Node root = buildHuffmanTreeFromCode(huffmanCode);
                StringBuilder decodedString = decodeString(root, encodedString);
                saveDecodedDataToFile(outputFile, decodedString.toString());
            } else if (mode.equals("info")) {
                Map<Character, String> huffmanCode = readHuffmanCodeFromFile(inputFile);
                Node root = buildHuffmanTreeFromCode(huffmanCode);
                String encodedString = readEncodedStringFromFile(inputFile);
                int originalSize = getOriginalSize(decodeString(root, encodedString).toString());
                int compressedSize = getCompressedSize(encodedString);
                double compressionRatio = (double) compressedSize / originalSize;
                printHuffmanCodes(huffmanCode);
                System.out.println("Original size: " + originalSize + " bytes");
                System.out.println("Compressed size: " + compressedSize + " bytes");
                System.out.println("Compression ratio: " + compressionRatio * 100 + "%");
            } else {
                System.out.println("Invalid mode. Use 'encode', 'decode', or 'info'.");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static String readFile(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            StringBuilder content = new StringBuilder();
            int character;
            boolean isNewLine = false;

            while ((character = reader.read()) != -1) {
                char currentChar = (char) character;
                if (currentChar != '\r') {
                    content.append(currentChar);}

                if (currentChar == '\n') {
                    isNewLine = true;
                } else if (isNewLine) {
                    content.append('\n');
                    isNewLine = false;
                }
            }
            return content.toString();
        }
    }

    private static void saveToFile(String filename, Map<Character, String> huffmanCode, String data) throws IOException {
        try (PrintWriter writer = new PrintWriter(filename)) {
            for (Map.Entry<Character, String> entry : huffmanCode.entrySet()) {

                if (entry.getKey() == '\n')
                {
                    writer.println("\\n " + entry.getValue());
                } else
                {
                    writer.println(entry.getKey() + " " + entry.getValue());
                }
            }
            writer.println();

            writer.print(data);
        }
    }

    private static void saveDecodedDataToFile(String filename, String decodedData) throws IOException {
        try (PrintWriter writer = new PrintWriter(filename)) {
            writer.print(decodedData);
        }
    }

    private static Map<Character, String> readHuffmanCodeFromFile(String filename) throws IOException {
        Map<Character, String> huffmanCode = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                char key;
                String value;
                if (line.startsWith("\\n"))
                {
                    key = '\n';
                    value = line.substring(3);
                } else {
                    key = line.charAt(0);
                    value = line.substring(2);
                }


                huffmanCode.put(key, value);
            }
        }
        return huffmanCode;
    }

    private static String readEncodedStringFromFile(String filename) throws IOException {
        StringBuilder encodedString = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            boolean readingData = false;
            String line;
            while ((line = reader.readLine()) != null) {
                if (readingData) {
                    encodedString.append(line);
                } else if (line.isEmpty()) {
                    readingData = true;
                }
            }
        }
        return encodedString.toString();
    }

    private static Node buildHuffmanTreeFromCode(Map<Character, String> huffmanCode) {
        Node root = new Node('\0', 0);
        for (Map.Entry<Character, String> entry : huffmanCode.entrySet()) {
            char symbol = entry.getKey();
            String code = entry.getValue();
            Node currentNode = root;
            for (char bit : code.toCharArray()) {
                if (bit == '0') {
                    if (currentNode.left == null) {
                        currentNode.left = new Node('\0', 0);
                    }
                    currentNode = currentNode.left;
                } else if (bit == '1') {
                    if (currentNode.right == null) {
                        currentNode.right = new Node('\0', 0);
                    }
                    currentNode = currentNode.right;
                }
            }
            currentNode.symbol = symbol;
        }
        return root;
    }

    private static StringBuilder decodeString(Node root, String encodedString) {
        StringBuilder decodedString = new StringBuilder();
        Node currentNode = root;
        for (char bit : encodedString.toCharArray()) {
            if (bit == '0') {
                currentNode = currentNode.left;
            } else if (bit == '1') {
                currentNode = currentNode.right;
            }
            if (currentNode.left == null && currentNode.right == null) {
                decodedString.append(currentNode.symbol);
                currentNode = root;
            }
        }
        return decodedString;
    }

    private static int getOriginalSize(String originalData) {
        return originalData.length() * 8;
    }

    private static int getCompressedSize(String encodedString) {
        return encodedString.length();
    }
}

