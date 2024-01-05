package cz.cvut.fit.tjv.foto.client.web_ui;

import cz.cvut.fit.tjv.foto.client.model.*;
import cz.cvut.fit.tjv.foto.client.service.OrderService;
import cz.cvut.fit.tjv.foto.client.service.PhotographerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collection;
import java.util.HashSet;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final PhotographerService photographerService;

    public OrderController(OrderService orderService, PhotographerService photographerService){
        this.orderService = orderService;
        this.photographerService = photographerService;
    }

    @GetMapping
    public String list(@ModelAttribute Range range, Model model){
        var all = orderService.readAll(range);
        model.addAttribute("allOrders", all);
        model.addAttribute("range",range);
        return "orders";
    }

//    @GetMapping("/filter")
//    public String showFilter(Model model, @ModelAttribute Range range){
//        model.addAttribute("range",range);
//        return "filterOrder";
//    }
//
//    @PostMapping("/filter")
//    public String submitFilter(@ModelAttribute Range range, Model model){
//        var all = orderService.readAll(range);
//        model.addAttribute("allProducts",all);
//        model.addAttribute("range",range);
//        return "orders";
//    }

//    @GetMapping("/filter/range")
//    public String showRange(Model model, @ModelAttribute OrderDto formData){
//        return "filterOrder";
//    }


    @GetMapping("/edit")
    public String showForm(@RequestParam Long id, Model model){
        orderService.setCurrentOrder(id);
        Order order = orderService.readOne().get();
        Collection<Long> idPhotographers = new HashSet<>();
        for(PhotographerDto photographerDto : order.getPhotographers()){
            idPhotographers.add(photographerDto.getId());
        }
        OrderDto orderDto =  new OrderDto(order.getId(), order.getDate(), order.getCost(), order.getAuthor().getId(), order.getMessage(), idPhotographers);
        model.addAttribute("orderDto", orderDto);
        return "editOrder";
    }

    @PostMapping("/edit")
    public String editSubmit(Model model, @ModelAttribute OrderDto formData) {
        orderService.setCurrentOrder(formData.getId());
        try {
            orderService.update(formData);
        } catch (HttpClientErrorException.NotFound e) {
            model.addAttribute("error", true);
        }
        return "redirect:/orders";
    }

    @GetMapping("/newOrder")
    public String addPhotographers(Model model, @ModelAttribute OrderDto formData) {
        OrderDto order = new OrderDto();
        Collection<Photographer> allPhotographers = photographerService.readAll();
        model.addAttribute("allPhotographers", allPhotographers);
        model.addAttribute("orderDto", order);
        return "newOrder";
    }

    @PostMapping("/newOrder")
    public  String postSubmit(Model model, @ModelAttribute OrderDto formData) {
        try {
            //check if each included photographer is available on that date
            for(Long photographer : formData.getPhotographers()){
                photographerService.setCurrentPhotographer(photographer);
                //checking if photographer is present with server
                if(photographerService.readOne().isEmpty()) {//NotFound
                    Collection<Photographer> allPhotographers = photographerService.readAll();
                    model.addAttribute("allPhotographers", allPhotographers);
                    model.addAttribute("error", true);
                    return "newOrder";
                }
                //ask server for info about photographer, set up
                Photographer photographerVar = new Photographer(photographerService.readOne().get().getId(),photographerService.readOne().get().getName(), photographerService.readOne().get().getPhoneNumber(), photographerService.readOne().get().getSessions());
                //if photographer is already on a different job that day, he is unavailable
                for(Order o : photographerVar.getSessions()) {
                    if (o.getDate().equals(formData.getDate())) {//Conflict
                        Collection<Photographer> allPhotographers = photographerService.readAll();
                        model.addAttribute("allPhotographers", allPhotographers);
                        model.addAttribute("errorC", true);
                        return "newOrder";
                    }
                }
            }
            //if everything is okay on the server, try to create the order
            orderService.create(formData);
        } catch (HttpClientErrorException.NotFound e) {//author
            Collection<Photographer> allPhotographers = photographerService.readAll();
            model.addAttribute("allPhotographers", allPhotographers);
            model.addAttribute("error", true);
            return "newOrder";
        }
        return  "redirect:/orders";
    }

    @GetMapping("/display")
    public String displayOrder(Model model, @RequestParam Long id){
        orderService.setCurrentOrder(id);
        var order = orderService.readOne().get();
        model.addAttribute("order", order);
        return "/displayOrder";
    }

    @GetMapping("/delete")
    public String deleteOrder(Model model, @RequestParam Long id){
        orderService.setCurrentOrder(id);
        orderService.delete();
        return "redirect:/orders";
    }

}
