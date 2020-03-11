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
				Path actualDir = grammarDir.resolve("testfiles/actual");
				if (Files.exists(actualDir)) {
					Path expectedDir = grammarDir.resolve("testfiles/expected");
					addTests(actualDir, actualDir, expectedDir, rootTestSuite);
				}
			}
		} catch (IOException | DirectoryIteratorException e) {
			System.err.println(e);
		}
		return rootTestSuite;
	}

	private static void addTests(Path testFileOrDir, Path actualDir, Path expectedDir, TestSuite rootTestSuite) throws IOException {
		try (DirectoryStream<Path> streamTestFileOrDir = Files.newDirectoryStream(testFileOrDir)) {
			for (Path testFile : streamTestFileOrDir) {
				if (Files.isDirectory(testFile)) {
					addTests(testFile, actualDir, expectedDir, rootTestSuite);
				} else {
					rootTestSuite.addTest(new ParserTest(testFile, actualDir, expectedDir));
				}
			}
		}
	}
}
