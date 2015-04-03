package com.bignerdranch.android.criminalintent;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by minggaoxi on 4/3/15.
 */
public class CriminalIntentJSONSerializer {

    private Context context;
    private String filename;

    public CriminalIntentJSONSerializer(Context c, String f) {
        this.context = c;
        this.filename = f;
    }

    public List<Crime> loadCrimes() throws IOException, JSONException {
        List<Crime> crimes = new ArrayList<>();
        BufferedReader reader = null;

        try {
            FileInputStream in = context.openFileInput(filename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }

            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            for (int i = 0; i < array.length(); i++) {
                crimes.add(new Crime(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e) {

        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return crimes;
    }

    public void saveCrimes(List<Crime> crimes) throws IOException, JSONException {
        JSONArray array = new JSONArray();
        for (Crime c : crimes) {
            array.put(c.toJSON());
        }

        Writer writer = null;
        try {
            FileOutputStream outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(outputStream);
            writer.write(array.toString());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
