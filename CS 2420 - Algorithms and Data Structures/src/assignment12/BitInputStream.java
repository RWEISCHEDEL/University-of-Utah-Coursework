package assignment12;

import java.io.IOException;
import java.io.InputStream;

/**
 * From Weiss textbook, Chapter 12.
 * 
 */
public class BitInputStream {

  private InputStream in;

  private int buffer;

  private int bufferPos;

  final static int BITS_PER_BYTE = 8;

  public BitInputStream(InputStream is) {
    this.in = is;
    this.bufferPos = BITS_PER_BYTE;
  }

  public int readBit() throws IOException {
    if (this.bufferPos == BITS_PER_BYTE) {
      this.buffer = this.in.read();
      if (this.buffer == -1) {
        return -1;
      }
      this.bufferPos = 0;
    }

    return getBit(this.buffer, this.bufferPos++);
  }

  public void close() throws IOException {
    this.in.close();
  }

  private static int getBit(int pack, int pos) {
    return (pack & (1 << pos)) != 0 ? 1 : 0;
  }
}
