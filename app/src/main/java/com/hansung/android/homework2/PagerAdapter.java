package com.hansung.android.homework2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.Calendar;

import static java.lang.Integer.MAX_VALUE;

public class PagerAdapter extends FragmentStateAdapter {

    int year;
    int month;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public PagerAdapter(FragmentActivity fa) {
        super(fa);
    }

    // 각 페이지를 나타내는 프래그먼트 반환
    @Override
    public Fragment createFragment(int position) {
                MonthFragment monthFragment = new MonthFragment();
                System.out.println("createFragement ");
               // Bundle bundle = new Bundle();

        int year =0; // position에  따라 year를 계산하는 식은 따로 정의( 알고리즘 짜야됨)
        int month =0; //position에 따라 month를 계산 하는 식은 따로 정의 (지금 이것도 걍 임의로 해놓은 것)

        //이번달이 어느달인지에따라

        if(position > 11) //position이 11보다 크다면
            position = 0 ; //다시 0으로 초기화
            year = Calendar.getInstance().get(Calendar.YEAR) + 1 ;

        System.out.println("position"+position);

        switch(Calendar.getInstance().get(Calendar.MONTH)){
            case 0: //첫달이 1월일때
                month = position +1;
                year = Calendar.getInstance().get(Calendar.YEAR);
            case 1:
                month = position +2;
                year = Calendar.getInstance().get(Calendar.YEAR);

            case 2:
                month = position +3;
                year = Calendar.getInstance().get(Calendar.YEAR);

            case 3:
                month = position +4;
                year = Calendar.getInstance().get(Calendar.YEAR);

            case 4:
                month = position +5;
                year = Calendar.getInstance().get(Calendar.YEAR);

            case 5:
                month = position +6;
                year = Calendar.getInstance().get(Calendar.YEAR);

            case 6:
                month = position +7;
                year = Calendar.getInstance().get(Calendar.YEAR);

            case 7:
                month = position +8;
                year = Calendar.getInstance().get(Calendar.YEAR);

            case 8:
                month = position +9;
                year = Calendar.getInstance().get(Calendar.YEAR);

            case 9:
                month = position +10;
                year = Calendar.getInstance().get(Calendar.YEAR);

            case 10:
                month = position +11;
                year = Calendar.getInstance().get(Calendar.YEAR);

            case 11: //첫달이 12월 일때
                month = position +12;
                year = Calendar.getInstance().get(Calendar.YEAR);

        }
        //bundle.putString
                return monthFragment.newInstance(year,month); //포지션 정보를 newInstance에 전달후 fragment를  return받음 --> fragment를 return
    }


    // 전체 페이지 개수 반환
    @Override
    public int getItemCount() {
        //return NUM_ITEMS;
        return MAX_VALUE; //무한으로 생성
    }
}