public class GenerateJavaIdentifierDef {

	static public void main(String[] args) {
		System.out.println("TOKEN :");
        System.out.println("  <#JAVA_IDENTIFIER_START :");
        System.out.println("    [");
		outputRanges(0, 0x10ffff, true);
		System.out.println("\n    ]");
		System.out.println("\n  >");
		System.out.println("  |");
        System.out.println("  <#JAVA_IDENTIFIER_PART :");
        System.out.println("    [");
		outputRanges(0, 0x10ffff, false);
		System.out.println("\n    ]");
		System.out.println("  >");
		System.out.println(";");
	}

	static void outputRanges(int start, int end, boolean justStart) {
		int lhs=start;
		boolean firstLine = true;
		for (int ch = start+1; ch<=end ;ch++) {
			boolean prevID = justStart ? Character.isJavaIdentifierStart(ch-1) : Character.isJavaIdentifierPart(ch-1);
			boolean currentID = justStart ? Character.isJavaIdentifierStart(ch) : Character.isJavaIdentifierPart(ch);
			if (prevID != currentID) {
				if (currentID) {
					lhs = ch;
				} else {
					if (!firstLine) {
						System.out.print(",\n");
					}
					firstLine = false;
					outputRange(lhs, ch-1);
				}
			}
		}
	}

	static void outputRange(int left, int right) {
		System.out.print("        ");
		String output = toUnicodeRep(left);
		if (left != right) {
			output += "-";
			output += toUnicodeRep(right);
		}
		System.out.print(output);
//		if (left > 0xFFFF) {
//			System.out.print(" // " + Integer.toHexString(left));
//			if (left != right) System.out.print("-" + Integer.toHexString(right));
//		}
	}

	static String toUnicodeRep(int ch) {
		if (ch <= 0xFFFF) {
			String hex = Integer.toString(ch, 16);
			int leadingZeros = 4-hex.length();
			switch (leadingZeros) {
				case 1 : hex = "0" + hex; break;
				case 2 : hex = "00" +hex; break;
				case 3 : hex = "000" + hex;
			}
			return "\"\\u" + hex + "\"";
	    }
		char high = Character.highSurrogate(ch);
		char low = Character.lowSurrogate(ch);
		String highRep = toUnicodeRep(high);
		String lowRep = toUnicodeRep(low);
		return highRep.substring(0, highRep.length()-1) + lowRep.substring(1, lowRep.length());
	}
}
