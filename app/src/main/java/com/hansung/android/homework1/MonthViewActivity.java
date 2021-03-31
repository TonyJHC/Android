package com.hansung.android.homework1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.Calendar;

// MonthViewActivity로 바꿔야함 --> 직접 타이밍으로 바꾸면 안됨. xml파일등에서 이미 MainActivity로 쓰고 있기 때문에 관련된 정보 전부 바꾸기 --> refactor
public class MonthViewActivity extends AppCompatActivity {

    int year;
    int month;
    int today_of_week; // 오늘이 무슨 요일인지
    int lastDay; //해당 달의 마지막 일수
    int firstDay_of_month; // 해당 달의 1일의 요일

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //새롭게 생성될때 전에 생겼더 액티비티의 정보를 받아오기
        Intent intent =getIntent(); //해당 액티비티가 시작되었을 때 생긴 정보를 받기
        year = intent.getIntExtra("year",-1); //int형 정보 아무것도 없는 경우 -1을 return 해줘라
        month = intent.getIntExtra("month",-1); //int형 정보 아무것도 없는 경우 -1을 return 해줘라

        if (year == -1 || month == -1 ){
            //현재 년도와 월을 알아보기(인터넷 검색하면 엄청 많이 나옴)
            //해당 되는 월의 마지막날은 언제인지도 필요함 (인터넷)
            //Calendar.getInstance()  : 현재 시간 저장
            year = Calendar.getInstance().get(Calendar.YEAR);  //Calender 객체를 return : 호출 시점의 년도
            month = Calendar.getInstance().get(Calendar.MONTH);  //Calender 객체를 return : 호출 시점의 월 --> 월 정보는 0~ 11까지 나옴 따라서 +1
            //lastDay = Calendar.getInstance().get(Calendar.DATE); //이번달 마지막일수
            //오늘이 무슨 요일인지 return : ex) 3월 31일이
            today_of_week = Calendar.getInstance().get(Calendar.DAY_OF_WEEK); //일요일:1 ~ 토요일 : 7
            //이번달의 마지막 일 구하기
            lastDay = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH); //이번달 일 수
        }

        //해당 달의 첫 일을 구하기 위한 Calender 객체 생성
        Calendar cal = Calendar.getInstance();
        // 객체 초기 세팅
        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DATE,1);
        int tmp = cal.get(Calendar.DAY_OF_WEEK); // 1일이 무슨 요일인지 임시로 저장
        //1일의 요일 구하기
        if ( tmp == 1)  //일요일
            firstDay_of_month = 0;
        else if ( tmp == 2) //월요일
            firstDay_of_month = 1;
        else if ( tmp == 3) //화요일
            firstDay_of_month = 2;
        else if ( tmp == 4) //수요일
            firstDay_of_month = 3;
        else if ( tmp == 5) //목요일
            firstDay_of_month = 4;
        else if ( tmp == 6) //금요일
            firstDay_of_month = 5;
        else if ( tmp == 7) //토요일
            firstDay_of_month = 6;


        lastDay += 7;
        String[] items  = new String[lastDay]; //마지막 날짜를 받아와서 인덱스로

        int j = 0;
        //첫주에대한 들여쓰기(1일의 요일에 따라)
        for(int i = 0; i< firstDay_of_month; i++){ //월요일 : firstDay_of_week == 1
            items[i] = (String) "";
            ++j ;
        }
        //
        int k = lastDay-7+j ; // ex) 42 - 7 + 1
        for(int i = j; i < k; i++){
            items[i] = (String) (i-j+1 + "");
        }
        for(int i = k; i < lastDay; i++  ){
            items[i] = (String) "";
        }


        //어댑터 준비 (배열 객체 이용, simple_list_item_1 리소스 사용)
        ArrayAdapter<String> adapt
                = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                items);

        //버튼과  년월 텍스트를 id로 참조
        TextView yearMonth = findViewById(R.id.year_month);
        yearMonth.setText(year +"년 " + (month+1) + "월"); //text뷰 컨텐츠 내용을 실행 시 해당 내용으로 변경 해줌 --> 현재 년 월 을 알아야 함

        //어댑터와 어댑터뷰(GridView)를 연결
        //id를 바탕으로 화면 레이아웃에 정의된 GridView 객체 로딩
        GridView gridview = (GridView) findViewById(R.id.gridview);
        //어댑터를 GridView 객체에 연결
        gridview.setAdapter(adapt);


        //다음 버튼이 눌려졌을때 MonthViewActivity에서  MonthViewActivity로 정보 전달
        Button nextBtn = findViewById(R.id.next);
        //행동하기 onClick 이벤트 clicklistener
        //정의와 동시에 생성
        nextBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){ //onClick 이벤트가 발생했을 때 수행되는 코드
                //시작할 컴포넌트의 이름을 intent 객체에 설정(명시적 intent) : 현재 앱 안에 있는 componet를 시작할 때 사용
                Intent intent = new Intent(getApplicationContext(), //현재 Activity context 정보를 통신할 Activity에게 넘겨줌
                        MonthViewActivity.class); //, 시작시킬 클래스(MonthViewActivity)의 정보

                //새로 만들어진 Activity에 context 정보(년 월) 전달 --> 새로운 MonthViewActivity가 시작되면 return (intent객체에 담아져서)
                intent.putExtra("year",year);//키, 값 (다음 버튼 눌렀을때 년도 바뀌는 것도 구현해야됨 : 12월 지나면 2022년으로 변경되도록)
                intent.putExtra("month",month+1);//키, 값 (다음 버튼 눌렀을때 달이 바뀌니까 +1)

                startActivity(intent); //자기가 자기자신을 시작시킴 MonthViewActivity를 실행중인데 또 위에 쌓이는 구조 --> 스택처럼 쌓이기 때문에 메모리 부족해짐 --> 원래 떠있는거 지우고 새로 띄우는거 필요
                finish();//현재 Activity 지우고 새로운거만 띄우기 (뒤로가기 버튼 누르면 바로 사라짐)

            }
        });


    }

}