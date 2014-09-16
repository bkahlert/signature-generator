package com.bkahlert.devel.signaturegenerator.replacement;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class RtfSignatureFieldReplacer extends TextSignatureFieldReplacer {

	public RtfSignatureFieldReplacer(File file) {
		super(file);
	}

	@Override
	public String getEncoding() {
		return "windows-1252";
	}

	@Override
	protected String fuseReplacements(List<String> strings) {
		return StringUtils.join(strings, "\\\\par ");
	}

	protected String getKeyPattern(String key) {
		return "\\$\\\\\\{" + key + "\\\\}";
	}

}
