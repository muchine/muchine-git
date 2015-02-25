var FileUploadManager = {
	
	/**
	 * @param context.url : a url to send a save request to
	 * @param context.ui.containerId : an id of container where file uploader is bound.
	 * @param context.ui.buttonName : uploader button label
	 * @param context.ui.loading : whether to show loading image
	 * @param context.validator validation function for upload
	 * @param context.success a handler function when upload is successful
	 * @param context.error a handler function when upload fails
	 */
	bindBaseLocalUploader : function(context) {
		return $("#" + context.ui.containerId).kendoUpload({
			localization : {
				select : context.ui.buttonName,
                headerStatusUploading: "",
                headerStatusUploaded: "",
				dropFilesHere : "파일을 이곳에 놓으세요"
			},
			showFileList : false,
			async : {
				saveUrl : context.url
			},
			upload : function(e) {
		    	$(".k-widget.k-upload.k-header").addClass("k-upload-empty");
		        $(".k-dropzone div").removeClass("k-state-focused");
		        
		       if (context.ui.loading) showLoading();
		    },
			select : function(e) {
				if (context.validator && !context.validator(e.files)) {
					e.preventDefault();
					return;
				}
				abcUploadFileForm.onSelect(e);
			},
			progress: abcUploadFileForm.onProgress,
			success : function(e) {
				var response = JSON.parse(e.XMLHttpRequest.responseText);
				if (context.success) {
					context.success(response);
				}
				
				$(".k-widget.k-upload.k-header").addClass("k-upload-empty");
	            $(".k-dropzone div").removeClass("k-state-focused");
				$(".k-upload .k-upload-status-total").remove();
			},
			error : function(e) {
				if (context.error) {
					context.error(e);
				}
			},
		    complete : function() {
		    	if (context.ui.loading) hideLoading();
		    },
			uploaded: false
		});
	},
	
	/**
	 * @param context.type : module type
	 * @param context.ui.containerId : an id of container where file uploader is bound.
	 * @param context.ui.buttonName : uploader button label
	 * @param context.ui.loading : whether to show loading image
	 * @param context.validator validation function for upload
	 * @param context.success a handler function when upload is successful
	 * @param context.error a handler function when upload fails
	 */
	bindSingleLocalUploader : function(context) {
		context.url = "/common/file/upload?type=" + context.type;
		return FileUploadManager.bindBaseLocalUploader(context);
	},
	
	/**
	 * @param context.type : module type
	 * @param context.ui.containerId : an id of container where file uploader is bound.
	 * @param context.ui.loading : whether to show loading image
	 * @param context.validator validator takes a file json object as a parameter. A file object has name and size properties.
	 */
	bindLocalPCUploader : function(context) {
		// Disable drag & drop
		kendo.ui.Upload.fn._supportsDrop = function() { return false; };
		
		context.ui.buttonName = "내PC";
		context.success = FileUploadPainter.paintUploadedResult;
		FileUploadManager.bindSingleLocalUploader(context).closest('.k-button').removeClass('k-button');
		
		$("#" + context.ui.containerId).parent().removeClass("k-button k-upload-button");
	},
	
	bindUboxUploader : function(buttonId, containerId, query) {
		$("#" + buttonId).click(function(e) {
			FileUploadManager.paintUboxUploader(containerId, query);
		});
	},
	
	bindWebdiskUploader : function(buttonId, containerId, query) {
		$("#" + buttonId).click(function() {			
			$("#" + containerId).kendoWindow({
				title : "웹디스크",
				modal : true,
				resizable : false,
				content : "/doc/finder.jstl?target=window" + query,
				width : 800,
				height : 425,
				close : function() {}
			}).data('kendoWindow').center().open();
		});
	},
	
	paintUboxUploader : function(containerId, query) {
		$("#" + containerId).kendoWindow({
			title : "U+ BOX",
			modal : true,
			resizable : false,
			content : "/common/ubox/upload.jstl?" + query,
			width : 650,
			height : 450,
			close : function() {}
		}).data('kendoWindow').center().open();
	},
	
	validateExcel : function(files) {
		for (var i = 0; i < files.length; i++) {
			var extension = files[i].extension.toLowerCase();
			if (extension != ".xls" && extension != ".xlsx") {
				layCmn.alert({
					msg : "엑셀 파일만 첨부 가능 합니다.",
					callback : function() {}
				});
				
				return false;
			}
		}
		
		return true;
	},
	
	validateImage : function(files) {
		for (var i = 0; i < files.length; i++) {
			if (!abcUploadFileForm.isImageFilename(files[i].name)) {
				layCmn.alert({
					msg : "이미지 파일만 첨부 가능 합니다.",
					callback : function() {}
				});
				
				return false;
			}
		}

		return true;
	}
};

var FileUploadPainter = {
	/*
	 	appendable : 파일을 계속 append할지 여부 (true | false)
		fileViewId : 첨부된 파일이 표시될 div 아이디
		imageViewId : 첨부된 이미지가 표시될 div 아이디
		fileIdsContainerId : 첨부파일 ID가 콤마로 구분되어 저장될 input 요소의 아이디
	 */
	context : {},
	
	isFileAttached : function() {
		/*
		 * All of attched file should be have a 'fileIcon' class. Need to consider about more precise criteria.
		 */
		return $("#" + FileUploadPainter.context.fileViewId + " .fileIcon").length > 0;
	},
		
	initialize : function() {
		if (!FileUploadPainter.isAppendable() || !FileUploadPainter.isFileAttached()) {
			$("#" + FileUploadPainter.context.fileViewId).html("");
		}
	},
		
	// 파일 첨부 완료 후 화면 업데이트 메소드 
	// @Deprecated
	paintUploadedFiles : function(result) {
		FileUploadPainter.initialize();
		
		var fileIds = new Array();
		for (var i = 0; i < result.length; i++) {
			FileUploadPainter.paintUploadedFile(
					result[i].temp_atch_file_seq, 
					result[i].temp_atch_file_nm, 
					result[i].temp_atch_file_vol, 
					result[i].atch_imgfile_path);
			
			fileIds.push(result[i].temp_atch_file_seq);
		}
		
		FileUploadPainter.setCommaDelimitedFileIds(fileIds);
		FileUploadPainter.addUploadedCount(result.length);
	},
	
	/**
	 * Paint only one file that is returned as upload result. This method is used by local PC uploader. 
	 * @param file.id : 파일의 ID
	 * @param file.property.name : 파일 이름
	 * @param file.volume.size : 파일 크기
	 * @param file.url : 파일 이미지 URL
	 */
	paintUploadedResult : function(file) {
		FileUploadPainter.initialize();
		
		var fileIds = new Array();
		FileUploadPainter.paintUploadedFile(
				file.id, 
				file.property.name, 
				file.volume.size, 
				file.url);
		
		fileIds.push(file.id);
		
		FileUploadPainter.setCommaDelimitedFileIds(fileIds);
		FileUploadPainter.addUploadedCount(1);
	},
	
	setCommaDelimitedFileIds : function(fileIds) {
		var commaDelimitedFileIds = "";
		if (FileUploadPainter.isAppendable()) {
			commaDelimitedFileIds = $("#" + FileUploadPainter.context.fileIdsContainerId).val();
			if (commaDelimitedFileIds == '0') commaDelimitedFileIds = "";
		}

		if (commaDelimitedFileIds != "") {
			commaDelimitedFileIds += "," + fileIds;
		} else {
			commaDelimitedFileIds = fileIds;
		}

		$("#" + FileUploadPainter.context.fileIdsContainerId).val(commaDelimitedFileIds);
	},
	
	addUploadedCount : function(uploadedCount) {
		var count = $("#uploadFileCntText").text();
		count = parseInt(count);
		$("#uploadFileCntText").html(count + uploadedCount);
	},
		
	paintUploadedFile : function(id, name, size, url) {
		var html = '<div class="fileBox" data-fileseq="' + id + '" data-filenm="'
				+ name + '" data-filevol="'	+ abcUploadFileForm.convertSize(size + "") + '">';
		html += '<div class="fileName F_12_black">';
		html += abcUploadFileForm.convertIcon(name) + " " + name + "("
				+ abcUploadFileForm.convertSize(size + "") + ")";
		html += "<span onclick='abcUploadFileForm.removeTempFile(this," + id + ", \""
				+ FileUploadPainter.context.fileIdsContainerId + "\")' class='o-icon o-listDelete floatR'></span>";
		html += "</div>";
		html += "</div>";

		$("#" + FileUploadPainter.context.fileViewId).append(html);
		if (FileUploadPainter.context.imageViewId) {
			$("#" + FileUploadPainter.context.imageViewId).attr("src", url);
		}
	},
	
	isAppendable : function() {
		return FileUploadPainter.context.appendable;
	}
};
