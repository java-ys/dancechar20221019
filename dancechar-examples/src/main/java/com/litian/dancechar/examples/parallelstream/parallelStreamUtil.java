package com.litian.dancechar.examples.parallelstream;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

public class parallelStreamUtil {
    private static List<ActItem> actItems = Lists.newArrayList();

    static {
        for (int i=0;i<1000;i++) {
            ActItem actItem = new ActItem();
            actItem.setItemId(i);
            actItem.setItemCode("code" + i);
            actItem.setItemName("物品" + i);
            actItems.add(actItem);
        }
    }

    public static ActItem getById(int i){
        return actItems.get(i);
    }

    public static void dealArrayList(int size){
        for(int k=0;k<size;k++){
            List<ActItem> result = Lists.newArrayList();
            Set<Integer> idSet = Sets.newHashSet(1, 2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20);
            idSet.parallelStream().forEach(i -> {
                ActItem actItem = getById(i);
                if(actItem != null){
                    result.add(actItem);
                }
            });
            System.out.println(result.size());
        }
    }

    public static void dealStream(int size){
        for(int k=0;k<size;k++){
            List<ActItem> result = Collections.synchronizedList(Lists.newArrayList());
            Set<Integer> idSet = Sets.newHashSet(1, 2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20);
            idSet.stream().forEach(i -> {
                ActItem actItem = getById(i);
                if(actItem != null){
                    result.add(actItem);
                }
            });
            System.out.println(result.size());
        }
    }

    public static void dealVector(int size){
        for(int k=0;k<size;k++){
            Vector<ActItem> result = new Vector();
            Set<Integer> idSet = Sets.newHashSet(1, 2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20);
            idSet.parallelStream().forEach(i -> {
                ActItem actItem = getById(i);
                if(actItem != null){
                    result.add(actItem);
                }
            });
            System.out.println(result.size());
        }
    }

    public static void dealSynchronizedList(int size){
        for(int k=0;k<size;k++){
            List<ActItem> result = Collections.synchronizedList(Lists.newArrayList());
            Set<Integer> idSet = Sets.newHashSet(1, 2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20);
            idSet.parallelStream().forEach(i -> {
                ActItem actItem = getById(i);
                if(actItem != null){
                    result.add(actItem);
                }
            });
            System.out.println(result.size());
        }
    }

    public static void dealCopyOnWriteArrayList(int size){
        for(int k=0;k<size;k++){
            List<ActItem> result = new CopyOnWriteArrayList();
            Set<Integer> idSet = Sets.newHashSet(1, 2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20);
            idSet.parallelStream().forEach(i -> {
                ActItem actItem = getById(i);
                if(actItem != null){
                    result.add(actItem);
                }
            });
            System.out.println(result.size());
        }
    }

    public static void main(String[] args) {
        // dealArrayList(100);
        //dealStream(100);
        //dealVector(100);
        //dealSynchronizedList(100);
        dealCopyOnWriteArrayList(100);
    }
}
