package com.szu.qq_hx;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.szu.qq_hx.MsgPackage;

public class Client implements Serializable {
    private static final int BAG_SIZE = 212;
    private static final String SERVER_IP = "172.21.128.127";
    private static final int SERVER_PORT = 1012;
    private static final boolean ONLINE = true;
    private static final boolean OFFLINE = false;
    private static final int COMMAND = 0;
    private static final int DIALOGUE = 1;
    private static final int NOINIT = -1;
    private static final int REGISTER = -1;
    private static final int LOGIN = -2;
    private static final int GETLIST = -3;
    private static final int EXIT = -4;
    private static final int SEND = 0;
    private static final int RECEIVE = 1;
    private static final int WECHAT = 0; // 群聊

    private boolean state;
    private Socket socket_id;
    private DataOutputStream s_out;
    private DataInputStream s_in;
    private RecvThread thread;
    private UserList cast = new UserList();

    public Client(){
        state = OFFLINE;
    }

    public boolean connect(){
        try {
            // 创建一个Socket对象，指定服务器端的IP地址和端口号
            socket_id = new Socket(SERVER_IP, SERVER_PORT);
            s_out = new DataOutputStream(socket_id.getOutputStream());
            s_in = new DataInputStream(socket_id.getInputStream());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return socket_id.isConnected();
    }

    public void disconnetc() throws IOException {
        s_in.close();
        s_out.close();
        socket_id.close();
    }

    public boolean isClientConnect(){
        return socket_id.isConnected();
    }

    public boolean login(String name, String password){
        boolean res = false;
        MsgPackage login_package = new MsgPackage();
        byte[] login_bag = login_package.configLoginPackage(name, password);
        try{
            s_out.write(login_bag);
            byte[] respond = new byte[BAG_SIZE];
            int len = s_in.read(respond, 0, respond.length);
            if(len == -1){
                System.out.println("recv Error");
            }else {
                if(len <= 0){
                    System.out.println("Network Crime");
                }else {
                    MsgPackage tmp = new MsgPackage();
                    tmp.makePackageFrom(respond);
                    tmp.showPackage();
                    if(tmp.isCmdPackage()){
                        if(tmp.getObject() == LOGIN){
                            res = true;
                            state = ONLINE;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public boolean register(String name, String password){
        boolean res = false;
        MsgPackage register_package = new MsgPackage();
        byte[] register_bag = register_package.configRegisterPackage(name, password);
        try{
            s_out.write(register_bag);
            byte[] respond = new byte[BAG_SIZE];
            int len = s_in.read(respond, 0, respond.length);
            if(len == -1){
                System.out.println("recv Error");
            }else {
                if(len <= 0){
                    System.out.println("Network Crime");
                }else {
                    MsgPackage tmp = new MsgPackage();
                    tmp.makePackageFrom(respond);
                    tmp.showPackage();
                    if(tmp.isCmdPackage()){
                        if(tmp.getObject() == REGISTER){
                            res = true;
                            state = ONLINE;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public void getUserList(){
        MsgPackage dlg_package = new MsgPackage();
        byte[] dlg_bag = dlg_package.configWannaListPackage();
        try{
            s_out.write(dlg_bag);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean exit() throws InterruptedException {
        boolean res = false;
        MsgPackage exit_package = new MsgPackage();
        byte[] exit_bag = exit_package.configExitPackage();
        try{
            s_out.write(exit_bag);
        } catch (IOException e) {
            e.printStackTrace();
        }
        thread.join();
        return res;
    }

    public void sendDlg(int object, String dlg){
        MsgPackage dlg_package = new MsgPackage();
        byte[] dlg_bag = dlg_package.configDlgPackage(object, dlg);
        try{
            s_out.write(dlg_bag);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void unPack(MsgPackage input_package){
        if(input_package.isCmdPackage()){
            switch (input_package.getObject()){
                case GETLIST:
                    cast.updateUserList(input_package.getDetail());
                    break;
                case EXIT:
                    cancleRecvPackage();
                    break;
                default:
                    break;
            }

        }else if(input_package.isDlgPackage()){
            if(input_package.getObject() == WECHAT){
                System.out.println("Wechat: " + input_package.getDetail());
            }else if(input_package.getObject() > 0){
                System.out.print(input_package.getObject());
                System.out.println(": " + input_package.getDetail());
            }
        }
    }

    private class RecvThread extends Thread{
        public volatile boolean exit = false;
        @Override
        public void run(){
            try {
                while (!exit){
                    byte[] respond = new byte[BAG_SIZE];
                    int len = s_in.read(respond, 0, respond.length);
                    if(len == -1){
                        System.out.println("recv Error");
                        break;
                    }else {
                        if(len <= 0){
                            System.out.println("Network Crime");
                            break;
                        }else {
                            MsgPackage tmp = new MsgPackage();
                            tmp.makePackageFrom(respond);
                            tmp.showPackage();
                            unPack(tmp);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    socket_id.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public void recvPackage(){
        thread = new RecvThread();
        thread.exit = false;
        thread.start();
    }

    public  void cancleRecvPackage(){
        thread.exit = true;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
//      UserList tmp = new UserList();
//        tmp.updateUserList("laowang#1234#xiaoliu#12343#yadan#54321");
//        tmp.showUserList();
//        Client cc = new Client();
//        cc.connect();
//        cc.login("lao", "123456");
        Client clt = new Client();
        if(clt.connect()){
            boolean res = false;
            Scanner sc = new Scanner(System.in);
            System.out.println("请先登录（1）/注册（2）：");
            int logOrreg = sc.nextInt();
            System.out.println("请输入用户名：");
            String name = sc.next();
            System.out.println("请输入密码：");
            String pwd = sc.next();
            if(logOrreg == 1){
                res = clt.login(name, pwd);
            }else if(logOrreg == 2){
                res = clt.register(name, pwd);
            }

            if (res){
                clt.recvPackage();
                while (true){
                    System.out.println("获取列表（3）/发送消息（4）/群发（5）/退出（6）：");
                    int t = sc.nextInt();
                    if(t == 6){
                        clt.exit();
                        break;
                    }else if(t == 5){
                        System.out.println("内容：");
                        String str = sc.next();
                        clt.sendDlg(0, str);
                    }else if(t == 4){
                        System.out.println("发给谁：（序号）");
                        int num = sc.nextInt();
                        System.out.println("内容：");
                        String strr = sc.next();
                        clt.sendDlg(num, strr);
                    }else if(t == 3){
                        clt.getUserList();
                    }
                }

            }
            clt.disconnetc();
        }
    }
}


class UserList{
    private static final int USER_MAX_NUM = 10;
    private User[] user_list;
    private int list_num;

    UserList(){
        user_list = new User[USER_MAX_NUM];
        for(int i = 0; i < user_list.length; i++){
            user_list[i] = new User();
        }
        list_num = 0;
    }

    public int getListNum(){
        return list_num;
    }

    public void updateUserList(String str){
        int num = 0;

        for(int i = 0; i < USER_MAX_NUM; i++){
            user_list[i].initUser();
        }

        String[] user_set = str.split("#");
        for(int i = 0; i < user_set.length / 2; i++){
            user_list[i].setUserName(user_set[i*2]);
            user_list[i].setUserID(Integer.parseInt(user_set[i*2+1]));
        }
        list_num = user_set.length / 2;
    }

    public void showUserList(){
        System.out.println("------------- User List --------------");
        for(int i = 0; i < list_num; i++){
            user_list[i].showUser();
        }
        System.out.println("--------------------------------------");
    }
}

class User{
    private String name;
    private int id;

    User(){
        name = "";
        id = -1;
    }

    User(String user_name, int user_id){
        name = user_name;
        id = user_id;
    }

    public void initUser(){
        name = "";
        id = -1;
    }

    public String getUserName()
    {
        return name;
    }

    public int getUserID(){
        return id;
    }

    public void setUserName(String user_name){
        name = user_name;
    }

    public void setUserID(int user_id){
        id = user_id;
    }

    public void showUser(){
        System.out.print("name: " + name + ", id: ");
        System.out.println(id);
    }
}