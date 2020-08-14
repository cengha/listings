package com.heycar.listings.spring.mvc;

import com.heycar.listings.spring.ListingParamsHandlerMethodArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

  @Override
  public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.addAll(
      List.of(new ListingParamsHandlerMethodArgumentResolver())
    );
  }

}
