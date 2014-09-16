package com.bkahlert.devel.signaturegenerator.replacement;

import java.io.File;
import java.util.List;

import com.bkahlert.devel.signaturegenerator.model.IReplacement;

public interface ISignatureFieldReplacer {
	public String getEncoding();

	public String replace(List<IReplacement> replacements)
			throws ReplacementException;

	public void replaceTo(List<IReplacement> replacements, File to)
			throws ReplacementException;
}
