package dao;

import model.User;

public class UserDaoImpl implements UserDao{
    @Override
    public boolean save(User user) {
        return false;
    }

    @Override
    public User getUserByName(String userName) {
        return null;
    }
}
