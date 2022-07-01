package com.example.smartorder1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

public class managetaxes extends AppCompatActivity {

    Button taxbut;
    EditText cgsttext,sgsttext;
    public static double stax,ctax;
    AwesomeValidation aw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managetaxes);
        taxbut=(Button)findViewById(R.id.updatetax);
        cgsttext=(EditText) findViewById(R.id.csgtid);
        sgsttext=(EditText) findViewById(R.id.sgstid);
        aw = new AwesomeValidation(ValidationStyle.BASIC);
        aw.addValidation(this,R.id.csgtid,"^[0-9]+(\\.){0,1}[0-9]*$",R.string.err_tax);
        aw.addValidation(this,R.id.sgstid,"^[0-9]+(\\.){0,1}[0-9]*$",R.string.err_tax);
        aw.addValidation(this,R.id.csgtid, RegexTemplate.NOT_EMPTY,R.string.err_emp);
        aw.addValidation(this,R.id.sgstid, RegexTemplate.NOT_EMPTY,R.string.err_emp);
        taxbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aw.validate())
                {
                    stax=Double.parseDouble(sgsttext.getText().toString().trim());
                    ctax=Double.parseDouble(cgsttext.getText().toString().trim());
                    Toast.makeText(getApplicationContext(),sgsttext.getText().toString()+cgsttext.getText().toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
