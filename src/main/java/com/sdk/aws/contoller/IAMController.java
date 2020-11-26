package com.sdk.aws.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sdk.aws.services.IAMServices;

@RestController
public class IAMController {

	@Autowired
	IAMServices iamServices;

	@GetMapping("/accesskey")
	public String accessKey() {
		return iamServices.createAccessKey();
	}
	
	@GetMapping("/createuser")
	public String createIAMUSer() {
		return iamServices.createIAMUser("");
	}
}
