package com.license.license;

import java.util.List;

public class BubbleSort {
    public void bubbleSort(List<User> userList){
        int size = userList.size();
        String aux;

        for(Integer i = 0; i < size - 1; i++){
            for(Integer j = i + 1; j < size; j++){
                if(userList.get(i).getEmail().compareTo(userList.get(j).getEmail()) > 0){
                    aux = userList.get(i).getEmail();
                    userList.get(i).setEmail(userList.get(j).getEmail());
                    userList.get(j).setEmail(aux);
                }
            }
        }
    }
}
