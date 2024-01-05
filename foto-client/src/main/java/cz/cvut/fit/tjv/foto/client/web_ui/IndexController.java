package cz.cvut.fit.tjv.foto.client.web_ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String showIndex() {
        return "index";
    }
}
