package com.sdk.aws.contoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@Api(value = "Set of endpoints for Health Check and swagger urls info.")
public class HealthContoller {
	
	
	
	@GetMapping("/health")
	public String checkHealth() {
		return "OK";
    }
	
	@RequestMapping(method = RequestMethod.GET, value = "/swaggerInfo")
	@ApiOperation(value="get basic swagger info",notes="returns swagger ul and api-docs urls." )
	public String swaggerInfo() {
		return "Swagger UI ::: http://<host>/swagger-ui.html  AND API-DOC ::: http://<host>/v2/api-docs";
	}
	
	

}
