import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public class BitInputStream implements Closeable {
    private final InputStream inputStream;
    private int currentByte;
    private int numBitsRemaining;

    public BitInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
        this.currentByte = 0;
        this.numBitsRemaining = 0;
    }

    public int readBit() throws IOException {
        if (numBitsRemaining == 0) {
            int nextByte = inputStream.read();
            if (nextByte == -1) {
                return -1;
            }
            currentByte = nextByte;
            numBitsRemaining = 8;
        }
        int bit = (currentByte >> (numBitsRemaining - 1)) & 1;
        numBitsRemaining--;
        return bit;
    }

    public int readBits(int numBits) throws IOException {
        int value = 0;
        for (int i = 0; i < numBits; i++) {
            int bit = readBit();
            if (bit == -1) {
                return -1;
            }
            value = (value << 1) | bit;
        }
        return value;
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
    }
}
