package com.lgu.abc.core.support.security.crypto.key;

import java.security.Key;

import com.lgu.abc.core.base.plugin.Sortable;

public interface AESKeyStore extends Sortable {

	Key getKey();
	
}
