package ar.edu.unlam.mobile.scaffolding.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ar.edu.unlam.mobile.scaffolding.data.database.dao.ChallengeDao
import ar.edu.unlam.mobile.scaffolding.data.database.dao.CoordinateDao
import ar.edu.unlam.mobile.scaffolding.data.database.dao.RouteDao
import ar.edu.unlam.mobile.scaffolding.data.database.entity.Challenge
import ar.edu.unlam.mobile.scaffolding.data.database.entity.Coordinate
import ar.edu.unlam.mobile.scaffolding.data.database.entity.Route

@Database(
    entities = [Route::class, Coordinate::class, Challenge::class],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getRouteDao(): RouteDao
    abstract fun getCoordinateDao(): CoordinateDao
    abstract fun getChallengeDao(): ChallengeDao
}