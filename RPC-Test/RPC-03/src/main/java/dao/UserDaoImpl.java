package dao;

import Util.Const;
import Util.FileUtil;
import model.User;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class UserDaoImpl implements UserDao{
    @Override
    public boolean save(User user) {
        File file = new File(Const.FILE_PATH);
        Map<String, User> map = null;
        if(file.exists()){//当文件存在的时候，不是第一个保存的用户
            map = FileUtil.getUserMapFile();
            map.put(user.getUserName(), user);
        }else{//之前没有文件
            map = new HashMap<>();
            map.put(user.getUserName(), user);
        }
        //把所有的用户保存回去
        FileUtil.saveMapToFile(map);
        return true;
    }

    @Override
    public User getUserByName(String userName) {

        File file = new File(Const.FILE_PATH);
        if(file.exists()){
            Map<String, User> userMapByFile = FileUtil.getUserMapFile();
            User user = userMapByFile.get(userName);
            return user;
        }
        return null;
    }

}
