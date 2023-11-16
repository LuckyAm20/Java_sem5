import java.io.File;

public class FileUtility {

    static int getOriginalSize(String originalData) {
        return originalData.length();
    }

    public static long getCompressedSize(String filename) {
        File file = new File(filename);
        return file.length();
    }

    static String getFileExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return "";
        } else {
            return filename.substring(lastDotIndex + 1);
        }
    }

    static String getBaseFilename(String filename) {
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return filename;
        } else {
            return filename.substring(0, lastDotIndex);
        }
    }
}
