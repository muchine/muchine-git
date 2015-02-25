package com.lgu.abc.core.support.security.crypto.key;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.lgu.abc.core.base.plugin.SortableComparator;

@Component
public class KeyStoreFactory {

	private List<AESKeyStore> stores = new ArrayList<AESKeyStore>();
	
	public synchronized void add(AESKeyStore store) {
		stores.add(store);
		Collections.sort(stores, SortableComparator.instance());
	}
	
	public AESKeyStore getStore() {
		return stores.get(0);
	}
	
}
