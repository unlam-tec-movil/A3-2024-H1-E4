package ar.edu.unlam.mobile.scaffolding.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ar.edu.unlam.mobile.scaffolding.data.database.entity.Challenge
import kotlinx.coroutines.flow.Flow

@Dao
interface ChallengeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(challenge: Challenge)

    @Delete
    fun delete(challenge: Challenge)

    @Update
    fun update(challenge: Challenge)

    @Query("SELECT * FROM challenge where id=:challengeId")
    fun getChallenge(challengeId: Long): Flow<Challenge>

    @Query("SELECT * FROM challenge")
    fun getAllChallenges(): Flow<List<Challenge>>
}
