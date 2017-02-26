package userstable.service;

import userstable.model.UserEntity;
import userstable.model.UsersFilter;

import java.util.List;

public interface UserService {
    void addUser(UserEntity userEntity);

    void updateUser(UserEntity userEntity);

    void removeUser(int id);

    UserEntity getUserById(int id);

    List<UserEntity> listUsers(UsersFilter filter);

    int usersCount(UsersFilter filter);

}
