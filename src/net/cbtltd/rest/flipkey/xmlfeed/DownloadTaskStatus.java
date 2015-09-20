package net.cbtltd.rest.flipkey.xmlfeed;

public class DownloadTaskStatus {
	private boolean isDownloadSucess;
	private String fileName;

	public boolean isDownloadSucess() {
		return isDownloadSucess;
	}

	public void setDownloadSucess(boolean isDownloadSucess) {
		this.isDownloadSucess = isDownloadSucess;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
