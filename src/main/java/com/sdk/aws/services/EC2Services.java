package com.sdk.aws.services;

import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.model.InstanceType;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.Tag;
import com.amazonaws.services.applicationdiscovery.model.CreateTagsRequest;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.StartInstancesRequest;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.StopInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Reservation;

@Service
public class EC2Services {

	private static final AWSCredentials AWS_CREDENTIALS;

	static {
		AWS_CREDENTIALS = new BasicAWSCredentials("<accessKey>", "<SecretKey>");

	}

	public String createEc2Instance() {

		AmazonEC2 ec2Client = setUpEc2Client();
		// Launch an Amazon EC2 Instance
		RunInstancesRequest run_request = new RunInstancesRequest().withImageId("ami-02b5cd5aa444bee23")
				.withInstanceType(InstanceType.T2Micro).withMaxCount(1).withMinCount(1);

		RunInstancesResult run_response = ec2Client.runInstances(run_request);

		String reservation_id = run_response.getReservation().getInstances().get(0).getInstanceId();

		System.out.println("EC2 Instance Id: " + reservation_id);
		return reservation_id;
	}

	public String startInstance(String instanceID) {
		AmazonEC2 ec2Client = setUpEc2Client();
		StartInstancesRequest request = new StartInstancesRequest().withInstanceIds(instanceID);

		ec2Client.startInstances(request);
		return "Instance start request initiated";

	}

	public String stopInstance(String instanceID) {
		AmazonEC2 ec2Client = setUpEc2Client();
		StopInstancesRequest request = new StopInstancesRequest().withInstanceIds(instanceID);

		ec2Client.stopInstances(request);
		return "Instance stop request initiated";

	}

	public String describeInstance() {
		AmazonEC2 ec2Client = setUpEc2Client();
		boolean done = false;
		String output="";

		DescribeInstancesRequest request = new DescribeInstancesRequest();
		while(!done) {
		    DescribeInstancesResult response = ec2Client.describeInstances(request);

		    for(Reservation reservation : response.getReservations()) {
		        for(Instance instance : reservation.getInstances()) {
		        	output =  "Found instance with id %s, " +
		                    "AMI %s, " +
		                    "type %s, " +
		                    "state %s " +
		                    "and monitoring state %s"+
		                    instance.getInstanceId()+
		                    instance.getImageId()+
		                    instance.getInstanceType()+
		                    instance.getState().getName()+
		                    instance.getMonitoring().getState();
		           
		        }
		    }

		    request.setNextToken(response.getNextToken());

		    if(response.getNextToken() == null) {
		        done = true;
		    }
		}
		return output;

	}
	
	private AmazonEC2 setUpEc2Client() {
		// Setting up the amazon ec2 client
		AmazonEC2 ec2Client = AmazonEC2ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(AWS_CREDENTIALS)).withRegion(Regions.US_EAST_1)
				.build();
		return ec2Client;
	}

}
