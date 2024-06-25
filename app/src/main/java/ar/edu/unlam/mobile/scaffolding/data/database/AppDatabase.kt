package ar.edu.unlam.mobile.scaffolding.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ar.edu.unlam.mobile.scaffolding.data.database.dao.CoordinateDao
import ar.edu.unlam.mobile.scaffolding.data.database.dao.RouteDao
import ar.edu.unlam.mobile.scaffolding.data.database.dao.UserDao
import ar.edu.unlam.mobile.scaffolding.data.database.entity.Coordinate
import ar.edu.unlam.mobile.scaffolding.data.database.entity.Route
import ar.edu.unlam.mobile.scaffolding.data.database.entity.User

@Database(
    entities = [Route::class, Coordinate::class, User::class],
    version = 2,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getRouteDao(): RouteDao

    abstract fun getCoordinateDao(): CoordinateDao

    abstract fun getUserDao(): UserDao
}
