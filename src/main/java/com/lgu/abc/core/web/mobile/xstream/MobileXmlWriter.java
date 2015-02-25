package com.lgu.abc.core.web.mobile.xstream;

import java.io.Writer;

import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.naming.NameCoder;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;

public class MobileXmlWriter extends PrettyPrintWriter {

	public MobileXmlWriter(Writer writer) {
		super(writer);
	}
	
	public MobileXmlWriter(Writer writer, NameCoder nameCoder) {
		super(writer, nameCoder);
	}

	@Override
	protected void writeText(QuickWriter writer, String text) {
		if (text.indexOf('<') < 0) {
            writer.write(text);
        }
        else { 
            writer.write("<[CDATA["); writer.write(text); writer.write("]]>"); 
        }
	}

}
