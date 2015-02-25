package com.lgu.abc.core.support.security;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class Base64Decoder implements Decoder {

	@Override
	public String decode(String encoded) {
		if (encoded == null) return null;
		
		return new String(Base64.decodeBase64(encoded));
	}
	
	public static void main(String[] args) {
		Decoder decoder = new Base64Decoder();
		
		String text = "2";
		
		String encoded = new String(Base64.encodeBase64(text.getBytes()));
		System.out.println(encoded);
		
		String decoded = decoder.decode(encoded);
		System.out.println(decoded);
	}
	
}
