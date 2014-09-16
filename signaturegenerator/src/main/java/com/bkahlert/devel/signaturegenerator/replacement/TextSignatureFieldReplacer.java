package com.bkahlert.devel.signaturegenerator.replacement;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.bkahlert.devel.signaturegenerator.model.IReplacement;

public class TextSignatureFieldReplacer implements ISignatureFieldReplacer {

	protected File file;

	public TextSignatureFieldReplacer(File file) {
		assert file != null;
		this.file = file;
	}

	public String getEncoding() {
		return "UTF-16";
	}

	protected String getKeyPattern(String key) {
		return "\\$\\{" + key + "}";
	}

	protected String getReplacement(String value) {
		return value;
	}

	protected String fuseReplacements(List<String> strings) {
		return StringUtils.join(strings, "\n");
	}

	public String replace(List<IReplacement> replacements)
			throws ReplacementException {
		try {
			String content = FileUtils.readFileToString(this.file,
					this.getEncoding());

			Map<String, List<String>> r = new HashMap<String, List<String>>();
			for (IReplacement replacement : replacements) {
				String name = replacement.getName();
				String value = replacement.getValue();

				if (!r.containsKey(name))
					r.put(name, new LinkedList<String>());
				r.get(name).add(value);
			}

			for (String key : r.keySet()) {
				content = content.replaceAll(this.getKeyPattern(key),
						this.getReplacement(this.fuseReplacements(r.get(key))));
			}

			return content;
		} catch (IOException e) {
			throw new ReplacementException(e);
		}
	}

	public void replaceTo(List<IReplacement> replacements, File to)
			throws ReplacementException {
		String content = replace(replacements);
		try {
			FileUtils.writeStringToFile(to, content, this.getEncoding());
		} catch (IOException e) {
			throw new ReplacementException(e);
		}
	}

}
