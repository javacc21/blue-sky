package javacc;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.runner.RunWith;
import org.junit.runners.AllTests;

import junit.framework.TestSuite;

@RunWith(AllTests.class)
public class ParserSuiteTest {

	private static final Path REPO_ROOT = Paths.get("grammars");

	public static TestSuite suite() throws Exception {
		TestSuite rootTestSuite = new TestSuite();
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(REPO_ROOT)) {
			for (Path grammarDir : stream) {
				Path testfiles = grammarDir.resolve("testfiles");
				if (Files.exists(testfiles)) {
					try (DirectoryStream<Path> streamTestFiles = Files.newDirectoryStream(testfiles)) {
						for (Path testFile : streamTestFiles) {
							if (!Files.isDirectory(testFile)) {
								rootTestSuite.addTest(new ParserTest(testFile));
							}
						}
					}
				}
			}
		} catch (IOException | DirectoryIteratorException e) {
			System.err.println(e);
		}
		return rootTestSuite;
	}
}
