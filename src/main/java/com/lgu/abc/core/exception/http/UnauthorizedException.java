package com.lgu.abc.core.exception.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends HttpException {

}
