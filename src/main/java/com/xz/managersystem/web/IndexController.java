package com.xz.managersystem.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/", method = RequestMethod.GET)
    private String defaultIndex(Model model) {
        return "redirect:/index";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    private String index(Model model) {
        logger.info("index");
        return "index";
    }

    @RequestMapping(value = "/Views/exception/exception", method = RequestMethod.GET)
    private String exception(Model model) {
        logger.info("exception");
        return "Views/exception/exception";
    }

//    @RequestMapping(value = "/Views/{module}/{page}", method = RequestMethod.GET)
//    private String defaultDispach(@PathVariable("module") String module, @PathVariable("page") String page) {
//        logger.info("module:" + module);
//        logger.info("page:" + page);
//        return "/Views/" + module + "/" + page;
//    }
}
