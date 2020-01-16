package com.kmoonwang.mywenda.service;

import org.apache.commons.lang3.CharUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class SensitiveFilterService {
    public class TrieNode{
        boolean end = false;
        char val;
        HashMap<Character,TrieNode> subnodes = new HashMap<>();
        public TrieNode(){
        }

        public void putnodes(char c,TrieNode node){
            subnodes.put(c,node);
        }

        public TrieNode returnnode(char c){
            return subnodes.get(c);
        }

        public void setend(boolean e){
            end = e;
        }

        public boolean isend(){
            return end;
        }

    }

    TrieNode root = new TrieNode();

    public void addword(String s){
        TrieNode rootcopy = root;
        char[] arr = s.toCharArray();
        for(int i = 0;i < arr.length;i++){
            if(i == arr.length - 1){
                rootcopy.setend(true);
            }
            if(rootcopy.returnnode(arr[i]) == null){
                TrieNode node = new TrieNode();
                rootcopy.putnodes(arr[i],node);
                rootcopy = node;
            }else{
                rootcopy = rootcopy.returnnode(arr[i]);
            }


        }
    }
    public boolean isSymbol(char c){
        int ic = (int)c;
        //0x2E80-0x9FFF东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(c)&&(ic < 0x2E80 || ic > 0x9FFF);
    }
    public String filter(String words){
        char[] arr = words.toCharArray();
        TrieNode rootcopy = root;
        int begin = 0;
        int position = 0;
        StringBuilder sb = new StringBuilder("");
        while(position < arr.length){
            char c = arr[position];

            if(isSymbol(c)){
                if(rootcopy == root){
                    sb.append(c);
                    begin++;
                }
                position++;
                continue;
            }
            if(rootcopy.returnnode(c) == null){
                sb.append(arr[begin]);
                position = begin+1;//不管当初走得有多远，最后又从头开始加
                begin = position;
                rootcopy = root;
            }else if(rootcopy.isend()){
                sb.append("***");
                begin = position+1;
                position = begin;
                rootcopy = root;
            } else{
                position++;
                rootcopy = rootcopy.returnnode(c);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args){
        SensitiveFilterService ss = new SensitiveFilterService();
        ss.addword("色情");
        ss.addword("嫖娼");
        System.out.println(ss.filter("你好x色 情piao嫖娼哦"));
    }
}
