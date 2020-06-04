package police.controllers;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import police.model.Accident;
import police.model.repo.AccidentRepository;

@RestController
public class AccidentController {
    @Autowired
    private AccidentRepository accidents;

    @GetMapping(value = "/accident/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String accident(Model model, @PathVariable("id") int id) {
        Accident acc = accidents.getById(id);
        JSONObject object = new JSONObject();
        object.put("accident", acc);
        return object.toJSONString();
    }
}
