package bg.forcar.api.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Responsible for the main request for Swagger IO tool
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Controller
public class HomeController {

    /**
     * It aims to return the home page of Swagger IO tool
     *
     * @return the main Swagger home page
     */
    @GetMapping(value
            = {
                "", "/", "/home", "/index"
            })
    public String root() {
        return "redirect:/swagger-ui.html";
    }
}
