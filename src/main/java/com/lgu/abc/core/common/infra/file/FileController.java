package com.lgu.abc.core.common.infra.file;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import com.lgu.abc.core.base.controller.AbstractHomeController;
import com.lgu.abc.core.common.file.FileManager;
import com.lgu.abc.core.common.file.download.FileResource;
import com.lgu.abc.core.common.file.download.transmit.FileTransmitter;
import com.lgu.abc.core.common.file.transfer.FileUpload;
import com.lgu.abc.core.common.file.transfer.MultipartFileTransference;
import com.lgu.abc.core.common.infra.file.support.FileFinder;
import com.lgu.abc.core.common.infra.file.view.CKEditorFileUploadCommand;
import com.lgu.abc.core.common.infra.file.view.FileUploadCommand;
import com.lgu.abc.core.web.support.WebUtils;

@Controller
@RequestMapping("/common/file")
public class FileController extends AbstractHomeController {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public static final String UPLOAD_PARAM = "upload";
	
	private static final String EXTERNAL_DOWNLOAD_LINK = "/common/file/download/external";
	
	@Autowired
	private FileManager manager;
	
	@Autowired
	private FileFinder finder;
	
	/*
	 * Fixed a bug that file download dialog is shown after upload in IE8. This is caused by application/json content type in http response.
	 * Therefore, do not declare @ResponseBody here. 
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/upload")
	@ResponseStatus(HttpStatus.OK)
	public void upload(@RequestParam(UPLOAD_PARAM) MultipartFile file, @ModelAttribute FileUploadCommand command, 
			BindingResult result, HttpServletResponse response) throws Exception {
		File uploaded = doUpload(file, command);
		WebUtils.createJsonResponse(response, uploaded);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/upload/ckeditor")
	public String uploadFromCKEditor(Model model, @RequestParam(UPLOAD_PARAM) MultipartFile file, 
			@ModelAttribute CKEditorFileUploadCommand command, BindingResult result) throws Exception {
		Image uploaded = new Image(doUpload(file, command));
				
		model.addAttribute("CKEditorFuncNum", command.getCKEditorFuncNum());
		model.addAttribute("imageURL", uploaded.getUrl());
		
		return "/common/editor/ckeditor/ckimageupload.jstl";
	}
	
	private File doUpload(MultipartFile file, FileUploadCommand command) {
		final FileUpload upload = new FileUpload(actor(), new MultipartFileTransference(file), command.isLarge());
		return manager.upload(command.getType(), upload);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/download/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public FileSystemResource download(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
		FileResource downloaded = manager.download(id, actor());
		return createResponse(downloaded, request, response);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/download/external/{link}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public FileSystemResource downloadByExternalLink(@PathVariable String link,  HttpServletRequest request,HttpServletResponse response) {
		File file = finder.findByLink(link);
		logger.debug("found file: " + file);
		
		/*
		 * NOTE We access a file instance directly here because download() method of FileManager has additional functionalities
		 * related to security. At this time it's not a good idea to put a direct file access method into FileManager. It might
		 * confuse developers in a sense that they don't know which method should be used in some cases.
		 */
		java.io.File content = file == null ? null : new java.io.File(file.fullpath());
		return createResponse(new FileResource(file, content), request, response);
	}
	
	private FileSystemResource createResponse(FileResource downloaded, HttpServletRequest request, HttpServletResponse response) {
		if (downloaded == null || !downloaded.exists()) return null;
		logger.debug("full path: " + downloaded.file().fullpath() + ", content exists: " + downloaded.hasContent());
		
		FileTransmitter transmitter = new FileTransmitter(response);
		transmitter.setFileName(downloaded.file().name(), request);

		return new FileSystemResource(downloaded.content());
	}
	
	public static String getExternalDownloadUrl() {
		return EXTERNAL_DOWNLOAD_LINK;
	}

}
