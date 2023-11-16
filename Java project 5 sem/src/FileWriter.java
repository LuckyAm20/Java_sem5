import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class FileWriter {

    static void saveToFile(String filename, Map<Character, String> huffmanCode, String data) throws IOException {
        File inputFile = new File(filename);
        String outputFilename;
        if (inputFile.getParent() != null) {
            outputFilename = inputFile.getParent() + File.separator + FileUtility.getBaseFilename(inputFile.getName()) + ".ark";
        } else {
            outputFilename = FileUtility.getBaseFilename(inputFile.getName()) + ".ark";
        }

        try (BitOutputStream bitOutputStream = new BitOutputStream(new FileOutputStream(outputFilename))) {
            String extension = FileUtility.getFileExtension(filename);
            for (int i = 0; i < extension.length(); i++) {
                char extensionChar = extension.charAt(i);
                bitOutputStream.writeBits(extensionChar, 8);
            }
            bitOutputStream.writeBits(0, 8);

            for (Map.Entry<Character, String> entry : huffmanCode.entrySet()) {
                char symbol = entry.getKey();
                String code = entry.getValue();
                bitOutputStream.writeBits(symbol, 8);
                bitOutputStream.writeBits(code.length(), 32);
                for (char bit : code.toCharArray()) {
                    bitOutputStream.writeBit(bit == '1');
                }
            }
            bitOutputStream.writeBits(0, 8);
            for (char c : data.toCharArray()) {
                bitOutputStream.writeBit(c == '1');
            }
        }
    }

    static void saveDecodedDataToFile(String filename, String defaultExtension, String decodedData) throws IOException {
        File inputFile = new File(filename);
        String outputFilename;
        if (inputFile.getParent() != null) {
            outputFilename = inputFile.getParent() + File.separator + FileUtility.getBaseFilename(inputFile.getName()) + "." + defaultExtension;
        } else {
            outputFilename = FileUtility.getBaseFilename(inputFile.getName()) + "." + defaultExtension;
        }


        try (BitOutputStream bitOutputStream = new BitOutputStream(new FileOutputStream(outputFilename))) {
            byte[] dataBytes = decodedData.getBytes();
            for (byte dataByte : dataBytes) {
                for (int i = 7; i >= 0; i--) {
                    bitOutputStream.writeBit((dataByte >> i & 1) == 1);
                }
            }
        }
    }
}
