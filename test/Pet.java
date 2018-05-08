

package test;

import android.support.annotation.NonNull;
import android.support.annotation.StringDef;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Created by markgarab on 8/5/18.
 */

public class Pet implements Comparable<Pet> {

    @StringDef({
            PetType.CAT,
            PetType.DOG
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface PetType {
        String CAT = "Cat";
        String DOG = "Dog";
    }

    @NotNull
    @SerializedName("name")
    String mPetsName;

    @NotNull
    @PetType
    @SerializedName("type")
    String mPetsType;

    public Pet() {}

    public Pet(@NotNull String name, @NotNull @PetType String type) {
        mPetsName = name;
        mPetsType = type;
    }

    public String getPetsName() {
        return mPetsName;
    }

    public void setPetsName(String petsName) {
        mPetsName = petsName;
    }

    public String getPetsType() {
        return mPetsType;
    }

    public void setPetsType(String petsType) {
        mPetsType = petsType;
    }

    @Override
    public int compareTo(@NonNull Pet other) {
        return this.mPetsName.compareTo(other.mPetsName);
    }
}
