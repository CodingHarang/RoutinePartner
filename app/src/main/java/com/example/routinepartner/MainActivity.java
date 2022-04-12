package com.example.routinepartner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    LottieAnimationView PetView;
    TextView PetName;
    Button btn_NameSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PetView=findViewById(R.id.lottie);
        PetView.setAnimation("HappyDog.json");
        PetView.setRepeatCount(3);  //app:lottie_loop="true"
        PetView.playAnimation();

        PetView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                PetView.setOnClickListener((view -> {
                    PetView.setRepeatCount(1);  //app:lottie_loop="true"
                    PetView.playAnimation();
                }));

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
}