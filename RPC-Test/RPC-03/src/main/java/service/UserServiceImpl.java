package service;

import dao.UserDao;
import dao.UserDaoImpl;
import model.User;
import model.UserResult;

public class UserServiceImpl implements UserService{
    @Override
    public UserResult login(User user) {
        //保存返回数据
        UserResult result = new UserResult();
        UserDao dao = new UserDaoImpl();
        //通过用户名从文件中收到的
        User userByName = dao.getUserByName(user.getUserName());
        if (userByName == null) {
            result.setResult(false);
            result.setDec("没有找到该用户");
        } else {
            if (userByName.getPassWord().equals(user.getPassWord())) {
                result.setResult(true);
                result.setDec("登录成功");
            } else {
                result.setResult(false);
                result.setDec("登录失败");
            }
        }


        return result;
    }

    @Override
    public UserResult register(User user) {
        UserResult result = new UserResult();
        UserDao dao = new UserDaoImpl();
        User userByName = dao.getUserByName(user.getUserName());
        if (userByName == null) {
            if (dao.save(user)){
                result.setResult(true);
                result.setDec("注册成功");
            } else {
                result.setResult(false);
                result.setDec("注册异常");
            }
        } else {
            result.setResult(false);
            result.setDec("名字被占用");
        }
        return result;
    }
}
