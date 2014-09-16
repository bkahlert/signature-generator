package com.bkahlert.devel.signaturegenerator.ui.model;

import java.util.ArrayList;
import java.util.List;

import com.bkahlert.devel.signaturegenerator.model.IConfig;

public class ConfigList extends ArrayList<IConfig> implements List<IConfig> {
	private static final long serialVersionUID = 1L;

	public List<String> getNames() {
		List<String> names = new ArrayList<String>();
		for (IConfig config : this) {
			names.add(config.toString());
		}
		return names;
	}
}
