package ar.edu.unlam.mobile.scaffolding.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ar.edu.unlam.mobile.scaffolding.data.database.entity.Challenge

@Dao
interface ChallengeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(challenge: Challenge)

    @Delete
    fun delete(challenge: Challenge)

    @Update
    fun update(challenge: Challenge)

    @Query("SELECT * FROM challenge where id=:challengeId")
    fun getRoute(challengeId: Long): Challenge

    @Query("SELECT * FROM route")
    fun getAllRoutes(): List<Challenge>
}