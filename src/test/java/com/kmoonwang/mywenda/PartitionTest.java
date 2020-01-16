package com.kmoonwang.mywenda;
import java.util.ArrayList;

public class PartitionTest {
    public ArrayList<Integer> GetLeastNumbers_Solution(int [] input, int k) {
        //使用partition的方法会改变原来的数据，同时不适合海量数据
        int s = 0;
        int e = input.length-1;
        ArrayList<Integer> res = new ArrayList<>();
        while(s <= e){
            int index = partition(input,s,e);
            if(index == k-1){
                for(int i = 0;i < k;i++){
                    res.add(input[i]);
                }
                break;
            }else if(index > k){
                e = index - 1;
            }else{
                s = index + 1;
            }
        }
        //System.out.println(partition(input,0,input.length-1));
        return res;
    }
    public int partition(int[] input,int s,int e){
        int target = input[e];
        int more = e;
        int less = s;
        while(s < e){
            if(input[s] < target){
                s++;
            }else{
                swap(input,s,--e);
            }
        }
        swap(input,more,s);
        return s;
    }
    public void swap(int[] input,int i,int j){
        int tmp = input[i];
        input[i] = input[j];
        input[j] = tmp;
    }
    public static void main(String[] args){
        PartitionTest pt = new PartitionTest();
        pt.GetLeastNumbers_Solution(new int[]{2,5,6,2,2,2,8},4);
    }
}
