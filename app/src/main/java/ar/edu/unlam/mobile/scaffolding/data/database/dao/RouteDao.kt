package ar.edu.unlam.mobile.scaffolding.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ar.edu.unlam.mobile.scaffolding.data.database.entity.Route
import kotlinx.coroutines.flow.Flow

@Dao
interface RouteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(route: Route)

    @Delete
    fun delete(route: Route)

    @Update
    fun update(route: Route)

    @Query("SELECT * FROM route where id=:routeId")
    fun getRoute(routeId: Long): Flow<Route>

    @Query("SELECT * FROM route")
    fun getAllRoutes(): Flow<List<Route>>
}
