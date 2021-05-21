package com.ece350.assembler.utility.tuple;

public enum AssemblerStatus {

    SUCCESS("success"),
    ERROR("error");

    private final String myStatus;

    private AssemblerStatus(String status) {
        this.myStatus = status;
    }
}
