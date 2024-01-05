package cz.cvut.fit.tjv.foto.client.web_ui;

import cz.cvut.fit.tjv.foto.client.model.PhotographerDto;
import cz.cvut.fit.tjv.foto.client.service.PhotographerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/photographers")
public class PhotographerController {
    private PhotographerService photographerService;

    public PhotographerController(PhotographerService photographerService){
        this.photographerService = photographerService;
    }

    @GetMapping
    public String list(Model model){
        var all = photographerService.readAll();
        model.addAttribute("allPhotographers", all);
        return "photographers";
    }

    @GetMapping("/edit")
    public String showForm(@RequestParam Long id, Model model){
        photographerService.setCurrentPhotographer(id);
        var photographer = photographerService.readOne().get();
        model.addAttribute("photographer", photographer);
        return "editPhotographer";
    }

    @PostMapping("/edit")
    public String editSubmit(Model model, @ModelAttribute PhotographerDto formData) {
        if(formData.getPhoneNumber().matches(".*[^0-9].*")){
            model.addAttribute("photographer", formData);
            model.addAttribute("error", true);
            return "editPhotographer";
        }
        photographerService.setCurrentPhotographer(formData.getId());
        try {
            photographerService.update(formData);
        } catch (HttpClientErrorException.NotFound e) {
            model.addAttribute("error", true);
        }
        return "redirect:/photographers";
    }


    @GetMapping("/registrationPhoto")
    public String register(Model model) {
        PhotographerDto photographer = new PhotographerDto(1L, "","", Collections.singleton(""));
        model.addAttribute("photographerDto", photographer);
        return "registrationPhoto";
    }

    @PostMapping("/registrationPhoto")
    public  String postSubmit(Model model, @ModelAttribute PhotographerDto formData) {
        if(formData.getPhoneNumber().matches(".*[^0-9].*")){
            model.addAttribute("photographer", formData);
            model.addAttribute("error", true);
            return "registrationPhoto";
        }
        photographerService.create(formData);
        return  "redirect:/photographers";
    }

    @GetMapping("/delete")
    public String deletePhotographer(Model model, @RequestParam Long id){
        photographerService.setCurrentPhotographer(id);
        try {
            photographerService.delete();
        } catch (HttpClientErrorException.BadRequest e) {
            model.addAttribute("error", true);
        }
        return "redirect:/photographers";
    }

}
