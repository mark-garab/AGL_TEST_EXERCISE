

package test;

import android.support.annotation.StringDef;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * Created by markgarab on 8/5/18.
 */

public class Owner {

    @StringDef({
            OwnerGender.MALE,
            OwnerGender.FEMALE
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface OwnerGender {
        String MALE = "Male";
        String FEMALE = "Female";
    }

    @NotNull
    @SerializedName("name")
    String mOwnerName;

    @NotNull
    @OwnerGender
    @SerializedName("gender")
    String mOwnerGender;

    @SerializedName("age")
    int mOwnerAge;

    @Nullable
    @SerializedName("pets")
    List<Pet> mOwnerPets;

    public Owner() {}

    public Owner(@NotNull String name, @NotNull @OwnerGender String gender, int age) {
        mOwnerName = name;
        mOwnerGender = gender;
        mOwnerAge = age;
    }

    public String getOwnerName() {
        return mOwnerName;
    }

    public void setOwnerName(String ownerName) {
        mOwnerName = ownerName;
    }

    public String getOwnerGender() {
        return mOwnerGender;
    }

    public void setOwnerGender(String ownerGender) {
        mOwnerGender = ownerGender;
    }

    public int getOwnerAge() {
        return mOwnerAge;
    }

    public void setOwnerAge(int ownerAge) {
        mOwnerAge = ownerAge;
    }

    public List<Pet> getOwnerPets() {
        return mOwnerPets;
    }

    public void setOwnerPets(List<Pet> ownerPets) {
        mOwnerPets = ownerPets;
    }
}
