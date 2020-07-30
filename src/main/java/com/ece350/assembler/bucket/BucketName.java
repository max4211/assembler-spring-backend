package com.ece350.assembler.bucket;

public enum BucketName {

    PROFILE_IMAGE("ece350-assembler-spring");

    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}
