package com.szu.qq_hx;

import java.util.Arrays;

public class MsgPackage {
    private static final int COMMAND = 0;
    private static final int DIALOGUE = 1;
    private static final int NOINIT = -1;
    private static final int REGISTER = -1;
    private static final int LOGIN = -2;
    private static final int GETLIST = -3;
    private static final int EXIT = -4;
    private static final int SEND = 0;
    private static final int RECEIVE = 1;
    private static final int DETAILS_LEN = 200;
    private static final int WECHAT = 0; // 群聊

    private int msg_type;
    private int object;
    private String detail;
    private int flag;

    public MsgPackage(){
        msg_type = NOINIT;
    }

    public boolean isCmdPackage(){
        if(msg_type == COMMAND){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isDlgPackage(){
        if(msg_type == DIALOGUE){
            return true;
        }
        else {
            return false;
        }
    }

    public int getObject(){
        return object;
    }

    public int getFlag(){
        return flag;
    }

    public String getDetail(){
        return detail;
    }

    private byte[] bytesMerger(){
        byte[] tmp = new byte[DETAILS_LEN + 12];
        byte[] tmp_details = detail.getBytes();
        byte[] tmp_msg_type = int2Bytes(msg_type);
        byte[] tmp_object = int2Bytes(object);
        byte[] tmp_flag = int2Bytes(flag);

        for(int i = 0; i < DETAILS_LEN + 12; i++){
            tmp[i] = 0;
        }

        for(int i = 0; i < 4; i++){
            tmp[i] = tmp_msg_type[i];
            tmp[i + 4] =  tmp_object[i];
            tmp[i + DETAILS_LEN + 8] = tmp_flag[i];
        }

        int i = 0;
        for(; i < tmp_details.length; i++) {
            tmp[i + 8] = tmp_details[i];
        }

        return tmp;
    }

    private void bytesSplit(byte[] bag){
        // 拷贝数组中的一部分赋给String
        int i = 8;
        while (bag[i] != '\0'){
            i++;
        }
        detail = new String(Arrays.copyOfRange(bag, 8, i));
        //detail = new String(Arrays.copyOfRange(bag, 8, 208)); // 包括上标from，不包括下标to
        msg_type = bytes2Int(Arrays.copyOfRange(bag, 0,4));
        object = bytes2Int(Arrays.copyOfRange(bag, 4, 8));
        flag = bytes2Int(Arrays.copyOfRange(bag, 208, 212));
    }

    public byte[] configLoginPackage(String name, String password){
        msg_type = COMMAND;
        object = LOGIN;
        flag = SEND;
        detail = name+'#'+password;
        byte[] tmp = bytesMerger();
        return tmp;
    }

    public byte[] configRegisterPackage(String name, String password){
        msg_type = COMMAND;
        object = REGISTER;
        flag = SEND;
        detail = name+'#'+password;
        byte[] tmp = bytesMerger();
        return tmp;
    }

    public byte[] configWannaListPackage(){
        msg_type = COMMAND;
        object = GETLIST;
        flag = SEND;
        detail = "";
        byte[] tmp = bytesMerger();
        return tmp;
    }

    public byte[] configExitPackage(){
        msg_type = COMMAND;
        object = EXIT;
        flag = SEND;
        detail = "";
        byte[] tmp = bytesMerger();
        return tmp;
    }

    public byte[] configDlgPackage(int object, String dialogue){
        msg_type = DIALOGUE;
        this.object = object;
        detail = dialogue;
        flag = SEND;
        byte[] tmp = bytesMerger();
        return tmp;
    }

    public void makePackageFrom(byte[] bag){
        bytesSplit(bag);
    }

    public void showPackage(){
        System.out.println("-------------------------");
        System.out.println("type: " + msg_type);
        System.out.println("object: " + object);
        System.out.println("detail: " + detail);
        System.out.println("flag: " + flag);
        System.out.println("-------------------------");
    }

    private byte[] int2Bytes(int integer)
    {
        byte[] bytes=new byte[4];

        bytes[3]=(byte) (integer >> 24);
        bytes[2]=(byte) (integer >> 16);
        bytes[1]=(byte) (integer >> 8);
        bytes[0]=(byte) integer;

        return bytes;
    }

    private int bytes2Int(byte[] bytes){
        int tmp = 0;
        for(int i = 0; i < 4; i++) {
            int shift= i * 8;
            tmp +=(bytes[i] & 0xFF) << shift;
        }
        if(tmp > 127)
            tmp = tmp - 256;
        return tmp;
    }

    public static void main(String[] args){
        byte[] test = new byte[212];
        test[0] = DIALOGUE;
        test[4] = 5;
        test[8] = 'I';
        test[9] = ' ';
        test[10] = 'a';
        test[11] = 'm';
        test[12] = ' ';
        test[13] = 'P';
        test[14] = '\0';
        test[208] = SEND;

        MsgPackage tmp = new MsgPackage();
        tmp.makePackageFrom(test);
        tmp.showPackage();

        MsgPackage tmp1 = new MsgPackage();

        byte[] test2;
        //test2 = tmp1.configDlgPackage(4, "Hello I am Jack");
        //test2 = tmp1.configExitPackage();
        //test2 = tmp1.configLoginPackage("Mike", "123456");
        //test2 = tmp1.configRegisterPackage("Amy", "1234678");
        test2 = tmp1.configWannaListPackage();
        tmp1.showPackage();

        System.out.println(test2[0]);
        System.out.println(test2[4]);
        System.out.println((char) test2[8]);
        System.out.println((char)test2[9]);
        System.out.println((char)test2[10]);
        System.out.println((char)test2[11]);
        System.out.println((char)test2[12]);
        System.out.println((char)test2[13]);
        System.out.println((char)test2[14]);
        System.out.println(test2[208]);


    }
}
