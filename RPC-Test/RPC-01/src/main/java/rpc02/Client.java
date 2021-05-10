package rpc02;

public class Client {
    public static void main(String[] args) throws Exception {
        Stub stub = new Stub();
        System.out.println(stub.findUserById(7369));
    }
}
