package com.ece350.assembler.utility.tuple;

import org.springframework.core.io.ByteArrayResource;

public class AssemblerResponse {

    private final AssemblerStatus myStatus;
    private final ByteArrayResource myByteArrayResource;

    public AssemblerResponse(AssemblerStatus status, ByteArrayResource byteArrayResource) {
        this.myStatus = status;
        this.myByteArrayResource = byteArrayResource;
    }
}
