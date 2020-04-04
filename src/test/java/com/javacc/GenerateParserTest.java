package com.javacc;

import java.nio.file.Path;

import org.junit.runner.Describable;
import org.junit.runner.Description;

import junit.framework.Test;
import junit.framework.TestResult;

public class GenerateParserTest implements Test, Describable {

	private final Path grammarDir;

	public GenerateParserTest(Path grammarDir) {
		this.grammarDir = grammarDir;
	}

	@Override
	public Description getDescription() {
		String desc = grammarDir.toString();
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
			executeTest(this, grammarDir);
		} catch (Throwable e) {
			result.addError(this, e);
		} finally {
			result.endTest(this);
		}
	}

	private static void executeTest(GenerateParserTest test, Path grammarDir) throws Exception {
		// Generate parser
		String name = grammarDir.getName(grammarDir.getNameCount() - 1).toString();
		String grammar = name.toUpperCase() + ".javacc";
		Path grammarFilePath = grammarDir.resolve(grammar);
		String[] args = new String[] { grammarFilePath.toString() };
		com.javacc.Main.mainProgram(args);
	}

}
