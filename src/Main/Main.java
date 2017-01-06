package Main;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import Delete.Delete;
import Entity.Database;
import Entity.Interval;
import Entity.Unit;
import Entity.User;
import File.*;
import Insert.Insert;
import Method.NewAlgorithm;
import Method.SortAndFind;

public class Main {
	
    public static void main(String[] args) throws IOException, ParseException, Exception {
    	
    	Database db = new Database();
    	/** 所有用户的集合*/
    	ArrayList<String> allUsers = new ArrayList<>();
    	Map<Integer, User> markUserMap = new HashMap<>();
    	ArrayList<String> allTimeArray = new ArrayList<>();
    	HashSet<String> allStartTimeSet = new HashSet<>();
		//持续时间为一分钟
        int duration = 60 * 1000 ;
    	
       // String testFile = "/Users/hou/Documents/data/50000/test";
    	//String testBitMapFile = "/Users/hou/Documents/data/50000/testBitMapFile";
    	
//    	String file = "/Users/supreme/Desktop/data/50000/60second50000";
//    	String bitmapFile = "/Users/supreme/Desktop/data/50000/BitMapFile";
//    	String compressedFile = "/Users/supreme/Desktop/data/50000/CompressedBitMapFile";
//        String output = "/Users/supreme/Desktop/data/50000/queryResult";



        String test =            "/Users/supreme/Desktop/data/Insert50000/60second50000";
    	String testBitMapFile = "/Users/supreme/Desktop/data/Insert50000/BitMapFile";
    	String testCompFile = "/Users/supreme/Desktop/data/Insert50000/CompressedBitMapFile";
		String testOutput = "/Users/supreme/Desktop/data/Insert50000/queryResult";
    	
    	RawFileRead fr = new RawFileRead();
    	fr.fileRead(test, db, duration, markUserMap, allTimeArray, allStartTimeSet,allUsers);

    	
    	/**
    	 * 获得原始的bitmap
    	 */

//		SortAndFind sortAndFind = new SortAndFind();
//		ArrayList<String> sortedTimeArray = SortAndFind.sortAllTime(allTimeArray);


//    	CodeToBitMapFileWrite ctbm = new CodeToBitMapFileWrite();
//    	ctbm.codeToBitMap(testBitMapFile, db, markUserMap, sortedTimeArray);


    	/**
    	 * 获得压缩后的bitmap
    	 */
//        CompressedFileWrite cmpfw = new CompressedFileWrite();
//    	cmpfw.compressedFileWrite(testBitMapFile, testCompFile);



    	CompressedFileRead compbr = new CompressedFileRead();
    	Map<String, ArrayList<Unit>> compressedMap = compbr.compressedFileRead(testCompFile);


        /**
         * 插入测试用例1
         *
         */

        String userID01 = "u000000";
        String start01 = "2013-07-01 06:00:00";
        String end01   = "2013-07-01 07:00:00";

        /**
         * 插入测试用例2
         *
         */
        String userID02 = "u000000";
        String start02 = "2013-07-01 10:00:00";
        String end02   = "2013-07-01 11:00:00";

        /**
         * 插入测试用例3
         */
        String userID03 = "u000000";
        String start03 = "2013-07-01 16:00:00";
        String end03   = "2013-07-01 17:00:00";

        /**
         * 插入测试用例4
         *
         */
        String userID04 = "u000002";
        String start04 = "2013-07-01 13:00:00";
        String end04   = "2013-07-01 15:00:00";

        /**
         * 插入测试用例5
         */
        String userID05 = "u000003";
        String start05 = "2013-07-01 13:00:00";
        String end05   = "2013-07-01 15:00:00";




        /**
         * 删除测试用例1
         */
        String userID06 = "u000000";
        String start06 = "2013-07-01 08:00:00";
        String end06   = "2013-07-01 09:00:00";


        /**
         * 删除测试用例2
         */
        String userID07 = "u000002";
        String start07  = "2013-07-01 08:00:00";
        String end07    = "2013-07-01 10:00:00";

        /**
         * 插入测试用例a.新加用户之前未存在
         *
         */
        String userID08 = "u044444";
        String start08 = "2013-07-26 22:52:33";
        String end08 = "2013-07-26 22:53:40";

        /**
         * 插入测试用例b,新加用户之前已经存在
         */
        String userID09 = "u000002";
        String start09 = "2013-07-26 22:52:33";
        String end09 = "2013-07-26 22:53:40";


        /*
        Calendar c1 = Calendar.getInstance();

        Interval insertI = new Interval(start09, end09);
        Insert insert = new Insert(userID09, insertI, markUserMap, allTimeArray, allStartTimeSet, allUsers, compressedMap);

        Calendar c2 = Calendar.getInstance();

        long insertCost = c2.getTimeInMillis() - c1.getTimeInMillis();
        System.out.println("插入操作花费时间: " + Integer.toString((int) insertCost) + " ms\n");
        */

        /**
         * 删除用例测试a
         */


        String userID10 = "u000000";
        String start10  = "2013-07-26 22:48:42";
        String end10    = "2013-07-26 23:42:52";

        Calendar c1 = Calendar.getInstance();

        Interval deleteI = new Interval(start10, end10);
        Delete delete = new Delete(userID10, deleteI, markUserMap, allTimeArray, allStartTimeSet, allUsers, compressedMap);

        Calendar c2 = Calendar.getInstance();

        long insertCost = c2.getTimeInMillis() - c1.getTimeInMillis();
        System.out.println("删除操作花费时间: " + Integer.toString((int) insertCost) + " ms\n");










//        Interval deleteI = new Interval(start06, end06);
//        Delete delete = new Delete(userID06, deleteI, markUserMap, allTimeArray, allStartTimeSet, allUsers, compressedMap);



		//将所有用户的index置0，供之后筛选假用户用
    	for(Integer order:markUserMap.keySet()){
    		User u = markUserMap.get(order);
    		u.index = 0;
    	}
  
    	NewAlgorithm al = new NewAlgorithm();
    	
    	
    	Calendar ca = Calendar.getInstance();
    	
    	//System.out.println("查询算法开始计时");
        Map<Interval, ArrayList<User>> maxHashMap = al.getMaxHashMap(compressedMap, duration, markUserMap, allTimeArray, allStartTimeSet);

    	
    	al.printTheFinalSearchingResult(maxHashMap,testOutput);
    	
    	//System.out.println("查询算法结束计时");
    	Calendar cb = Calendar.getInstance();
    	
    	long queryCost = cb.getTimeInMillis() - ca.getTimeInMillis();
		System.out.println("查询算法花费时间: " + Integer.toString((int) queryCost) + " ms\n");


    	
	}  

}
