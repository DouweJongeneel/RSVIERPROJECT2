package com.adm.web;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Milan_Verheij on 15-08-16.
 */
@Controller
@RequestMapping({"/", "/homepage", "/index"})
public class HomeController {
    @RequestMapping(method=GET)
    public String home() {
        return "home";
    }
}
