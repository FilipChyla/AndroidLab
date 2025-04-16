package pl.pollub.android.myapplication.phonesExercise.data;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Transaction;

import java.util.List;

public class PhoneRepository {
    private PhoneDao phoneDao;
    private LiveData<List<PhoneEntity>> allPhones;

    public PhoneRepository(Application application){
        PhoneDatabase phoneDatabase = PhoneDatabase.getDatabase(application);

        phoneDao = phoneDatabase.phoneDao();
        allPhones = phoneDao.getAllPhones();
    }

    public LiveData<List<PhoneEntity>> getAllPhones(){
        return allPhones;
    }

    public void deleteAll(){
        PhoneDatabase.databaseWriteExecutor.execute(() -> phoneDao.deleteAll());
    }

    public void addPhone(PhoneEntity phone){
        PhoneDatabase.databaseWriteExecutor.execute(() -> phoneDao.insertPhone(phone));
    }

    public void deleteOne(PhoneEntity phone){
        PhoneDatabase.databaseWriteExecutor.execute(() -> phoneDao.deletePhone(phone));
    }

    public void resetDatabase(){
        deleteAll();
        addPhone(new PhoneEntity("Samsung","Galaxy S4", "4.2.2", "https://en.wikipedia.org/wiki/Samsung_Galaxy_S4"));
        addPhone(new PhoneEntity("Huawei","P30", "9", "https://en.wikipedia.org/wiki/Huawei_P30"));
        addPhone(new PhoneEntity("Oppo","A5", "9", "https://en.wikipedia.org/wiki/Oppo_A9_2020"));
        addPhone(new PhoneEntity("Xiaomi","Redmi Note 8", "9", "https://en.wikipedia.org/wiki/Redmi_Note_8"));
        addPhone(new PhoneEntity("OnePlus","6T", "11", "https://en.wikipedia.org/wiki/OnePlus_6T"));
    }
}
