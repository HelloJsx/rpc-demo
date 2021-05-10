package rpc07;

import rpc.common.IUserService;
import rpc.common.User;

public class UserServiceImpl implements IUserService {
    @Override
    public User findUserById(Integer empno) {
        return new User(empno,"SMITH");
    }
}
