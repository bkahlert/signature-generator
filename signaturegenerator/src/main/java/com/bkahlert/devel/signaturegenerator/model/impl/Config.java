package com.bkahlert.devel.signaturegenerator.model.impl;

import java.util.List;

import com.bkahlert.devel.signaturegenerator.model.IConfig;
import com.bkahlert.devel.signaturegenerator.model.IEntity;

public class Config implements IConfig {

	private List<IEntity> entities;
	
	@SuppressWarnings("unused")
	private Config() {
		this.entities = null;
	}

	public Config(List<IEntity> entities) {
		this.entities = entities;
	}

	public List<IEntity> getEntities() {
		return this.entities;
	}

}
