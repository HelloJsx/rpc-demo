package Util;

import model.User;

import java.io.*;
import java.util.Map;

public class FileUtil {

    public static void saveMapToFile(Map<String, User>map){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Const.FILE_PATH));
            oos.writeObject(map);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String,User> getUserMapFile(){
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Const.FILE_PATH));
            Object readObject = ois.readObject();
            Map<String,User> map = (Map<String, User>) readObject;
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
