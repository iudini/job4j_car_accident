package ru.job4j.accident.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.repository.AccidentMem;

import java.util.Collection;


@Controller
public class AccidentControl {
    private final AccidentMem accidents;

    public AccidentControl(AccidentMem accidents) {
        this.accidents = accidents;
    }

    @GetMapping("/create")
    public String create(Model model) {
        Collection<AccidentType> types = accidents.getTypes();
        model.addAttribute("types", types);
        return "accident/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident) {
        accidents.create(accident);
        return "redirect:/";
    }

    @GetMapping("/update")
    public String update(@RequestParam("id") int id, Model model) {
        accidents.findById(id).ifPresent(value -> model.addAttribute("accident", value));
        Collection<AccidentType> types = accidents.getTypes();
        model.addAttribute("types", types);
        return "accident/edit";
    }
}
