package police.controllers;

import org.hibernate.validator.constraints.NotBlank;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import police.model.Accident;
import police.model.repo.AccidentRepository;

@RestController
public class AccidentController {
    private final AccidentRepository accidents;

    @Autowired
    public AccidentController(AccidentRepository accidents) {
        this.accidents = accidents;
    }

    @GetMapping(path = "accident/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAccident(Model model, @PathVariable("id") int id) {
        Accident acc = accidents.getById(id);
        JSONObject object = new JSONObject();
        object.put("accident", acc);
        return object.toJSONString();
    }

    @PostMapping(path = "accident/new")
    public RedirectView addAccident(@RequestParam @NonNull @NotBlank String name,
                                    @RequestParam @NonNull String address,
                                    @RequestParam String text,
                                    Model model) {
        accidents.addAccident(new Accident(name, text, address));
        return new RedirectView("../");
    }

    @PostMapping(path = "accident/delete")
    public RedirectView delAccident(@RequestParam @NonNull Integer id, Model model) {
        accidents.delete(id);
        return new RedirectView("../");
    }

    @PostMapping(path = "accident/edit")
    public RedirectView editAccident(@RequestParam @NonNull String id,
                                     @RequestParam @NonNull @NotBlank String name,
                                     @RequestParam @NonNull String address,
                                     @RequestParam String text,
                                     Model model) {
        Accident accident = new Accident(name, text, address);
        accident.setId(Long.parseLong(id));
        accidents.update(accident);
        return new RedirectView("../");
    }
}
