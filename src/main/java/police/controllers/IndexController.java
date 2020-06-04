package police.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import police.model.repo.AccidentRepository;

@Controller
public class IndexController {
    @Autowired
    private AccidentRepository accidents;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("accidents", accidents.getAllAccidents());
        return "index";
    }
}
