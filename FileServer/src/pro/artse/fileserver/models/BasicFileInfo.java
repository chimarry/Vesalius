package pro.artse.fileserver.models;

import java.io.Serializable;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class BasicFileInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String fileName;
	private int sizeInBytes;
	private LocalDateTime savedOn;

	public BasicFileInfo() {

	}

	public BasicFileInfo(String fileName, long sizeInBytes, FileTime savedOn) {
		this.fileName = fileName;
		this.sizeInBytes = (int) sizeInBytes;
		this.savedOn = LocalDateTime.ofInstant(savedOn.toInstant(), ZoneId.systemDefault());
	}

	public BasicFileInfo(String fileName, int sizeInBytes) {
		this.fileName = fileName;
		this.sizeInBytes = sizeInBytes;
		this.savedOn = LocalDateTime.now();
	}

	public BasicFileInfo(String fileName, int sizeInBytes, LocalDateTime savedOn) {
		this.fileName = fileName;
		this.sizeInBytes = sizeInBytes;
		this.savedOn = savedOn;
	}

	public BasicFileInfo(String name) {
		this.fileName = name;
		this.savedOn = LocalDateTime.now();
	}

	public LocalDateTime getSavedOn() {
		return savedOn;
	}

	public void setSavedOn(LocalDateTime savedOn) {
		this.savedOn = savedOn;
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
}
