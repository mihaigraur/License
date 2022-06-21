package com.license.license;

import java.util.List;

public class BinarySearch {
    public int binarySearch(List<User> userList, String key){
        Integer left = 0;
        Integer right = userList.size() - 1;
        while(left <= right){
            Integer middle = left + (right - left) / 2;
            Integer result = key.compareTo(userList.get(middle).getEmail());

            if(result == 0){
                return middle;
            }
            if(result > 0){
                left = middle + 1;
            }
            else{
                right = middle - 1;
            }
        }
        return -1;
    }
}
