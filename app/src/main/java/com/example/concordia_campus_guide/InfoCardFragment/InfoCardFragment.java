//package com.example.concordia_campus_guide.InfoCardFragment;
//
//import android.os.Bundle;
//import android.view.View;
//import android.view.animation.TranslateAnimation;
//import android.widget.LinearLayout;
//
//import com.example.concordia_campus_guide.R;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//
//// This class is the view (UI) related things
//public class InfoCardFragment extends Fragment {
//    boolean opened;
//    LinearLayout view;
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        view = findViewById(R.id.view);
//        view.setVisibility(View.INVISIBLE);
//        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(!opened){
//                    view.setVisibility(View.VISIBLE);
//                    TranslateAnimation animate = new TranslateAnimation(
//                            0,
//                            0,
//                            view.getHeight(),
//                            0);
//                    animate.setDuration(500);
//                    animate.setFillAfter(true);
//                    view.startAnimation(animate);
//                } else {
//                    view.setVisibility(View.INVISIBLE);
//                    TranslateAnimation animate = new TranslateAnimation(
//                            0,
//                            0,
//                            0,
//                            view.getHeight());
//                    animate.setDuration(500);
//                    animate.setFillAfter(true);
//                    view.startAnimation(animate);
//                }
//                opened = !opened;
//            }
//        });
//    }
//}
