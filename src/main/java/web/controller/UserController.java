package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.model.User;
import web.service.UserService;

import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin/users")
    public String printUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/admin/create")
    public String showCreateForm(Model model) {
        return "create";
    }

    @PostMapping("/admin/save")
    public String saveUsers(Model model, @RequestParam(required = false) Long idEdit,
                            @RequestParam String firstNameEdit,
                            @RequestParam String lastNameEdit,
                            @RequestParam String passwordEdit,
                            @RequestParam String emailEdit) {

        User user = new User(idEdit, passwordEdit, firstNameEdit, lastNameEdit, emailEdit);
        userService.save(user);
        model.addAttribute("users", userService.getAllUsers());
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/edit")
    public String showEditForm(Model model, @RequestParam Long id) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "edit";
    }

    @GetMapping("/user")
    public String showUserInfo(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }
}
