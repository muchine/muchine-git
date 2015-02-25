package com.lgu.abc.core.common.file.validation.spec;

import com.lgu.abc.core.common.file.validation.Volume;
import com.lgu.abc.core.common.spec.Specification;
import com.lgu.abc.core.prototype.org.company.Company;
import com.lgu.abc.core.prototype.org.user.User;

public class FileSpecificationFactory {
	
	public static Specification office(Company company, Volume volume) {
		return new UploadedFileSpecification()
			.and(new CompanyFileSpecification(company))
			.and(new FileSizeSpecification(volume.getAttachedFileSize()))
			.and(new FileSizeSumSpecification(volume.getTotalAttachedFileSize()))
			.and(new AvailableSpaceSpecification(volume.getAvailableFileSize()));
	}
	
	public static Specification basic(Company company) {
		return new UploadedFileSpecification()
			.and(new CompanyFileSpecification(company));
	}
	
	public static Specification download(User actor) {
		return new CompanyFileSpecification(actor.getCompany());
	}
	
}
