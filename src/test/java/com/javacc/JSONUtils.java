package com.javacc;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.lang.reflect.Method;

public class JSONUtils {

	private static final String INDENT = "  ";

	static private String stringrep(Object n) {
		/*
		 * if (n instanceof Token) { return n.toString().trim(); }
		 */
		return n.getClass().getSimpleName();
	}

	public static void dump(Object n, String prefix, Writer writer) throws Exception {
		dump(n, prefix, writer, null);
	}

	public static void dump(Object n, String prefix, OutputStream out) throws Exception {
		dump(n, prefix, null, out);
	}

	private static void dump(Object n, String prefix, Writer writer, OutputStream out) throws Exception {
		String output = stringrep(n);
		writeln((prefix + output + ": {"), writer, out);
		// location
		int beginLine = getBeginLine(n);
		int beginColumn = getBeginColumn(n);
		int endLine = getEndLine(n);
		int endColumn = getEndColumn(n);
		writeln((prefix + INDENT + "location: (" + beginLine + "," + beginColumn + "," + endLine + "," + endColumn
				+ ")"), writer, out);
		// dirty
		boolean virtual = isVirtual(n);
		if (virtual) {
			writeln((prefix + INDENT + "virtual: " + virtual), writer, out);
		}
		// children
		int count = getChildCount(n);
		for (int i = 0; i < count; i++) {
			Object child = getChild(n, i);
			dump(child, prefix + INDENT, writer, out);
		}
		writeln(prefix + "}", writer, out);
	}

	private static int getBeginLine(Object n) throws Exception {
		Method method = n.getClass().getMethod("getBeginLine");
		return (int) method.invoke(n);
	}

	private static int getBeginColumn(Object n) throws Exception {
		Method method = n.getClass().getMethod("getBeginColumn");
		return (int) method.invoke(n);
	}

	private static int getEndLine(Object n) throws Exception {
		Method method = n.getClass().getMethod("getEndLine");
		return (int) method.invoke(n);
	}

	private static int getEndColumn(Object n) throws Exception {
		Method method = n.getClass().getMethod("getEndColumn");
		return (int) method.invoke(n);
	}

	private static boolean isVirtual(Object n) throws Exception {
		Method method = n.getClass().getMethod("isDirty");
		return (boolean) method.invoke(n);
	}

	private static Object getChild(Object n, int i) throws Exception {
		Method method = n.getClass().getMethod("getChild", new Class[] { int.class });
		return method.invoke(n, i);
	}

	private static int getChildCount(Object n) throws Exception {
		Method method = n.getClass().getMethod("getChildCount");
		return (int) method.invoke(n);
	}

	private static void write(String text, Writer writer, OutputStream out) throws IOException {
		if (writer != null) {
			writer.write(text);
		} else {
			out.write(text.getBytes());
		}
	}

	private static void writeln(String text, Writer writer, OutputStream out) throws IOException {
		write(text, writer, out);
		write(System.lineSeparator(), writer, out);
	}
}
