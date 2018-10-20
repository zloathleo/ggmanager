package com.xz.managersystem.web.resolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GolbalExceptionResolver implements HandlerExceptionResolver {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object handler, Exception ex) {

        ex.printStackTrace();


        String requestURI = request.getRequestURI();
        logger.info(requestURI);
        logger.error("业务异常[ur=" +requestURI+ "]",ex);

        ModelAndView mv = new ModelAndView();
        mv.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
//        if(requestURI.endsWith(".do")){
            MappingJackson2JsonView view = new MappingJackson2JsonView();
            mv.setView(view);
            if (ex.getMessage().contains("ccessToken")) {
                mv.addObject("err", "-5");
            } else {
                mv.addObject("err", "-1");
            }
            mv.addObject("message", ex.getMessage());
            return mv;
//        }else{
//            mv.addObject("err", "-1");
//            mv.addObject("message", ex.getMessage());
//            mv.setViewName("redirect:/Views/exception/exception");
//            return mv;
//        }
    }
}
