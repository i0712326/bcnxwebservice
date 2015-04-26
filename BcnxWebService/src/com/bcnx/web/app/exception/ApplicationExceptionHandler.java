package com.bcnx.web.app.exception;

import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.bcnx.web.app.service.entity.ErrMsg;

@Provider
@Component
public class ApplicationExceptionHandler implements	ExceptionMapper<Exception> {
	private static final Logger logger = Logger.getLogger(ApplicationExceptionHandler.class);
	@Produces("application/json")
	@Override
	public Response toResponse(Exception ex) {
		logger.debug("Exception occured while process request",ex);
		logger.debug("Exception message :"+ex.getMessage());
		ErrMsg errMsg = new ErrMsg();
		errMsg.setCode("510");
		errMsg.setMessage("Internal Message Error");
		return Response.serverError().entity(errMsg).build();
	}
}
