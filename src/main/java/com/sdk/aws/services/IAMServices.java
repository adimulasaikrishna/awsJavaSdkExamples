package com.sdk.aws.services;

import org.springframework.stereotype.Service;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.AccessKeyMetadata;
import com.amazonaws.services.identitymanagement.model.CreateAccessKeyRequest;
import com.amazonaws.services.identitymanagement.model.CreateAccessKeyResult;
import com.amazonaws.services.identitymanagement.model.ListAccessKeysRequest;
import com.amazonaws.services.identitymanagement.model.ListAccessKeysResult;
import com.amazonaws.services.identitymanagement.model.Policy;
import com.amazonaws.services.identitymanagement.model.UpdateUserRequest;
import com.amazonaws.services.identitymanagement.model.UpdateUserResult;
import com.amazonaws.services.identitymanagement.model.CreateUserRequest;
import com.amazonaws.services.identitymanagement.model.CreateUserResult;
import com.amazonaws.services.identitymanagement.model.DeleteConflictException;
import com.amazonaws.services.identitymanagement.model.DeleteUserRequest;
import com.amazonaws.services.identitymanagement.model.CreatePolicyRequest;
import com.amazonaws.services.identitymanagement.model.CreatePolicyResult;
import com.amazonaws.services.identitymanagement.model.GetPolicyRequest;
import com.amazonaws.services.identitymanagement.model.GetPolicyResult;

@Service
public class IAMServices {

	public static final String POLICY_DOCUMENT = "{" + "  \"Version\": \"2012-10-17\"," + "  \"Statement\": [" + "    {"
			+ "        \"Effect\": \"Allow\"," + "        \"Action\": \"logs:CreateLogGroup\","
			+ "        \"Resource\": \"%s\"" + "    }," + "    {" + "        \"Effect\": \"Allow\","
			+ "        \"Action\": [" + "            \"dynamodb:DeleteItem\"," + "            \"dynamodb:GetItem\","
			+ "            \"dynamodb:PutItem\"," + "            \"dynamodb:Scan\","
			+ "            \"dynamodb:UpdateItem\"" + "       ]," + "       \"Resource\": \"RESOURCE_ARN\"" + "    }"
			+ "   ]" + "}";

	public String createAccessKey() {

		BasicAWSCredentials awsCreds = new BasicAWSCredentials("",
				"");

		final AmazonIdentityManagement iam = AmazonIdentityManagementClientBuilder.standard()
				.withCredentials(new com.amazonaws.auth.AWSStaticCredentialsProvider(awsCreds)).build();

		CreateAccessKeyRequest request = new CreateAccessKeyRequest().withUserName("sadimula");

		CreateAccessKeyResult response = iam.createAccessKey(request);

		System.out.println("Created access key: " + response.getAccessKey());

		return response.getAccessKey().getAccessKeyId();
	}

	public void listAccessKeys() {

		BasicAWSCredentials awsCreds = new BasicAWSCredentials("",
				"");

		final AmazonIdentityManagement iam = AmazonIdentityManagementClientBuilder.standard()
				.withCredentials(new com.amazonaws.auth.AWSStaticCredentialsProvider(awsCreds)).build();

		boolean done = false;
		ListAccessKeysRequest request = new ListAccessKeysRequest().withUserName("sadimula");

		while (!done) {

			ListAccessKeysResult response = iam.listAccessKeys(request);

			for (AccessKeyMetadata metadata : response.getAccessKeyMetadata()) {
				System.out.format("Retrieved access key %s", metadata.getAccessKeyId());
			}

			request.setMarker(response.getMarker());

			if (!response.getIsTruncated()) {
				done = true;
			}
		}
	}

	public String createIAMUser(String userName) {
		AmazonIdentityManagement iam = setup();
		CreateUserRequest createuser = new CreateUserRequest().withUserName(userName);
		CreateUserResult response = iam.createUser(createuser);
		return response.getUser() + "  created " + response.toString();
	}

	public String modifyIAMUser(String userName, String newName) {
		AmazonIdentityManagement iam = setup();
		UpdateUserRequest updateUser = new UpdateUserRequest().withUserName(userName).withNewUserName(newName);
		UpdateUserResult response = iam.updateUser(updateUser);
		return userName + "  updated " + response.toString();
	}

	public String DeleteIAMUser(String userName) {
		AmazonIdentityManagement iam = setup();
		DeleteUserRequest request = new DeleteUserRequest().withUserName(userName);

		try {
			iam.deleteUser(request);

		} catch (Exception e) {
			System.out.println("Unable to delete user. Verify user is not" + " associated with any resources");
		}
		return "user deleted";

	}

	private AmazonIdentityManagement setup() {
		BasicAWSCredentials awsCreds = new BasicAWSCredentials("",
				"");
		final AmazonIdentityManagement iam = AmazonIdentityManagementClientBuilder.standard()
				.withCredentials(new com.amazonaws.auth.AWSStaticCredentialsProvider(awsCreds)).build();
		return iam;
	}

	
	
	
	
	
	
	private Policy createPolicy(String policyName) {
		AmazonIdentityManagement iam = setup();

		CreatePolicyRequest request = new CreatePolicyRequest().withPolicyName(policyName)
				.withPolicyDocument(POLICY_DOCUMENT);

		CreatePolicyResult response = iam.createPolicy(request);
		
		return response.getPolicy();

	}
	
	//To retrieve an existing policy
	private Policy retrivePolicy(String policy_arn) {
		AmazonIdentityManagement iam = setup();

		GetPolicyRequest request = new GetPolicyRequest()
			    .withPolicyArn(policy_arn);

			GetPolicyResult response = iam.getPolicy(request);
		
		return response.getPolicy();

	}

}
