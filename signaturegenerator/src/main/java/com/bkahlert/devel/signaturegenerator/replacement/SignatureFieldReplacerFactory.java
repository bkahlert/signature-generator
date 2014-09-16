package com.bkahlert.devel.signaturegenerator.replacement;

import java.io.File;

public class SignatureFieldReplacerFactory {
	public static ISignatureFieldReplacer createSignatureFieldReplacer(File file) {
		if (file.toString().endsWith(".txt"))
			return new TextSignatureFieldReplacer(file);
		if (file.toString().endsWith(".rtf"))
			return new RtfSignatureFieldReplacer(file);
		if (file.toString().endsWith(".html"))
			return new HtmlSignatureFieldReplacer(file);
		if (file.toString().endsWith(".htm"))
			return new HtmlSignatureFieldReplacer(file);
		return null;
	}
}
