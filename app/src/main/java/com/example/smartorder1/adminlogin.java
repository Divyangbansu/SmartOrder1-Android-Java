package com.example.smartorder1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.smartorder1.R;

public class adminlogin extends AppCompatActivity {
    EditText name,pass;
    Button btsub;
    final String getname="aa";
    final String getpass="aa";
    AwesomeValidation aw;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminlogin);
        name=(EditText)findViewById(R.id.nameuser);
        pass=(EditText)findViewById(R.id.password);
        btsub=(Button) findViewById(R.id.butsub);
        aw = new AwesomeValidation(ValidationStyle.BASIC);
        aw.addValidation(this,R.id.nameuser, getname,R.string.err_name);
        aw.addValidation(this,R.id.password, getpass,R.string.err_pass);
        aw.addValidation(this,R.id.nameuser, RegexTemplate.NOT_EMPTY,R.string.invalid_name);
        aw.addValidation(this,R.id.password, RegexTemplate.NOT_EMPTY,R.string.invalid_password);

        btsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aw.validate())
                {
                    startActivity(new Intent(getApplicationContext(),AdminManage.class));
                    Toast.makeText(getApplicationContext(),"Entered Successfully ! ",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
