package com.vinh.doctor_x;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vinh.doctor_x.User.Doctor_class;

import java.util.ArrayList;

/**
 * Created by Vinh on 09-May-18.
 */

public class activity_infor_doctor extends AppCompatActivity
{
    ImageView avatar;
    TextView name,infor,countnumber;
    RatingBar rating;
    ListView listcomment;
    ArrayList<Comment> ar;
    adatapter adatapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infordoctor);
        avatar = (ImageView) findViewById(R.id.mg_avatar);
        name = (TextView) findViewById(R.id.name);
        countnumber = (TextView) findViewById(R.id.countnumber);
        rating = (RatingBar) findViewById(R.id.rating);
        infor = (TextView)findViewById(R.id.infor);
        listcomment = findViewById(R.id.listcomment);
        ar =new ArrayList<>();
        String i = getIntent().getStringExtra("key");

        ar.add(new Comment("Đoàn ","nice guy",4));
        ar.add(new Comment("Kiều Phong","good",5));
        ar.add(new Comment("Hư Trúc","very nice",3));
        ar.add(new Comment("Doãn Chí Bình","good",3));

        adatapter = new adatapter(activity_infor_doctor.this, ar);
        listcomment.setAdapter(adatapter);
        adatapter.notifyDataSetChanged();
        countnumber.setText(ar.size() + " reviews");


        DatabaseReference data = FirebaseDatabase.getInstance().getReference();

        data.child("doctor").child(i).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(activity_infor_doctor.this, "aaa", Toast.LENGTH_SHORT).show();
                Doctor_class doc = dataSnapshot.getValue(Doctor_class.class);
                name.setText(doc.getName());
                avatar.setImageBitmap(covertToBitmap(doc.getAvatar()));
                infor.setText("Address : " + doc.getAddress()+ " \nPhone : " + doc.getPhone() + "\nSpecialist : " + doc.getSpecialist() + "\nEmail : " + doc.getEmail());
                rating.setRating(Float.parseFloat(doc.getRating()+""));



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private Bitmap covertToBitmap(String string1) {
        byte[] decodedString = Base64.decode(string1, Base64.DEFAULT);

        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

    }
}
