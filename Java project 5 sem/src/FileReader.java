import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileReader {
    public static String readFile(String filename) throws IOException {
        try (BitInputStream bitInputStream = new BitInputStream(new FileInputStream(filename))) {
            StringBuilder stringBuilder = new StringBuilder();
            int bit;
            while ((bit = bitInputStream.readBits(8)) != -1) {
                char character = (char) bit;
                stringBuilder.append(character);
            }
            return stringBuilder.toString();
        }
    }

    public static String readEncodedStringFromFile(BitInputStream bitInputStream, Map<Character, String> huffmanCode) throws IOException {
        StringBuilder content = new StringBuilder();
        int bit;
        StringBuilder currentCode = new StringBuilder();
        while ((bit = bitInputStream.readBit()) != -1) {
            currentCode.append(bit);
            for (Map.Entry<Character, String> entry : huffmanCode.entrySet()) {
                if (entry.getValue().contentEquals(currentCode)) {
                    content.append(entry.getKey());
                    currentCode = new StringBuilder();
                    break;
                }
            }
        }
        return content.toString();
    }

    static String readExtensionFromFile(BitInputStream bitInputStream) throws IOException {
        StringBuilder extensionBuilder = new StringBuilder();
        char extensionChar;
        while ((extensionChar = (char) bitInputStream.readBits(8)) != 0) {
            extensionBuilder.append(extensionChar);
        }
        return extensionBuilder.toString();
    }

    static Map<Character, String> readHuffmanCode(BitInputStream bitInputStream) throws IOException {
        Map<Character, String> huffmanCode = new HashMap<>();
        int symbol;
        while ((symbol = bitInputStream.readBits(8)) != 0) {
            int codeLength = bitInputStream.readBits(32);
            StringBuilder code = new StringBuilder();
            for (int i = 0; i < codeLength; i++) {
                code.append(bitInputStream.readBit());
            }
            huffmanCode.put((char) symbol, code.toString());
        }
        return huffmanCode;
    }
}
