package pl.pollub.android.myapplication.phonesExercise.view;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import pl.pollub.android.myapplication.phonesExercise.data.PhoneEntity;
import pl.pollub.android.myapplication.phonesExercise.data.PhoneRepository;

public class PhoneViewModel extends AndroidViewModel {
    private final PhoneRepository phoneRepository;
    private final LiveData<List<PhoneEntity>> allElements;
    public PhoneViewModel(@NonNull Application application) {
        super(application);
        phoneRepository = new PhoneRepository(application);
        allElements = phoneRepository.getAllPhones();
    }

    public LiveData<List<PhoneEntity>> getAllPhones(){
        return allElements;
    }
    public void deleteAll(){
        phoneRepository.deleteAll();
    }
    public void resetDatabase(){
        phoneRepository.resetDatabase();
    }
    public void addPhone(PhoneEntity phone){
        phoneRepository.addPhone(phone);
    }
    public void deleteOne(PhoneEntity phone){
        phoneRepository.deleteOne(phone);
    }
}
