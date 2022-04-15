package com.example.routinepartner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    LottieAnimationView PetView;
    TextView PetName;
    Button btn_NameSet;
    String action="meal"; //actinfoitem 에서 가져옴

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PetView=findViewById(R.id.lottie);
        PetView.setAnimation("HappyDog.json");
        PetView.setRepeatCount(2);  
        PetView.playAnimation();

        PetView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                ImageView img=(ImageView) findViewById(R.id.imageView);
                switch(action){
                    case "meal":
                        img.setImageResource(R.drawable.foodbowl);
                        fadeOutImage(img);
                        break;
                    case "sleep":
                        img.setImageResource(R.drawable.bed);
                        fadeOutImage(img);
                        break;
                    case "study":
                        img.setImageResource(R.drawable.book);
                        fadeOutImage(img);
                        break;
                    default:
                }

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                PetView.setOnClickListener((view -> {
                    PetView.setRepeatCount(1);  //app:lottie_loop="true"
                    PetView.playAnimation();
                }));

                PetView.setOnLongClickListener((view) -> {
                    action="interaction";
                    ImageView HeartImage=findViewById(R.id.HeartImage);
                    HeartImage.setImageResource(R.drawable.heart);
                    fadeInImage(HeartImage);
                    PetView.setRepeatCount(1);
                    PetView.playAnimation();
                    return true;
                });

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        PetName =(TextView) findViewById(R.id.PetName);
        btn_NameSet=(Button)findViewById(R.id.btn_NameSet);

        btn_NameSet.setOnClickListener((view)->{
            AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("펫 이름 설정");
            builder.setMessage("이름을 입력하시오.");
            final EditText editText=new EditText(MainActivity.this);
            builder.setView(editText);

            builder.setPositiveButton("입력", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog,int which){
                    String name;
                    name=editText.getText().toString();
                    PetName.setText(name);
                    dialog.dismiss();
                    //btn_NameSet.setText("이름변경");
                }
            });
            builder.setNegativeButton("취소",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            builder.show();

        });

    }

    private void fadeOutImage(final ImageView img) {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(2000);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                img.setVisibility(View.GONE); }
            public void onAnimationRepeat(Animation animation) {

            }
            public void onAnimationStart(Animation animation) {}
        });
        img.startAnimation(fadeOut);
    }

    private void fadeInImage(final ImageView img) {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new AccelerateInterpolator());
        fadeIn.setDuration(2000);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                img.setVisibility(View.GONE); }
            public void onAnimationRepeat(Animation animation) {

            }
            public void onAnimationStart(Animation animation) {}
        });
        img.startAnimation(fadeIn);
    }
}