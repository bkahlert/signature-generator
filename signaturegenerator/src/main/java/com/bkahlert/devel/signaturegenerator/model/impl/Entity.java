package com.bkahlert.devel.signaturegenerator.model.impl;

import java.util.List;

import com.bkahlert.devel.signaturegenerator.model.IEntity;
import com.bkahlert.devel.signaturegenerator.model.ITemplate;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class Entity implements IEntity {

	@XStreamAsAttribute
	private String name;
	
	@XStreamAsAttribute
	private String alias;
	
	private List<ITemplate> templates;
	
	@SuppressWarnings("unused")
	private Entity() {
		this.name = null;
		this.alias = null;
		this.templates = null;
	}

	public Entity(String name, List<ITemplate> templates) {
		super();
		this.name = name;
		this.templates = templates;
	}

	public String getName() {
		return this.name;
	}
	
	public String getAlias() {
		return this.alias;
	}

	public List<ITemplate> getTemplates() {
		return this.templates;
	}

	@Override
	public String toString() {
		return this.getName();
	}

}
