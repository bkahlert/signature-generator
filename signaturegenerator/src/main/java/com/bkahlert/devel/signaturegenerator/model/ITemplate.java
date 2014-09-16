package com.bkahlert.devel.signaturegenerator.model;

import java.util.List;

public interface ITemplate {
	public String getName();

	public List<IReplacement> getReplacements();
}
