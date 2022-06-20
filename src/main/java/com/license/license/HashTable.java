package com.license.license;
import java.util.Hashtable;
import java.util.Map;

public class HashTable {
    int size = 100;
    //User user = new User();

    public HashTable(int size) { //creeaza un hash tabel gol cu capacitatea de 100
        this.size = size;
    }

    Hashtable<Integer, String> hashtable = new Hashtable<Integer, String>();

    public void insert(Integer id, String email){
        hashtable.put(id, email);
    }

    public Boolean search(Integer id, String email){
        for(Map.Entry<Integer, String> entry : hashtable.entrySet()){
            String str = entry.getValue();
            if(str == email){
                return false;
            }
        }
        return true;
    }

}
