package com.bkahlert.devel.signaturegenerator.model.impl;

import java.text.Normalizer;
import java.util.List;

import com.bkahlert.devel.signaturegenerator.model.IReplacement;
import com.bkahlert.devel.signaturegenerator.model.ITemplate;

public class Template implements ITemplate {

	private String name;
	private List<IReplacement> replacements;

	@SuppressWarnings("unused")
	private Template() {
		this.name = null;
		this.replacements = null;
	}
	
	public Template(String name, List<IReplacement> replacements) {
		super();
		this.name = name;
		this.replacements = replacements;
	}

	public String getName() {
		return Normalizer.normalize(this.name, Normalizer.Form.NFC);
	}

	public List<IReplacement> getReplacements() {
		return this.replacements;
	}

}
