package ar.edu.unlam.mobile.scaffolding.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ar.edu.unlam.mobile.scaffolding.data.database.entity.Coordinate

@Dao
interface CoordinateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(coordinate: Coordinate)

    @Delete
    fun delete(coordinate: Coordinate)

    @Update
    fun update(coordinate: Coordinate)

    @Query("SELECT * FROM coordinate where id=:coordId")
    fun getCoordinate(coordId: Long): Coordinate

    @Query("SELECT * FROM coordinate")
    fun getAllCoordinates(): List<Coordinate>
}