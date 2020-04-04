package com.javacc;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Assert;
import org.junit.runner.Describable;
import org.junit.runner.Description;

import junit.framework.Test;
import junit.framework.TestResult;

public class ParserTest implements Test, Describable {

	private final Path testFilesDir;

	private final Path actualDir;

	private final Path expectedDir;

	public ParserTest(Path testFilesDir, Path actualDir, Path expectedDir) {
		this.testFilesDir = testFilesDir;
		this.actualDir = actualDir;
		this.expectedDir = expectedDir;
	}

	@Override
	public Description getDescription() {
		String desc = testFilesDir.toString();
		return Description.createSuiteDescription(desc);
	}

	@Override
	public int countTestCases() {
		return 1;
	}

	@Override
	public void run(TestResult result) {
		try {
			result.startTest(this);
			executeTest(this, testFilesDir, actualDir, expectedDir);
		} catch (Throwable e) {
			result.addError(this, e);
		} finally {
			result.endTest(this);
		}
	}

	private static void executeTest(ParserTest test, Path testFile, Path actualDir, Path expectedDir) throws Exception {

		// Load parser instance
		String name = testFile.getName(1).toString();
		String className = name + "." + name.toUpperCase() + "Parser";

		Class parserClass = Class.forName(className);
		Constructor constructor = parserClass.getConstructor(new Class[] { java.io.Reader.class });

		String fileContent = new String(Files.readAllBytes(testFile), Charset.forName("UTF-8"));

		Reader reader = new StringReader(fileContent);
		Object parserInstance = constructor.newInstance(reader);

		// Parse the file
		Method rootMethod = parserClass.getMethod("Root");
		rootMethod.invoke(parserInstance);

		Method rootNodeMethod = parserClass.getMethod("rootNode");
		Object rootNode = rootNodeMethod.invoke(parserInstance);

		// Check AST
		Path path = actualDir.relativize(testFile);
		Path expectedFile = expectedDir.resolve(path);

		if (!Files.exists(expectedFile)) {
			Files.createDirectories(expectedFile.getParent());
			StringWriter actual = new StringWriter();
			JSONUtils.dump(rootNode, "", actual);
			Files.write(expectedFile, actual.toString().getBytes());
		}

		if (Files.exists(expectedFile)) {
			StringWriter actual = new StringWriter();
			JSONUtils.dump(rootNode, "", actual);
			String expectedContent = new String(Files.readAllBytes(expectedFile), Charset.forName("UTF-8"));
			Assert.assertEquals(expectedContent, actual.toString());
		}

	}

}
