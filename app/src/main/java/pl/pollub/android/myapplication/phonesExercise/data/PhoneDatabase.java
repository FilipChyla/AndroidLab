package pl.pollub.android.myapplication.phonesExercise.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {PhoneEntity.class}, version = 2, exportSchema = false)
public abstract class PhoneDatabase extends RoomDatabase {
    private static volatile PhoneDatabase INSTANCE;

    public abstract PhoneDao phoneDao();

    public static PhoneDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PhoneDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                                    PhoneDatabase.class,
                                                "phone_database")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();

                }
            }
        }
        return INSTANCE;
    }

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                PhoneDao dao = INSTANCE.phoneDao();
                dao.insertPhone(new PhoneEntity("Samsung","Galaxy S4", "4.2.2", "https://en.wikipedia.org/wiki/Samsung_Galaxy_S4"));
                dao.insertPhone(new PhoneEntity("Huawei","P30", "9", "https://en.wikipedia.org/wiki/Huawei_P30"));
                dao.insertPhone(new PhoneEntity("Oppo","A5", "9", "https://en.wikipedia.org/wiki/Oppo_A9_2020"));
                dao.insertPhone(new PhoneEntity("Xiaomi","Redmi Note 8", "9", "https://en.wikipedia.org/wiki/Redmi_Note_8"));
                dao.insertPhone(new PhoneEntity("OnePlus","6T", "11", "https://en.wikipedia.org/wiki/OnePlus_6T"));
            });
        }
    };
}
