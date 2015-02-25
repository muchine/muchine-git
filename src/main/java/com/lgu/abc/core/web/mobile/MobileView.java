package com.lgu.abc.core.web.mobile;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import org.apache.commons.collections.CollectionUtils;

import com.lgu.abc.core.base.utils.IterableUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.io.naming.NameCoder;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.CompactWriter;

@XStreamAlias("DATA")
public @Data class MobileView {

	private static final XStream stream = new XStream();
	
	private static final NameCoder nameCoder = new NoNameCoder();
	
	static {
		stream.autodetectAnnotations(true);
	}
	
	@XStreamImplicit
	private final List<MobileItemView> contents = new ArrayList<MobileItemView>();
	
	public MobileView() {}
	
	public MobileView(MobileItemView content) {
		contents.add(content);
	}
	
	public <T extends MobileItemView> MobileView(Iterable<T> iterable) {
		if (IterableUtils.isEmpty(iterable)) return;
		CollectionUtils.addAll(contents, iterable.iterator());
	}
	
	public void add(MobileItemView content) {
		contents.add(content);
	}
	
	public String toXML() {
		StringWriter writer = new StringWriter();
		stream.marshal(this, new CompactWriter(writer, nameCoder));
		
		return writer.toString();
	}
	
}
