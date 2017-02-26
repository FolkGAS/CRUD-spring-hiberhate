package userstable.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import userstable.dao.UserDao;
import userstable.model.UserEntity;
import userstable.model.UsersFilter;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public void addUser(UserEntity userEntity) {
        userDao.addUser(userEntity);
    }

    @Override
    @Transactional
    public void updateUser(UserEntity userEntity) {
        userDao.updateUser(userEntity);
    }

    @Override
    @Transactional
    public void removeUser(int id) {
        userDao.removeUser(id);
    }

    @Override
    @Transactional
    public UserEntity getUserById(int id) {
        return userDao.getUserById(id);
    }

    @Override
    @Transactional
    public List<UserEntity> listUsers(UsersFilter filter) {
        return userDao.listUsers(filter);
    }

    @Override
    @Transactional
    public int usersCount(UsersFilter filter) {
        return userDao.usersCount(filter);
    }
}
