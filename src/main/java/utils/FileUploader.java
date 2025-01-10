package utils;

import java.io.IOException;
import java.util.UUID;

import configs.Config;
import jakarta.servlet.http.Part;

public class FileUploader {
	public static String save(Part part) throws IOException {
		String fileName = UUID.randomUUID().toString() + part.getSubmittedFileName();
		part.write(Config.UPLOAD_PATH + fileName);
		return fileName;
	}
}
