package com.example.pr177;


import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    DBHelper dbHelper;
    EditText editTextAnimal, editTextName, editTextSize, editTextWeight;
    Button btnRead1;

    String LOG_TAG = "Hello";

    SQLiteDatabase db = dbHelper.getWritableDatabase();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Кнопки
        btnRead1 = findViewById(R.id.btnRead);
        btnRead1.setOnClickListener(this);

        dbHelper = new DBHelper(this);

        ContentValues cv = new ContentValues();

        String Animal = editTextAnimal.getText().toString();
        String Name = editTextName.getText().toString();
        String Size = editTextSize.getText().toString();
        String Weight = editTextWeight.getText().toString();


        cv.put("Animal", Animal);
        cv.put("Name", Name);
        cv.put("Size", Size);
        cv.put("Weight", Weight);

        long rowID = db.insert("mytable", null, cv);
        Log.d(LOG_TAG, "row inserted, ID = "+ rowID);


    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnRead){
            Log.d(LOG_TAG, "--- Rows in mytable: ---");
            Cursor c = db.query("mytable", null, null, null, null, null, null);
            if(c.moveToFirst()){
                int idColIndex = c.getColumnIndex("id");
                int AnimalColIndex = c.getColumnIndex("Animal");
                int NameColIndex = c.getColumnIndex("Name");
                int SizeColIndex = c.getColumnIndex("Size");
                int WeightColIndex = c.getColumnIndex("Weight");
                do{
                    Log.d(LOG_TAG,
                            "ID = " + c.getInt(idColIndex) +
                                    ", Animal = " + c.getString(AnimalColIndex) +
                                    ", Name = " + c.getString(NameColIndex) +
                                    ", Size = " + c.getString(SizeColIndex) +
                                    ", Weight = " + c.getString(WeightColIndex));
                } while (c.moveToNext());
            }else{
                Log.d(LOG_TAG, "0 rows");
                c.close();
            }
            if (v.getId()==R.id.btnClear){
                Log.d(LOG_TAG, "--- Clear mytable: ---");
                int clearCount = db.delete("mytable", null, null);
                Log.d(LOG_TAG, "deleted rows count = "+ clearCount);
                dbHelper.close();

            }
        }

    }
}