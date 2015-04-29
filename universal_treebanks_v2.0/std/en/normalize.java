import java.io.*;
import java.util.*;
import org.apache.commons.lang3.StringEscapeUtils;

public class normalize {
  public static void main(String[] args) throws IOException {
    BufferedReader map = new BufferedReader(new FileReader(args[1]));
    HashMap<String, String> upos_map = new HashMap<String, String>();
    String line = map.readLine();
    while (line != null) {
      String[] pair = line.split("\t");
      upos_map.put(pair[0], pair[1]);
      line = map.readLine();
    }

    BufferedReader in = new BufferedReader(new FileReader(args[0]));
    line = in.readLine();
    while (line != null) {
      if (line.trim().length() == 0) {
        System.out.println();
        line = in.readLine();
        continue;
      }

      String[] fields = line.split("\t");

      // Quick normalization
      NormalizeWords(fields);
      NormalizeTags(fields);
      fields[3] = upos_map.get(fields[4]); // Add universal categories
      NormalizeLabels(fields);

      // Generate new string.
      line = "";
      for (int i = 0; i < fields.length; ++i) {
        line += fields[i] + "\t";
      }
      System.out.println(line.trim());
      line = in.readLine();
    }
  }

  public static void NormalizeWords(String[] fields) {
    fields[1] = StringEscapeUtils.unescapeJava(fields[1]);
    if (fields[1].equals("-LRB-")) {
      fields[1] = "(";
    } else if (fields[1].equals("-RRB-")) {
      fields[1] = ")";
    } else if (fields[1].equals("-LCB-")) {
      fields[1] = "{";
    } else if (fields[1].equals("-RCB-")) {
      fields[1] = "}";
    } else if (fields[1].equals("-LSB-")) {
      fields[1] = "[";
    } else if (fields[1].equals("-RSB-")) {
      fields[1] = "]";
    }
  }

  public static void NormalizeLabels(String[] fields) {
    if (fields[7].equals("null")) {
      fields[7] = "ROOT";
    } else if (fields[7].equals("root")) {
      fields[7] = "ROOT";
    } else if (fields[7].equals("punct")) {
      fields[7] = "p";
    } else if (fields[7].equals("possessive")) {
      fields[7] = "adp";
    } else if (fields[7].equals("abbrev")) {
      fields[7] = "appos";
    } else if (fields[7].equals("number")) {
      fields[7] = "num";
    }  else if (fields[7].equals("npadvmod")) {
      fields[7] = "nmod";
    } else if (fields[7].equals("prep")) {
      fields[7] = "adpmod";
    } else if (fields[7].equals("pobj")) {
      fields[7] = "adpobj";
    } else if (fields[7].equals("pcomp")) {
      fields[7] = "adpcomp";
    } else if (fields[7].equals("purpcl")) {
      fields[7] = "advcl";
    } else if (fields[7].equals("tmod")) {
      if (fields[3].equals("NOUN") || fields[3].equals("NUM")
          || fields[3].equals("PRON") || fields[3].equals("X")) {
        fields[7] = "nmod";
      } else if (fields[3].equals("ADV")) {
        fields[7] = "advmod";
      } else if (fields[3].equals("ADJ")) {
        fields[7] = "amod";
      } else if (fields[3].equals("ADP")) {
        fields[7] = "adpmod";
      } else if (fields[3].equals("VERB")) {
        fields[7] = "advcl";
      }
    } else if (fields[7].equals("quantmod")) {
      fields[7] = "advmod";
    } else if (fields[7].equals("complm")) {
      fields[7] = "mark";
    } else if (fields[7].equals("predet")) {
      fields[7] = "det";
    } else if (fields[7].equals("preconj")) {
      fields[7] = "cc";
    } else if (fields[7].equals("nn")) {
      fields[7] = "compmod";
    } else if (fields[7].equals("ps")) {
      fields[7] = "adp";
    }
  }

  public static void NormalizeTags(String[] fields) {
    if (fields[4].equals("-LRB-")) {
      fields[4] = "(";
    } else if (fields[4].equals("-RRB-")) {
      fields[4] = ")";
    } else if (fields[4].equals("TO") && fields[1].toLowerCase().equals("to")) {
      fields[4] = "IN";
    }

    // Fix up incorrect "to" particles
    if (fields[4].equals("IN") && fields[1].toLowerCase().equals("to")
        && (fields[7].equals("aux") || fields[7].equals("xcomp")
            || fields[7].equals("ccomp"))) {
      fields[4] = "TO";
    }
  }
}
