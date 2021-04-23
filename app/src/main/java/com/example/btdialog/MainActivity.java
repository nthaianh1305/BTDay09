package com.example.btdialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.PopupMenu;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.btdialog.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private static final int SELECTED_IMAGE_NAME = 222;
    String selectedImagePath;
    int gio = 14;
    int phut = 20;
    int ngay = 4;
    int thang = 3;
    int nam = 2021;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        binding.tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog timePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        ngay = dayOfMonth;
                        thang = month;
                        nam = year;
                        binding.tvDate.setText(ngay+"/"+(thang+1)+"/"+nam);
                    }
                }, nam, thang, ngay);
                timePickerDialog.show();
            }
        });
        binding.tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        gio = hourOfDay;
                        phut = minute;
                        binding.tvTime.setText(hourOfDay+":"+minute);
                    }
                },gio,phut,true);
                timePickerDialog.show();

            }
        });

        binding.btnTune.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this,binding.btnTune);
                popupMenu.inflate(R.menu.menu_tune);

                Menu menu = popupMenu.getMenu();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.mn_file:
                                Toast.makeText(getBaseContext(),"From file",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent,"Select Picture"),SELECTED_IMAGE_NAME);
                                break;
                            case R.id.mn_defaults:
                                String[] listTune = {"Nexus Tune","Winphone Tune","Peep Tune","Nokia Tune","Etc"};
                                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                                        .setTitle("")
                                        .setSingleChoiceItems(listTune, 0, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //Toast.makeText(getBaseContext(),listTune[which],Toast.LENGTH_LONG).show();
                                                binding.tvPath.setText(listTune[which]);
                                            }
                                        })
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        })
                                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        })
                                        .create();
                                alertDialog.show();
                                break;
                            default:
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        binding.tvTag.setOnClickListener(new View.OnClickListener() {
            String[] strings = {"Family","Game","Android","VTC","Friend"};
            boolean[] booleans = {true,false,true,false,true};
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Choose tags:")
                        .setMultiChoiceItems(strings, booleans, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String s = "";
                                for(int i = 0; i<strings.length;i++){
                                    if (booleans[i] == true)
                                        s+=strings[i]+ " ";
                                }
                                binding.tvTag.setText(s);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create();
                alertDialog.show();
            }
        });

        binding.tvWeek.setOnClickListener(new View.OnClickListener() {
            String[] strings = {"Sun","Mon","Tues","Wed","Thu","Fri","Sat"};
            boolean[] booleans = {true,false,true,false,true,false,false};
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Choose Week")
                        .setMultiChoiceItems(strings, booleans, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String s = "";
                                for(int i = 0; i<strings.length;i++){
                                    if (booleans[i] == true)
                                        if(i==0)
                                            s+=strings[i]+ " ";
                                        else
                                            s+=", "+ strings[i]+ " ";
                                }
                                binding.tvWeek.setText(s);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create();
                alertDialog.show();
            }
        });
        //Spinner
        List<String> list = new ArrayList<>();
        list.add("Work1");
        list.add("Work2");
        list.add("Work3");
        list.add("Work4");
        list.add("Work5");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,list);
        binding.spin.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.menu_save,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnCancel:
                Toast.makeText(getBaseContext(),"Cancel",Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SELECTED_IMAGE_NAME && resultCode == Activity.RESULT_OK){
            try {
                Uri imageUri = data.getData();
                selectedImagePath = getPath(imageUri);
                binding.tvPath.setText("Image path: "+selectedImagePath);
                binding.ivImage.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri));
            }catch (Exception e){
                Toast.makeText(getBaseContext(),e.toString(),Toast.LENGTH_LONG).show();
            }
            return;
        }
    }

    private String getPath(Uri imageUri) {
        String[] strings = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContentResolver().query(imageUri,strings,null,null,null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            String filePath = cursor.getString(column_index);
            cursor.close();
            return filePath;
        }
        return imageUri.getPath();
    }
}