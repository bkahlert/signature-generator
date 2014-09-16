package com.bkahlert.devel.signaturegenerator.replacement;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import com.bkahlert.devel.signaturegenerator.model.IReplacement;

public class HtmlSignatureFieldReplacer extends TextSignatureFieldReplacer {

	public HtmlSignatureFieldReplacer(File file) {
		super(file);
	}

	@Override
	public String getEncoding() {
		return "windows-1252";
	}

	@Override
	protected String fuseReplacements(List<String> strings) {
		return StringUtils.join(strings, "<br/>");
	}

	@Override
	public void replaceTo(List<IReplacement> replacements, final File to)
			throws ReplacementException {
		super.replaceTo(replacements, to);

		// also copy the asset directory
		for (File srcDir : this.file.getParentFile().listFiles(
				new FilenameFilter() {
					public boolean accept(File dir, String name) {
						String currentBaseName = FilenameUtils
								.getBaseName(name);
						String toBaseName = FilenameUtils.getBaseName(to
								.getName());
						return currentBaseName.startsWith(toBaseName)
								&& new File(dir, currentBaseName).isDirectory();
					}
				})) {
			try {
				File destDir = new File(to.getParentFile(),
						FilenameUtils.getBaseName(srcDir.toString()));
				FileUtils.copyDirectory(srcDir, destDir);
			} catch (IOException e) {
				throw new ReplacementException(e);
			}
		}
	}

}
