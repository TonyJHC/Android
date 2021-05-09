package com.hansung.android.homework2;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MonthFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    FragmentActivity listener;

    int year;
    int month;
    int count ;

    int today_of_week; // 오늘이 무슨 요일인지
    int lastDay; //해당 달의 마지막 일수
    int firstDay_of_month; // 해당 달의 1일의 요일
    String[] items;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MonthFragment() {
        // Required empty public constructor

    }

    //newInstance 구현
    public  static MonthFragment newInstance(int param1 , int param2) { //PagerAdapter에서 호출시 MonthFragment 객체를 생성후 return
        MonthFragment fragment = new MonthFragment();
        Bundle args = new Bundle();

        args.putInt(ARG_PARAM1,param1);
        args.putInt(ARG_PARAM1,param2);

        fragment.setArguments(args);
        return fragment;
    }

    // TODO: Rename and change types and number of parameters
    //팩토리 메소드 : 객체 인스턴스를 만들어서 Bundle 타입에 정보를 담아서(키:값) fragment객체를 전달


    //해당 Fragment와 연결된 액티비티의 reference 가져오기 -->어댑터에 연결하기 위함
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof Activity){
            this.listener = (FragmentActivity) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            year = getArguments().getInt(ARG_PARAM1,-99);
            month = getArguments().getInt(ARG_PARAM2,-99);

            System.out.println("onCreate");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 이전 프래그먼트에서 전달한 번들 저장
        Bundle bundle = getArguments();
       // PagerAdapter adapter = new PagerAdapter(position);
        year = bundle.getInt(ARG_PARAM1,-99);
        month = bundle.getInt(ARG_PARAM2,-99);


        if (month >= 12) {  //12월이 넘어가면 13 14 .. 가 되는것을 방지
            month = 0;
        }

        if (month == -1) {
            month = 11;
        }

        if (year == -99 || month == -99 ){  //처음 실행했을때만
            //현재 년도와 월을 알아보기
            //해당 되는 월의 마지막날은 언제인지도 필요함 (인터넷)
            //Calendar.getInstance()  : 현재 시간 저장
            count = 0;
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

        lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);  //이번달 일 수
        lastDay += 7; //6 x 7그리드뷰의 빈공간이 없도록
        System.out.println("lastDay"+lastDay);

        //데이터 원본 준비 (Adapter에 연결할)
        items  = new String[lastDay]; //마지막 날짜를 받아와서 인덱스로
        int j = 0;
        //---------------데이터(items 스트링 배열) 구축 알고리즘 -------------------
        //첫주에대한 들여쓰기(1일의 요일에 따라)
        for(int i = 0; i< firstDay_of_month; i++){ //월요일 : firstDay_of_week == 1
            items[i] = (String) "";
            ++j ;
        }

        //날짜 채우기
        int k = lastDay-7+j ; // 들여쓰기 만큼 더해주기
        for(int i = j; i < k; i++){ //들여쓰기 이후부터 날짜 채우기
            items[i] = (String) (i-j+1 + "");
        }

        for(int i = k; i < lastDay; i++  ){
            items[i] = (String) "";
        }

        ArrayAdapter<String> adapt = new ArrayAdapter<String>(listener,
                android.R.layout.simple_list_item_1,
                items);

        //rootview생성
        View view = inflater.inflate(R.layout.fragment_month, container, false);

        //버튼과  년월 텍스트를 id로 참조
        TextView yearMonth = view.findViewById(R.id.year_month);
        yearMonth.setText(year +"년 " + (month+1) + "월"); //text뷰 컨텐츠 내용을 실행 시 해당 내용으로 변경 해줌 --> 현재 년 월 을 알아야 함

        //어댑터와 어댑터뷰(GridView)를 연결
        //id를 바탕으로 화면 레이아웃에 정의된 GridView 객체 로딩
        GridView gridview = (GridView) view.findViewById(R.id.gridview);
        //어댑터를 GridView 객체에 연결
        gridview.setAdapter(adapt);
        System.out.println("어댑터연결");


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  //클릭이벤트 처리를 위해 리스트뷰 사용(인터넷)
                String item = String.valueOf(parent.getItemAtPosition(position));
                //날짜를 누르면 토스트메시지로 뜸
                Toast.makeText(listener, year + "." + (month + 1) + "." + item, Toast.LENGTH_SHORT).show();
                //백그라운 변경 하셈
                gridview.setBackgroundColor(Color.CYAN);
                //셀 범위 설정하기
                //다른 날짜 클릭시 배경 변경
            }
        });




        //그리드뷰 객체에 어댑터 연결
        MonthFragment fragment = new MonthFragment(); //DetailsFragment객체 생성
        //bundle.putInt("year",-99);
        //if() {// 오른쪽으로 스와이프 했을때 +1
            bundle.putInt(ARG_PARAM1, year + 1); // 인자 값을 (키,값) 페어로 번들 객체에 설정
            bundle.putInt(ARG_PARAM2, month + 1); // 인자 값을 (키,값) 페어로 번들 객체에 설정
            bundle.putInt("position",count + 1); //스와이프시 카운트 증가
            fragment.setArguments(bundle); //인자값을 저장한 번들 객체를 프래그먼트로 전달

        return view;
    }



}