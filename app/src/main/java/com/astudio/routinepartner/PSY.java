package com.astudio.routinepartner;

import java.util.ArrayList;
import java.util.Arrays;

public class PSY {
    //PetStateManager
    //ActInfo에서 받아온 시간data를 수치로 계산
    //줄어들기
    //Pet 떠나갈 상태
    private float TimeData = 0;
    private float CategoryData = 0;

    //ArrayList<String> CategoryList = new ArrayList<>(Arrays.asList("식사", "취침", "공부", "운동"));
    ArrayList<String> CategoryList=SavedSettings.CategoryList;
    ArrayList<String> CategoryStat =SavedSettings.StatList;
    ArrayList<Integer> GoalList=SavedSettings.Goal;
    ArrayList<Integer> GoalType =SavedSettings.GoalType;
    /*ArrayList<Integer> GoalList = new ArrayList<>(Arrays.asList(2, 7, 4, 1));
    ArrayList<Integer> GoalType = new ArrayList<>(Arrays.asList(2, 1, 1, 1));*/
    ArrayList<String> StatList = new ArrayList<>(Arrays.asList("지능", "재미", "체력", "포만감", "잔고", "자아실현"));
    //ArrayList<String> CategoryStat = new ArrayList<>(Arrays.asList("포만감", "체력", "지능", "체력"));
    //public static ArrayList<Integer> AffectingStat = new ArrayList<>(Arrays.asList(4, 2, 4, 1, 2));
    ArrayList<Integer> CategoryStatInt=new ArrayList<>(Arrays.asList(3,2,0,2));
    ArrayList<Integer> Order=new ArrayList<>(Arrays.asList(1,2,3,4));

    public float calTimeValue(String category, int SH, int SM, int EH, int EM){
        int index=CategoryList.indexOf(category);
        /*for (int i = 0; i < CategoryList.size(); i++) {  //한글 비교 불가
            *//*if (category == CategoryList.get(i)) {
                index = i;
            }*//*
            index=CategoryList.indexOf(category);
        }*/

        if (index < 0) {
            return 0;
        }
        switch(GoalType.get(index)){
            case 1:{
                if (SH > EH) {
                    TimeData = (24 - SH) + EH + ((60 - SM) + EM) / 60;
                } else {
                    TimeData = (EH - SH) + (EM - SM) / 60;
                }

                /*if (TimeData >= 6) {  //6시간 이상일 때와 아닐 때의 칸수 조절(안하면 한쪽만 너무 크게 증가)
                    TimeData = (TimeData / 3) * 20;
                } else {
                    TimeData = (TimeData / 2) * 20;
                }*/
            }break;

            case 2:
                TimeData = 1;
                break;

            default:
                TimeData=0;
                break;
        }
        return TimeData;
    }


    public float calCategoryData(ArrayList<Float> StateDataList){
        float TotalData=0;
        for(int i=0;i<StateDataList.size();i++){
            TotalData+=StateDataList.get(i);
        }
        return TotalData;
    }

    public ArrayList<Boolean> isGoalAchieved(ArrayList<Float> TotalTimeList){
        ArrayList<Boolean> IsGoalAchieved=new ArrayList<>();
        /*for(int i=0;i<TotalTimeList.size();i++){
            IsGoalAchieved.add(false);
        }*/
        for(int i=0;i<TotalTimeList.size();i++){
            switch(GoalType.get(i)){
                case 1:{
                    if(TotalTimeList.get(i)>=GoalList.get(i)){
                        IsGoalAchieved.set(i,true);
                    }else{
                        IsGoalAchieved.set(i,false);
                    }
                }break;

                case 2:{
                    if(TotalTimeList.get(i)==GoalList.get(i).floatValue()){
                        IsGoalAchieved.set(i,true);
                    }else{
                        IsGoalAchieved.set(i,false);
                    }
                }break;

            }
        }
        return IsGoalAchieved;
    }


}
