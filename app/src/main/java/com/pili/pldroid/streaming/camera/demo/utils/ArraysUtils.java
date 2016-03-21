package com.pili.pldroid.streaming.camera.demo.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/2/3.
 */
public class ArraysUtils {

    public static ArrayList getArrays(List list, int start , int end){
        ArrayList dataEntities = new ArrayList<>();
        for(int i=start;i<end;i++){
            dataEntities.add(list.get(i));
        }
        return dataEntities;
    }
}
