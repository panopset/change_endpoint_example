package com.panopset.api;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

public abstract class TextResourceToFile {
	
	/**
	 * Apply transformation to each line.
	 * @param s String to transform.
	 * @return Transformed String.
	 */
	protected abstract String transform(final String s);

	/**
	 * Input lines.
	 */
	private final List<String> lines;

	/**
	 * Windows/DOS return character.
	 */
	private static final String DOS_RTN = "" + (char) 13 + (char) 10;

	/**
	 * 
	 * @param file
	 */
	public void write(final File file) {
		List<String> s = new ArrayList<String>();
		for (String t : lines) {
			s.add(transform(t));
		}
		try {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			IOUtils.writeLines(s, DOS_RTN, bw);
			bw.flush();
			bw.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * @param resourcePath
	 *            Resource path.
	 */
	public TextResourceToFile(final String resourcePath) {
		try {
			lines = IOUtils.readLines(getClass().getClassLoader()
					.getResourceAsStream(resourcePath));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
