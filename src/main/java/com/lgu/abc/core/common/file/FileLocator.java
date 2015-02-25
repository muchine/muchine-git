package com.lgu.abc.core.common.file;

import com.lgu.abc.core.common.configuration.AbcConfig;
import com.lgu.abc.core.common.configuration.upload.file.FileLocationEnvironment;

public class FileLocator {

	public static String location(boolean large) {
		final AbcConfig configuration = AbcConfig.instance();
		final FileLocationEnvironment location = configuration.upload().file().location();
		
		/*
		 * NOTE For now, large volume file is stored in the same place with normal volume file. The design was changed because
		 * we can't know whether the file should be regarded as large volume file when it is uploaded to the server. Instead,
		 * the large volume flag is determined when the file is confirmed. Therefore, large volume files do not need to be stored
		 *  in a separate location from the one where normal volume files are stored.
		 */
//		return large ? location.large() : location.permanent();
		return location.permanent();
	}
	
}
