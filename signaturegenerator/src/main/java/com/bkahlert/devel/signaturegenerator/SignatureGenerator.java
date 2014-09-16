package com.bkahlert.devel.signaturegenerator;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

import com.bkahlert.devel.signaturegenerator.model.IConfig;
import com.bkahlert.devel.signaturegenerator.model.IEntity;
import com.bkahlert.devel.signaturegenerator.model.ITemplate;
import com.bkahlert.devel.signaturegenerator.replacement.ISignatureFieldReplacer;
import com.bkahlert.devel.signaturegenerator.replacement.ReplacementException;
import com.bkahlert.devel.signaturegenerator.replacement.SignatureFieldReplacerFactory;

public class SignatureGenerator {

	private static final Logger LOGGER = Logger
			.getLogger(SignatureGenerator.class);

	private File signatureDirectory;
	private IConfig config;

	/**
	 * Creates a new {@link SignatureGenerator} operating with the given
	 * {@link IConfig} and the signature templates found in the given directory.
	 * 
	 * @param signatureDirectory
	 * @param config
	 */
	public SignatureGenerator(File signatureDirectory, IConfig config) {
		super();
		assert signatureDirectory != null;
		assert config != null;
		this.signatureDirectory = signatureDirectory;
		this.config = config;

		// TODO LDAP support
		// PersonDao x = new PersonDaoImpl(new LdapTemplate(config));
		// x.getAllPersons();
	}

	/**
	 * Creates all signatures and puts them into the given output directory.
	 * 
	 * @param outputDirectory
	 */
	public void createSignatures(File outputDirectory) {
		this.createSignatures(outputDirectory, null);
	}

	/**
	 * Creates all given signatures and puts them into the given output
	 * directory.
	 * 
	 * @param outputDirectory
	 * @param entities
	 *            if null or empty all signatures are created.
	 */
	public void createSignatures(File outputDirectory, String[] entities) {
		for (IEntity entity : config.getEntities()) {
			if (entities != null && entities.length > 0
					&& !ArrayUtils.contains(entities, entity.getName()))
				continue;

			File entityDirectory = new File(outputDirectory, entity.getName());
			for (final ITemplate template : entity.getTemplates()) {
				boolean found = false;
				for (String signatureFile : this.signatureDirectory
						.list(new FilenameFilter() {
							public boolean accept(File dir, String name) {
								return name.startsWith(template.getName() + ".");
							}
						})) {
					found = true;
					ISignatureFieldReplacer signatureFieldReplacer = SignatureFieldReplacerFactory
							.createSignatureFieldReplacer(new File(
									signatureDirectory, signatureFile));
					if (signatureFieldReplacer == null)
						continue;

					try {
						File replacedSignatureFile = new File(entityDirectory,
								signatureFile);
						signatureFieldReplacer.replaceTo(
								template.getReplacements(),
								replacedSignatureFile);
						LOGGER.info("Successfully created signature \""
								+ template.getName()
								+ "\" ["
								+ FilenameUtils.getExtension(signatureFile)
										.toUpperCase() + "] for \""
								+ entity.getName() + "\"");
					} catch (ReplacementException e) {
						LOGGER.error("Error replacing signature file "
								+ signatureFile, e);
					}
				}
				if (!found) {
					LOGGER.warn("Could not create signature \""
							+ template.getName()
							+ "\" for \""
							+ entity.getName()
							+ "\" since the template signature could not be found.");
				}
			}
		}
	}

	public static File[] getConfigFiles(File directory) {
		return directory.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.isFile() && pathname.canRead()
						&& pathname.toString().endsWith(".xml");
			}
		});
	}
}
