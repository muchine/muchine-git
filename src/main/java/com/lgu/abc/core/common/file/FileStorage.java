package com.lgu.abc.core.common.file;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.common.configuration.AbcConfig;
import com.lgu.abc.core.common.file.exception.FileTransferException;
import com.lgu.abc.core.common.file.transfer.FileUpload;
import com.lgu.abc.core.plugin.file.FileUploadable;
import com.lgu.abc.core.prototype.org.user.User;

/**
 * Low-level interface class to read/write file from/to the physical storage.
 * @author Sejoon Lim
 * @since 2014. 2. 12.
 *
 */
@Component
public class FileStorage {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public static final String EXTENSION_TEMP = ".temp";
	
	@Autowired
	private AbcConfig configuration;
	
	/**
	 * Store a file into the storage as a temporary file.
	 * @param uploadable the interface how to control file upload behavior
	 * @param upload the representation of uploaded file
	 * @return the path on which the saved file is stored.
	 * @throws FileTransferException in the case when file transference to the storage is failed.
	 */
	public String save(FileUploadable uploadable, FileUpload upload) throws FileTransferException {
		final String location = uploadable.location(upload);
		final String path = path(uploadable, upload.getActor());
		
        final String filename = upload.getGenerator().generate();
        final String filepath = directory(location + path) + filename;
        
        logger.debug("saving file... path: " + filepath);
        
        File file = new File(filepath);
    	upload.transferTo(file);
    	
    	return filepath.replace(location, "");
	}
		
	private String path(FileUploadable uploadable, User actor) {
		return uploadable.path() + uploadable.classifiable().subpath(actor);
	}
		
	private String directory(String path) {
		File directory = new File(path);
        if (!directory.isDirectory()) createDirectory(directory);
        
        return path.endsWith("/") ? path : path + "/";
	}
	
	private synchronized void createDirectory(File directory) {
		if (directory.isDirectory()) return;
		if (!directory.mkdirs()) throw new RuntimeException("failed to make directory " + directory);	 
	}
		
}
