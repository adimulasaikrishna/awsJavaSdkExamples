package com.sdk.aws.contoller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.amazonaws.services.s3.model.Bucket;
import com.sdk.aws.services.S3Services;

@RestController
@RequestMapping(value = "/s3")
public class S3Controller {

	@Autowired
	private S3Services service;

	/*
	 * @PostMapping(value = "/upload") public ResponseEntity<String>
	 * uploadFile(@RequestPart(value = "file") final MultipartFile multipartFile) {
	 * //service.uploadFileToS3Bucket(multipartFile); final String response = "[" +
	 * multipartFile.getOriginalFilename() + "] uploaded successfully."; return new
	 * ResponseEntity<>(response, HttpStatus.OK); }
	 */

	@GetMapping(value="/createbucket")
	public String createBucket(@RequestParam String name) {
		try {
		service.createBucket(name);
		return name +" Bucket created successfully";
		}catch(Exception exe) {
			return exe.getMessage();
			
		}
		
		
	}

	@GetMapping(value = "/listbuckets")
	public List<Bucket> listBucket() {

		return service.listBucket();
	}

	@GetMapping(value = "/deletebucket")
	public String deleteBucket(@RequestParam String name) {
		service.deleteBucket(name);
		return "Bucket deleted successfully";
	}

	/*
	 * @GetMapping(value="/uploadAnObject") public String uploadObjectToBucket() {
	 * 
	 * }
	 */

	@GetMapping(value = "/deleteObject")
	public String deleteObjectFromBucket(@RequestParam String name, @RequestParam String keyName) {
		service.deleteObjectFromBucket(name, keyName);

		return "Object deleted from Bucket deleted successfully";
	}
	
	

}
