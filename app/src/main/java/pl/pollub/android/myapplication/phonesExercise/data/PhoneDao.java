package pl.pollub.android.myapplication.phonesExercise.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PhoneDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPhone(PhoneEntity phone);

    @Query("SELECT * FROM phones")
    LiveData<List<PhoneEntity>> getAllPhones();

    @Delete
    void deletePhone(PhoneEntity phone);

    @Query("DELETE FROM phones")
    void deleteAll();

}
