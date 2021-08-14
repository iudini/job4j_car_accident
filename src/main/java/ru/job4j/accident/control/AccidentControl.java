package ru.job4j.accident.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.repository.AccidentMem;

@Controller
public class AccidentControl {
    private final AccidentMem accidents;

    public AccidentControl(AccidentMem accidents) {
        this.accidents = accidents;
    }

    @GetMapping("/create")
    public String create() {
        return "accident/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident) {
        accidents.create(accident);
        return "redirect:/";
    }

    @GetMapping("/{accident-id}")
    public String edit(@PathVariable("accident-id") int id, Model model) {
        model.addAttribute("accident", accidents.find(id));
        return "accident/edit";
    }

    @PostMapping("/{accident-id}")
    public String update(@ModelAttribute Accident accident, @PathVariable("accident-id") String id) {
        accident.setId(Integer.parseInt(id));
        accidents.update(accident);
        return "redirect:/";
    }
}
