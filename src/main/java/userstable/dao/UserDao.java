package userstable.dao;

import userstable.model.UserEntity;
import userstable.model.UsersFilter;

import java.util.List;

public interface UserDao {
    public void addUser(UserEntity userEntity);

    public void updateUser(UserEntity userEntity);

    public void removeUser(int id);

    public UserEntity getUserById(int id);

    public List<UserEntity> listUsers(UsersFilter filter, int page, int length);

    public int usersCount(UsersFilter filter);
}
