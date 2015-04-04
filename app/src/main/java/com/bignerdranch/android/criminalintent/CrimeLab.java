package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by minggaoxi on 2/22/15.
 */
public class CrimeLab {
    private static final String TAG = "CrimeLab";
    private static final String FILENAME = "crimes.json";

    private CriminalIntentJSONSerializer serializer;

    private static CrimeLab crimeLab;
    private Context appContext;

    private ArrayList<Crime> crimes;

    private CrimeLab(Context appContext) {
        this.appContext = appContext;
        serializer = new CriminalIntentJSONSerializer(appContext, FILENAME);

        try {
            crimes = (ArrayList<Crime>) serializer.loadCrimes();
        } catch (Exception e) {
            crimes = new ArrayList<>();
            Log.e(TAG, "Error loading crimes: ", e);
        }
    }

    public static CrimeLab get(Context c) {
        if (crimeLab == null) {
            crimeLab = new CrimeLab(c.getApplicationContext());
        }
        return crimeLab;
    }

    public ArrayList<Crime> getCrimes() {
        return crimes;
    }

    public Crime getCrime(UUID id) {
        for (Crime c : crimes) {
            if (c.getId().equals(id))
                return c;
        }
        return null;
    }

    public void addCrime(Crime c) {
        crimes.add(c);
    }

    public void deleteCrime(Crime c) {
        crimes.remove(c);
    }

    public boolean saveCrimes() {
        try {
            serializer.saveCrimes(crimes);
            Log.d(TAG, "crimes saved to file");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error saving crimes: ", e);
            return false;
        }
    }
}
