package test;

import Util.FileUtil;
import dao.UserDao;
import dao.UserDaoImpl;
import model.User;
import model.UserResult;
import org.junit.Test;
import service.UserService;
import service.UserServiceImpl;

import java.util.HashMap;
import java.util.Map;

public class TestFile {

    @Test
    public void testFileUtile(){
        Map<String, User> map = new HashMap<>();
        map.put("wc",new User("wc","666666","吴超",20));

        FileUtil.saveMapToFile(map);
        Map<String,User> userMapByFile = FileUtil.getUserMapFile();
        System.out.println(userMapByFile);
    }

    @Test
    public void testUserDao(){
        UserDao dao = new UserDaoImpl();

        User userByName = dao.getUserByName("wc");
        System.out.println(userByName);
    }

    @Test
    public void testUserService(){
        UserService service = new UserServiceImpl();
        UserResult result = service.login(new User("zdj","1234",null,0));
        System.out.println(result);

        UserResult result1 = service.register(new User("chaochao","123","超超",20));
        System.out.println(result1);
    }
}
