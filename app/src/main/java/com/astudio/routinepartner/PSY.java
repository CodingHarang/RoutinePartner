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
    public static boolean IsAffectionMinus=false;
    private int FailNum=0;
    ArrayList<String> CategoryList=SavedSettings.CategoryList;
    ArrayList<String> CategoryStat =SavedSettings.StatList;
    ArrayList<Integer> GoalList=SavedSettings.Goal;
    ArrayList<Integer> GoalType =SavedSettings.GoalType;
    ArrayList<String> StatList = new ArrayList<>(Arrays.asList("지능", "재미", "체력", "포만감", "잔고", "자아실현"));
    ArrayList<Integer> CategoryStatInt=new ArrayList<>(Arrays.asList(3,2,0,2));
    ArrayList<Integer> Order=new ArrayList<>(Arrays.asList(1,2,3,4));

    public float calTimeValue(String category, int SH, int SM, int EH, int EM){
        int index=CategoryList.indexOf(category);

        if (index < 0) {
            return 0;
        }
        switch(GoalType.get(index)){
            case 2:{  //GoalType 2: 시간
                if (SH > EH) {
                    if(SH>=12){
                        TimeData = (24 - SH) + EH + ((60 - SM) + EM) / 60f;
                    }else{
                        TimeData =(EH+12)-SH+((60 - SM) + EM) / 60f;
                    }
                } else {
                    TimeData = (EH - SH) + (EM - SM) / 60f;
                }
            }break;

            case 1: //GoalType 1: 횟수
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

    public float applyGoal(float ByDateTotalTime, String category, int goaltype, boolean isToday){
        int index=CategoryList.indexOf(category);
        float ReturnValue=ByDateTotalTime;
        if(index<0){
            return 0f;
        }
        if(isToday&&ByDateTotalTime==0){
            return 0f;
        }
        switch(goaltype){
            case 1: { //횟수
                if(GoalList.get(index)<=ByDateTotalTime){
                    ReturnValue=10;
                }
                else{
                    ReturnValue=-10;
                }
            }break;
            case 2: {
                if(GoalList.get(index)<=ByDateTotalTime){
                    ReturnValue=10;
                }else{
                    ReturnValue=-10;
                }
            }break;
        }
        return ReturnValue;
    }

    public int subtractInteractionNum(ArrayList<Float> TodayDataList){
        int FailNum=0;
        if(!TodayDataList.isEmpty()){
            for(float data: TodayDataList){
                if(data<0){
                    FailNum++;
                }
            }
        }
        return FailNum;
    }
}
