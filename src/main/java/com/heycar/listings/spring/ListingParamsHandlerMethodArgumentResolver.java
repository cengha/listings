package com.heycar.listings.spring;

import com.heycar.listings.controller.ListingParams;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;


public class ListingParamsHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

  public static final String OFFSET = "offset";
  public static final String LIMIT = "limit";

  @Override
  public boolean supportsParameter(final MethodParameter parameter) {
    return ListingParams.class.isAssignableFrom(parameter.getParameterType());
  }

  @Override
  public Object resolveArgument(
    final MethodParameter parameter,
    final ModelAndViewContainer mavContainer,
    final NativeWebRequest webRequest,
    final WebDataBinderFactory binderFactory
  ) {
    final ListingParams.ListingParamsBuilder builder = ListingParams.builder();
    extract(webRequest, OFFSET).map(builder::offset);
    extract(webRequest, LIMIT).map(builder::limit);
    return builder.build();
  }

  public static Optional<Integer> extract(final WebRequest request, final String parameterName) {
    try {
      return Optional.ofNullable(request.getParameter(parameterName)).map(Integer::valueOf);
    } catch (Exception e) {
      throw new RuntimeException(String.format("invalid listing parameter %s", parameterName));
    }
  }

}
