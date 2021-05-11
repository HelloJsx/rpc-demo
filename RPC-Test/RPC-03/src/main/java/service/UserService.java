package service;

import model.User;
import model.UserResult;

/**
 * 用户登录和注册
 */
public interface UserService {

    UserResult login(User user);

    UserResult register(User user);
}
