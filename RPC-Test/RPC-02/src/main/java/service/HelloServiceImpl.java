package service;

public class HelloServiceImpl implements HelloService{
    @Override
    public String sayHi(String name) {
        return "测试 ," + name;
    }
}
