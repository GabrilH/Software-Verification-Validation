package slides;

import java.io.DataInput;
import java.io.IOException;

public class LineCounter {
  /**
   * Count number of lines read from a <code>DataInput</code> instance, with the option
   * of ignoring blank lines.
   * @param in <code>DataInput</code> instance
   * @param ignoreBlankLines Ignore blanks.
   * @throws IOException Produced eventually by access to <code>in</code> object.
   */
  public static int count(DataInput in, boolean ignoreBlankLines) throws IOException {
    int count = 0;
    String line;
    while ((line = in.readLine()) != null) {
      if (line.length() > 0 || !ignoreBlankLines) { 
        count ++;
      }
    }
    return count;
  }
}

