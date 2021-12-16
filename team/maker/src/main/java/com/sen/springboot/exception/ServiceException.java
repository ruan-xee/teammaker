package com.sen.springboot.exception;

public class ServiceException extends RuntimeException{

    public ServiceException(ServiceExceptionEnum serviceExceptionEnum){
        super(serviceExceptionEnum.getMessage());
    }
}
