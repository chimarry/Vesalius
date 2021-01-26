package pro.artse.user.models;

import java.time.LocalDateTime;

public class MedicalDocument {

	private String fileName;
	private int sizeInBytes;
	private LocalDateTime createdOn;

	public MedicalDocument() {
	}

	public MedicalDocument(String fileName, int size, LocalDateTime createdOn) {
		this.fileName = fileName;
		this.sizeInBytes = size;
		this.createdOn = createdOn;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getSizeInBytes() {
		return sizeInBytes;
	}

	public void setSizeInBytes(int sizeInBytes) {
		this.sizeInBytes = sizeInBytes;
	}

	public LocalDateTime getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}
}
