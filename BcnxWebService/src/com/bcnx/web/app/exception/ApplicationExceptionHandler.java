package com.bcnx.web.app.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.spi.ApplicationException;

import com.bcnx.web.app.service.entity.ErrMsg;

@Provider
public class ApplicationExceptionHandler implements
		ExceptionMapper<ApplicationException> {
	@Override
	public Response toResponse(ApplicationException ex) {
		ErrMsg errMsg = new ErrMsg();
		errMsg.setCode("510");
		errMsg.setMessage("Internal Message Error");
		return Response.status(500).entity(errMsg).build();
	}
}
