package vttp2023.batch3.csf.assessment.cnserver.repositories;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

@Repository
public class ImageRepository {

	@Autowired
	private AmazonS3 s3;

	@Value("${s3.bucket.bucketname}")
	private String bucketName;
	
	// TODO: Task 1
	public String upload(String title, String description, MultipartFile file) throws IOException {

		Map<String, String> userData = new HashMap<>();
		userData.put("title", title);
		userData.put("description", description);

		try (InputStream is = new ByteArrayInputStream(file.getBytes())) {
			String fileName = file.getName();
			String contentType = getContentType(fileName);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len;
			while ((len = is.read(buffer)) > 0) {
				baos.write(buffer, 0, len);
			}
			byte[] bytes = baos.toByteArray();
			InputStream inputStream = new ByteArrayInputStream(bytes);
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType(contentType);
			metadata.setContentLength(bytes.length);
			metadata.setUserMetadata(userData);

			PutObjectRequest putReq = new PutObjectRequest(bucketName, fileName, inputStream, metadata);
			putReq = putReq.withCannedAcl(CannedAccessControlList.PublicRead);
			PutObjectResult putObjectResult = s3.putObject(putReq);

			String url = s3.getUrl(bucketName, fileName).toString();
			return url;
		} 
	}

	private String getContentType(String fileName) {
		String extension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
		switch (extension) {
			case "png":
				return "image/png";
			case "jpg":
			case "jpeg":
				return "image/jpeg";
			case "gif":
				return "image/gif";
			default:
				return "application/octet-stream";
		}
	}
}
