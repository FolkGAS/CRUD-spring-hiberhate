package userstable.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import userstable.dao.UserDaoImpl;
import userstable.model.UserEntity;
import userstable.model.UsersFilter;
import userstable.service.UserService;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@SessionAttributes(value = "filter")
@Controller
public class ControllerImpl {
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
    private int length = 5;

    @Autowired(required = true)
    @Qualifier(value = "userService")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("filter")
    public UsersFilter getUsersFilter(){
        return new UsersFilter();
    }

    @RequestMapping(value = "users")
    public String listUsers(Model model,
                            @ModelAttribute(value = "filter") UsersFilter filter) {

        model.addAttribute("filter", filter);
        model.addAttribute("user", new UserEntity());
        model.addAttribute("listUsers", userService.listUsers(filter));
        model.addAttribute("pagesCount", Math.ceil(userService.usersCount(filter) * 1.0 / length));
        return "users";
    }

    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("user") UserEntity userEntity) {
        userEntity.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        if (userEntity.getId() == 0)
            userService.addUser(userEntity);
        else
            userService.updateUser(userEntity);
        return "redirect:/users";
    }

    @RequestMapping("/remove/{id}")
    public String removeUser(@PathVariable("id") int id) {
        userService.removeUser(id);
        return "redirect:/users";
    }

    @RequestMapping("edit/{id}")
    public String editUser(Model model,
                           @ModelAttribute(value = "filter") UsersFilter filter,
                           @PathVariable("id") int id) {
        model.addAttribute("filter", getUsersFilter());
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("listUsers", userService.listUsers(filter));
        return "users";
    }

    @RequestMapping("userData/{id}")
    public String userData(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.getUserById(id));
        return "userData";
    }

}
