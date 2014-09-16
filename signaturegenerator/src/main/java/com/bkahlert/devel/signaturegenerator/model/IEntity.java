package com.bkahlert.devel.signaturegenerator.model;

import java.util.List;

public interface IEntity {
	public String getName();

	public List<ITemplate> getTemplates();
}
