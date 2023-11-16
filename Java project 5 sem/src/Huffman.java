import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Huffman {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Use: java Huffman <encode/decode/info> <inputFile>");
            return;}

        String mode = args[0].toLowerCase();
        String inputFile = args[1];

        try {
            switch (mode) {
                case "encode" -> {
                    String text = FileReader.readFile(inputFile);
                    Node root = HuffmanEncoder.buildHuffmanTree(text);
                    Map<Character, String> huffmanCode = new HashMap<>();
                    HuffmanEncoder.generateHuffmanCodes(root, "", huffmanCode);
                    StringBuilder encodedString = new StringBuilder();
                    for (char c : text.toCharArray()) {
                        encodedString.append(huffmanCode.get(c));
                    }
                    FileWriter.saveToFile(inputFile, huffmanCode, encodedString.toString());
                }
                case "decode" -> {
                    BitInputStream bitInputStream = new BitInputStream(new FileInputStream(inputFile));
                    String defaultExtension = FileReader.readExtensionFromFile(bitInputStream);
                    Map<Character, String> huffmanCode = FileReader.readHuffmanCode(bitInputStream);
                    String decodedString = FileReader.readEncodedStringFromFile(bitInputStream, huffmanCode);
                    FileWriter.saveDecodedDataToFile(inputFile, defaultExtension, decodedString);
                }
                case "info" -> {
                    BitInputStream bitInputStream = new BitInputStream(new FileInputStream(inputFile));
                    FileReader.readExtensionFromFile(bitInputStream);
                    Map<Character, String> huffmanCode = FileReader.readHuffmanCode(bitInputStream);
                    String decodedString = FileReader.readEncodedStringFromFile(bitInputStream, huffmanCode);
                    int originalSize = FileUtility.getOriginalSize(decodedString);
                    long compressedSize = FileUtility.getCompressedSize(inputFile);
                    double compressionRatio = (double) compressedSize / originalSize;
                    HuffmanEncoder.printHuffmanCodes(huffmanCode);
                    System.out.println("Original size: " + originalSize + " bytes");
                    System.out.println("Compressed size: " + compressedSize + " bytes");
                    System.out.println("Compression ratio: " + compressionRatio * 100 + "%");
                }
                default -> System.out.println("Invalid mode. Use 'encode', 'decode', or 'info'.");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
