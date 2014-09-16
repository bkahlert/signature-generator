package com.bkahlert.devel.signaturegenerator.model;

import java.io.File;
import java.io.IOException;

import com.bkahlert.devel.signaturegenerator.persistence.ConfigSerializer;

public class ConfigFactory {
	public static IConfig readFromFile(File configFile) throws IOException {
		return new ConfigSerializer().read(configFile);
	}
}
