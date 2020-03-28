import java.io.*;
import qute.*;

public class QUTETest {
    static public void parseFile(File file, boolean dumpTree) throws IOException, ParseException {
        FileReader fr = new FileReader(file);
        QUTEParser parser = new QUTEParser(fr);
        parser.setInputSource(file.toString());
        Node rootNode = parser.Root();
        if (dumpTree) {
            Nodes.dump(rootNode, "");
        }
    }
 
    static public void main(String[] args) throws Exception {
      if (args.length == 0) {
        usage();
      }
      else {
        for (String arg :args) {
          File f = new File(arg);
          try {
            parseFile(f, true);
          }
          catch (Exception e) {
            System.err.println("Error parsing file: " + f);
            e.printStackTrace();
          }
        }
      }
    }

    static public void usage() {
      System.out.println("Little test harness for QUTE Parser");
      System.out.println("java QUTETest <filename>");
    }
}
