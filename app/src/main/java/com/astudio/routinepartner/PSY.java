package com.astudio.routinepartner;

import java.util.ArrayList;

public class PSY {
    //PetStateManager
    //ActInfo에서 받아온 시간data를 수치로 계산
    //줄어들기
    //Pet 떠나갈 상태
    private float TimeData =0;
    private float CategoryData=0;

    private ArrayList<Float> SleepStateData=new ArrayList<>();
    private ArrayList<Float> EatStateData=new ArrayList<>();
    private ArrayList<Float> StudyStateData=new ArrayList<>();


    public void calRadarValue(String category,int starthour,int startmin,int endhour,int endmin){
        if(starthour>endhour){
            TimeData =(24-starthour)*60+(60-startmin)+endhour+endmin;
            TimeData /=20;
        }else{
            TimeData =(endhour-starthour)*60+(endmin-startmin);
            TimeData /=20;
        }

        switch(category){
            case "Sleep":
                SleepStateData.add(TimeData);
                break;

            case "Eat":
                EatStateData.add(TimeData);
                break;

            case "Study":
                StudyStateData.add(TimeData);
                break;

            default:
                break;
        }

    }

    public float calCategoryData(String category){

        switch(category){
            case "Sleep":
                for(int i=0;i<SleepStateData.size();i++){
                    CategoryData+= SleepStateData.get(i);
                }
                break;

            case "Eat":
                for(int i=0;i<EatStateData.size();i++){
                    CategoryData+= EatStateData.get(i);
                }
                break;

            case "Study":
                for(int i=0;i<StudyStateData.size();i++){
                    CategoryData+= StudyStateData.get(i);
                }
                break;

            default:
                break;
        }

        return CategoryData;
    }

}
