import java.io.*;
import qute.*;

public class QUTETest {

    static public void parseFile(File file, boolean dumpTree) throws IOException, ParseException {
        FileReader fr = new FileReader(file);
        QUTEParser parser = new QUTEParser(fr);
        parser.setInputSource(file.toString());
//        parser.setTracingEnabled(true);
        parser.Root();
        Node rootNode = parser.rootNode();
        if (dumpTree) {
            rootNode.dump();
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
