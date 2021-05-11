package dao;

import model.User;

public interface UserDao {
    /**
     * 保存user对象
     * @param user
     * @return
     */
    boolean save(User user);

    /**
     * 通过用户名获取用户对象
     * @param userName
     * @return
     */
    User getUserByName(String userName);
}
