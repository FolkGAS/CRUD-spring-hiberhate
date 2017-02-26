package userstable.service;

import userstable.model.UserEntity;
import userstable.model.UsersFilter;

import java.util.List;

public interface UserService {
    public void addUser(UserEntity userEntity);

    public void updateUser(UserEntity userEntity);

    public void removeUser(int id);

    public UserEntity getUserById(int id);

    public List<UserEntity> listUsers(UsersFilter filter);

    public int usersCount(UsersFilter filter);

}
