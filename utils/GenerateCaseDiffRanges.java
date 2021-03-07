public class GenerateCaseDiffRanges {

	static public void main(String[] args) {
		System.out.println("Lower case diff ranges");
		System.out.println("----------------");
		outputRanges(0, 0x10FFFF, false);
		System.out.println("----------------");
		System.out.println("Now upper case diffs");
		System.out.println("----------------");
		outputRanges(0, 0x10FFFF, true);
	}

	static void outputRanges(int start, int end, boolean upper) {
		boolean inRange = false;
		int left = start;
		for (int ch=start; ch<=end; ch++) {
			boolean diff = upper? (ch != Character.toUpperCase(ch)) : (ch!=Character.toLowerCase(ch));
			if (diff) {
				if (inRange) continue;
				left = ch;
				inRange = true;
			} else {
				if (!inRange) continue;
				outputRange(left, ch -1);
				inRange = false;
			}
		}
	}

	static void outputRange(int left, int right) {
		System.out.println("" + left + ", " + right + ",");
	}
}
