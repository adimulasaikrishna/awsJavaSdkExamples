package com.sdk.aws.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sdk.aws.services.EC2Services;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/ec2")
@Api(produces = "application/json", value = "Operations pertaining to EC2 using SDKS")
public class EC2Controller {
	
	@Autowired
	EC2Services ec2Services; 
	
	@RequestMapping(value = "/createInstance", method = RequestMethod.POST)
	@ApiOperation(value = "Create a EC2 Instance.", response = ResponseEntity.class)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created a EC2 Instance"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public ResponseEntity<String>  createNewInstance() {
		String responce = ec2Services.createEc2Instance();
        return new ResponseEntity<String>(responce, HttpStatus.CREATED);
    }
	
	@RequestMapping(value = "/startInstance", method = RequestMethod.GET)
	@ApiOperation(value = "Start a EC2 Instance.", response = ResponseEntity.class)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Started a EC2 Instance"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public ResponseEntity<String>  startInstance(@RequestParam String instanceId) {
		String responce = ec2Services.startInstance(instanceId);
        return new ResponseEntity<String>(responce, HttpStatus.OK);
    }
	
	
	@RequestMapping(value = "/stopInstance", method = RequestMethod.GET)
	@ApiOperation(value = "Stop a EC2 Instance.", response = ResponseEntity.class)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Stopped a EC2 Instance"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public ResponseEntity<String>  stopInstance(@RequestParam String instanceId) {
		String responce = ec2Services.stopInstance(instanceId);
        return new ResponseEntity<String>(responce, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/describeInstance", method = RequestMethod.GET)
	@ApiOperation(value = "describe a EC2 Instance.", response = ResponseEntity.class)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Info on EC2 Instance"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public ResponseEntity<String>  getInstanceInfo() {
		String responce = ec2Services.describeInstance();
        return new ResponseEntity<String>(responce, HttpStatus.OK);
    }

}
