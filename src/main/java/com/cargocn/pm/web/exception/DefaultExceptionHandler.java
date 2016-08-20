package com.cargocn.pm.web.exception;

import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 * User: Zhang Kaitao
 * <p>
 * Date: 14-2-12
 * <p>
 * Version: 1.0
 */
@ControllerAdvice
public class DefaultExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 没有权限 异常
	 * <p/>
	 * 后续根据不同的需求定制即可
	 */
	@ExceptionHandler({ UnauthorizedException.class })
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ModelAndView processUnauthenticatedException(NativeWebRequest request, UnauthorizedException e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("exception", e);
		mv.setViewName("unauthorized");
		return mv;
	}
	
	/**
	 * 上传文件太大 异常 
	 */
	@ExceptionHandler({ MaxUploadSizeExceededException.class })
	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	public ModelAndView processMaxUploadSizeExceededException(NativeWebRequest request, MaxUploadSizeExceededException e) {
		logger.error("e:",e);
		ModelAndView mv = new ModelAndView();
		mv.addObject("exception", e);
		mv.setViewName("error_fileupload");
		return mv;
	}
	
}
