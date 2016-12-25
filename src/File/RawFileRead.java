package File;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import Entity.Database;
import Entity.Interval;
import Entity.User;
import Method.Time;
/**
 * 
 * @author eason
 *
 */
public class RawFileRead {
	
	/**
	 * 
	 * @param file 原始文件
	 * @param db   内存数据库
	 * @param duration  指定区间
	 * @param markUserMap  对每个用户的一个编号，User-->Order
	 * @param allTimeArray  所有时间(无序)
	 * @param allStartTimeArray  所有开始时间(无序)
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	public void fileRead(String file, Database db, 
			long duration, Map<Integer, User> markUserMap,
			ArrayList<String> allTimeArray, HashSet<String> allStartTimeArray,
			ArrayList<String> allUsers) throws IOException, ParseException
	
	{
		Time TIME = new Time();
		long min = Integer.MAX_VALUE,max =-1;
		long startTIME = 0;
		long endTIME = 0;
		
		File f = new File(file);
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String line = "";
		String preUser = "";
		int count = 0;
		int markUserFigure = 0;
		Map<String, Boolean> markStartEndMap = new HashMap<>();
		while ( (line = br.readLine()) != null){
			String[] context = line.split("-->");
			String user = context[0];
			String[] time = context[1].split(",");
			String start = time[0].substring(1);
			String end = time[1].substring(0, time[1].length()-1);
			
			//设置开始时间 和 严格结束时间
			markStartEndMap.put(start, true);
			
			if (!markStartEndMap.containsKey(end)) {
				markStartEndMap.put(end, false);
			}
			
			startTIME = TIME.uniformTime(start);	
			endTIME = TIME.uniformTime(end);
			
			if( (endTIME - startTIME) >= duration )
			{
				if(!user.equals(preUser))
				{
					preUser = user;
					User u = new User(user,new Interval(start,end));
					db.insert(u);
					
					//markUserMap.put(u, markUserFigure);
					allUsers.add(user);
					markUserMap.put(markUserFigure, u);
					++markUserFigure;
				}
				else{
					db.insertI(new Interval(start,end));
				}
				if( startTIME < min ) min = startTIME;
				if( endTIME > max ) max = endTIME;
			}		
			++count;
		}
		/*
		 * 把 markStartEndMap 里 value 值为 true 的 key (即所有开始时间)加到 allStartTimeArray;
		 */
		for (String time : markStartEndMap.keySet()) {
			allTimeArray.add(time);
			if (markStartEndMap.get(time)) {
				allStartTimeArray.add(time);
			}
		}
		
		
		/*
		for (String time : markStartEndMap.keySet()) {
			System.out.println(time);
		}
		System.out.println();
		System.out.println("#############################");
		System.out.println();
		for (int i = 0; i < allTimeArray.size(); i++) {
			System.out.println(allTimeArray.get(i));
		}
		*/
		System.out.println("共读入"+  count + "记录");
		System.out.println("有效用户: " + db.data.size());
	}
}
