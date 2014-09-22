package com.bkahlert.devel.signaturegenerator.model;

import java.util.List;

public interface IEntity {
	public String getName();
	
	/**
	 * All templates belonging to {@link #getName()} will also get generated for the username returned by this method.
	 * @return
	 */
	public String getAlias();

	public List<ITemplate> getTemplates();
}
