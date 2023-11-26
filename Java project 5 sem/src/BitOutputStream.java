import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

public class BitOutputStream implements Closeable {
    private final OutputStream outputStream;
    private int currentByte;
    private int numBitsFilled;

    public BitOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.currentByte = 0;
        this.numBitsFilled = 0;
    }

    public void writeBit(boolean bit) throws IOException {

        currentByte = (currentByte << 1) | (bit ? 1 : 0);
        numBitsFilled++;

        if (numBitsFilled == 8) {
            outputStream.write(currentByte);
            currentByte = 0;
            numBitsFilled = 0;
        }
    }

    public void writeBits(int value, int numBits) throws IOException {
        for (int i = numBits - 1; i >= 0; i--) {
            boolean bit = ((value >> i) & 1) == 1;
            writeBit(bit);
        }
    }

    @Override
    public void close() throws IOException {
        while (numBitsFilled != 0) {
            writeBit(true);
        }
        outputStream.close();
    }
}
