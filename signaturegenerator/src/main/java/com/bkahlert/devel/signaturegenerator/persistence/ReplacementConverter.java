package com.bkahlert.devel.signaturegenerator.persistence;

import com.bkahlert.devel.signaturegenerator.model.IReplacement;
import com.bkahlert.devel.signaturegenerator.model.impl.Replacement;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ReplacementConverter implements Converter {

	public boolean canConvert(@SuppressWarnings("rawtypes") Class clazz) {
		return clazz.equals(Replacement.class);
	}

	public void marshal(Object value, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		IReplacement replacement = (IReplacement) value;
		writer.addAttribute("name", replacement.getName());
		writer.setValue(replacement.getValue());
	}

	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		return new Replacement(reader.getAttribute("name"), reader.getValue());
	}

}