package assignment12;

import java.io.IOException;
import java.io.OutputStream;

/**
 * From Weiss textbook, Chapter 12.
 * 
 */
public class BitOutputStream {

  private OutputStream out;

  private int buffer;

  private int bufferPos;

  final static int BITS_PER_BYTE = 8;

  public BitOutputStream(OutputStream os) {
    this.bufferPos = 0;
    this.buffer = 0;
    this.out = os;
  }

  public void writeBit(int val) throws IOException {
    this.buffer = setBit(this.buffer, this.bufferPos++, val);
    if (this.bufferPos == BITS_PER_BYTE)
      flush();
  }

  public void writeBits(int[] val) throws IOException {
    for (int i = 0; i < val.length; i++) 
      writeBit(val[i]);
  }

  public void flush() throws IOException {
    if (this.bufferPos == 0)
      return;

    this.out.write(this.buffer);
    this.bufferPos = 0;
    this.buffer = 0;
  }

  public void close() throws IOException {
    flush();
    this.out.close();
  }

  private int setBit(int pack, int pos, int val) {
    if (val == 1)
      pack |= (val << pos);
    return pack;
  }
}
