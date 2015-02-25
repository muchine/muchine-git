package com.lgu.abc.core.common.infra.file.support.icon;

public abstract class AbstractFileIcon implements FileIcon {

	private final String[] extensions;
	
	protected AbstractFileIcon(String[] supported) {
		this.extensions = supported;
	}

	@Override
	public boolean accept(String extension) {
		for (String supported : extensions) {
			if (supported.equals(extension)) return true;
		}
		
		return false;
	}

	@Override
	public String cssClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj.getClass().equals(getClass());
	}
	
}
