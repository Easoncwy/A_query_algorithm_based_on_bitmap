package Method;

import Entity.Interval;
import Entity.Unit;
import Entity.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by eason on 2016/12/26.
 */
public class Delete {
    public Delete(String userId , Interval interval,
           Map<Integer, User> markUserMap,
           ArrayList<String> allTimeArray,
           HashSet<String> allStartTimeSet,
           ArrayList<String> allUsers,
           Map<String, ArrayList<Unit>> compressedMap){

        String start,end;
        int index = -1;
        User user = null;
        for(Integer order:markUserMap.keySet()){
            user = markUserMap.get(order);
            if(user.userID.equals(userId)){

                user.intervals.remove(interval);
                break;
            }
        }

        if (user.isEmpty()){




        }else {





        }












    }






}
