package userstable.controller;

import org.springframework.ui.Model;
import userstable.model.UserEntity;
import userstable.model.UsersFilter;

public interface Controller {
    public String listUsers(Model model, UsersFilter filter, Integer page);

    public String addUser(UserEntity userEntity);

    public String removeUser(int id);

    public String editUser(Model model, UsersFilter filter, int id);

    public String userData(Model model, int id);
}
