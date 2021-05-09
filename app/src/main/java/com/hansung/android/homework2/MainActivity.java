package com.hansung.android.homework2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private java.lang.Object Object;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager2 vpPager = findViewById(R.id.vpPager);
        FragmentStateAdapter adapter = new PagerAdapter(this);
        vpPager.setAdapter(adapter);


        vpPager.setCurrentItem(0); // ViewPager2 객체의 현재 페이지를 설정 :2 --> thirdFragment

        // Attach the page change listener inside the activity
        //
        vpPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                Toast.makeText(MainActivity.this,
                        "Selected page position: " + position, Toast.LENGTH_SHORT).show();
            }
        });

        
        //메인액티비티에서 캘린더를 viewpager layout에 속하도록 해줘야됨
        //fragment에 달력을 넣은 것 처럼
        //viewpager 어댑터가 필요함 --> 검색
        //viewpager에서 아이템 갯수를 return해주는 메소드가 있다.(override --> ctrl 5)
        //


        // MainActivity에서 DetailsActivity로 전달된 인텐트의 Extra에서 이름이 "index"인 int형 값을 뽑아와서
        // 새로이 생성된 DetailsFragment 객체에 전달


        int mParam1 = Calendar.getInstance().get(Calendar.YEAR);  //Calender 객체를 return : 호출 시점의 년도
        int mParam2 = Calendar.getInstance().get(Calendar.MONTH);  //Calender 객체를 return : 호출 시점의 월 --> 월 정보는 0~ 11까지 나옴 따라서 +1

        MonthFragment weeks = MonthFragment.newInstance(mParam1,mParam2);
                //getIntent().getIntExtra("index",-1));  //getIntent()함수로 Intent객체를 얻어오고 객체안에 있는 Extra에 저장된 키와 값(값이 정수형이getIntExtra, 문자열이면 getStringExtra)을 가져옴
        // --> Extra내부에 "index"라는 이름의 문자열이 있다면 저장된 값이 반환됨( ex) 항목번호)
        // 새로이 생성된 MonthFragment 객체를 기존 것과 교체
        //FragmentManager를 가져온다음 beginTransaction()을 호출하여 FragmentTransaction을 생성하고 replace()호출하여 프래그먼트를 교체(동적) --> 변경할 준비가 끝나면 commit을 호출해야됨

        //런타임 시 활동에 프래그먼트 추가(동적으로 추가하기)
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, weeks).commit();

    }

    //출처: https://duzi077.tistory.com/282 [개발하는 두더지]
    //화면 회전을 위한 함수
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.monthview:
                Toast.makeText(getApplicationContext(), "action_quick", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.weekview:
                Toast.makeText(getApplicationContext(), "action_settings", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}




