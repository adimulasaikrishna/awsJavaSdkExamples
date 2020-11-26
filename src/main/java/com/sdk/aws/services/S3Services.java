package com.sdk.aws.services;

import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;

import java.util.List;
import java.io.File;
import com.amazonaws.AmazonServiceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.*;
import java.util.Iterator;

@Service
public class S3Services {

	@Autowired
	private AmazonS3 amazonS3;

	@Value("${aws.s3.bucket}")
	private String bucketName;

	public Bucket createBucket(String bucketName) {
		Bucket bucket = null;
		if (amazonS3.doesBucketExistV2(bucketName)) {
			System.out.format("Bucket %s already exists.\n", bucketName);
			bucket = getBucket(bucketName);
		} else {
			try {
				bucket = amazonS3.createBucket(bucketName);
			} catch (AmazonS3Exception e) {
				System.err.println(e.getErrorMessage());
			}
		}
		return bucket;

	}

	public Bucket getBucket(String bucket_name) {
		Bucket named_bucket = null;
		List<Bucket> buckets = amazonS3.listBuckets();
		for (Bucket b : buckets) {
			if (b.getName().equals(bucket_name)) {
				named_bucket = b;
			}
		}
		return named_bucket;
	}
	
	public List<Bucket> listBucket() {
		Bucket named_bucket = null;
		List<Bucket> buckets = amazonS3.listBuckets();
		
		return buckets;
	}

	public void deleteBucket(String bucketName) {
		ObjectListing objectList = amazonS3.listObjects(bucketName);
		while (true) {
			for (Iterator<?> iterator = objectList.getObjectSummaries().iterator(); iterator.hasNext();) {
				S3ObjectSummary summary = (S3ObjectSummary) iterator.next();
				amazonS3.deleteObject(bucketName, summary.getKey());
			}

			if (objectList.isTruncated()) {
				objectList = amazonS3.listNextBatchOfObjects(objectList);
			} else {
				break;
			}
		}
		amazonS3.deleteBucket(bucketName);
	}


	//Different operation on Bucket
	
	public void uploadObjectToBucket(String bucketName,File file,String keyName) {
		try {
			amazonS3.putObject(bucketName, keyName, file);
		} catch (AmazonServiceException e) {
		    System.err.println(e.getErrorMessage());
		}
		
	}
	
	public void deleteObjectFromBucket(String bucketName, String keyName) {
		try {
			amazonS3.deleteObject(bucketName, keyName);
		} catch (AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
		}

	}
	
	/*public void deleteMultipleObjectFromBucket(String bucketName, String keyName) {
		try {
			DeleteObjectsRequest dor = new DeleteObjectsRequest(bucketName)
		            .withKeys(Arrays.asList("key_names"));
			amazonS3.deleteObjects(dor);
		} catch (AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
		}

	}*/
	
	
}
