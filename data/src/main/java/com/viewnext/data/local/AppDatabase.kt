package com.viewnext.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.viewnext.data.local.dao.FacturaDao;
import com.viewnext.data.local.entity.FacturaEntity;

/**
 * Base de datos Room para la aplicación. Esta clase define la base de datos
 * y proporciona acceso a las entidades y DAO (Data Access Objects).
 * @see FacturaDao
 * @see FacturaEntity
 */
@Database(entities = {FacturaEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    /**
     * Proporciona acceso al DAO para las operaciones con las facturas.
     * @return Un objeto {@link FacturaDao} para acceder a la base de datos de facturas.
     */
    public abstract FacturaDao facturaDao();

    // Instancia singleton de la base de datos
    private static volatile AppDatabase INSTANCE;

    /**
     * Obtiene la instancia única de la base de datos.
     * Utiliza el patrón Singleton para asegurar que solo haya una instancia de la base de datos
     * durante el ciclo de vida de la aplicación.
     * @param context El contexto de la aplicación, utilizado para crear la base de datos.
     * @return La instancia única de {@link AppDatabase}.
     */
    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "app_db"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}
