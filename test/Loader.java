
package test;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import test.Owner.OwnerGender;
import test.Pet.PetType;

/**
 * Created by markgarab on 8/5/18.
 *
 * Boilerplate class to load, bucket-by-owner, sort the pets, print cats
 */

public class Loader {

    /**
     * Owner-gender buckets of cats
     */
    private Map<String, List<Pet>> mOwners = new HashMap<>();

    public List<Owner> readData(Reader reader) {
        Type listType = new TypeToken<List<Owner>>(){}.getType();

        return new Gson().fromJson(reader, listType);
    }

    public void bucketPets(List<Owner> owners) {
        mOwners.put(OwnerGender.MALE, new ArrayList<Pet>());
        mOwners.put(OwnerGender.FEMALE, new ArrayList<Pet>());

        for (Owner owner : owners) {
            if (owner.getOwnerPets() != null) {

                // Assuming all pets are different animals despite their names may match
                // do not filter dogs out yet, it's faster to iterate and ignore then to add/remove,
                // given that the filtered collections still operate on calculating the filter-predicate

                mOwners.get(owner.getOwnerGender()).addAll(owner.getOwnerPets());
            }
        }
    }

    public void parse () {
        URL url;
        HttpURLConnection connection = null;
        Reader reader = null;
        try {
            // For simplicity of setup using HttpUrlConnection
            // Retrofit would be a cleaner solution but it's setup overhead is out of scope for this task

            url = new URL("http://agl-developer-test.azurewebsites.net/people.json");
            connection = (HttpURLConnection)url.openConnection();
            int responseCode = connection.getResponseCode();
            if (responseCode / 100 != 2) {
                // Not a success response
                Log.e("Error", "The request returned " + responseCode + " failure");
                return;
            }

            reader = new InputStreamReader(new BufferedInputStream(connection.getInputStream()));

            //read and distribute into owner-gender buckets
            bucketPets(readData(reader));


            // sort alphabetically by pet name
            Collections.sort(mOwners.get(OwnerGender.MALE));
            Collections.sort(mOwners.get(OwnerGender.FEMALE));

            // print in the Log

            // Male
            List<Pet> pets = mOwners.get(OwnerGender.MALE);
            Log.d("PETS", "MALE");
            for (Pet pet : pets) {
                if (pet.getPetsType().equals(PetType.CAT)) {
                    Log.d("PETS", "\t" + pet.getPetsName());
                }
            }

            //Female
            pets = mOwners.get(OwnerGender.FEMALE);
            Log.d("PETS", "FEMALE");
            for (Pet pet : pets) {
                if (pet.getPetsType().equals(PetType.CAT)) {
                    Log.d("PETS", "\t" + pet.getPetsName());
                }
            }
        } catch (Exception exp) {
            // No specific error handling is required. Do Catch-all case, log and finish.
            Log.e("Error", exp.getMessage(), exp);
        } finally {
            try {
                if (connection != null && reader != null) {
                    reader.close();
                    connection.disconnect();
                }
            } catch (Exception exp) {
                Log.e("Error", exp.getMessage(), exp);
            }
        }
    }
}
