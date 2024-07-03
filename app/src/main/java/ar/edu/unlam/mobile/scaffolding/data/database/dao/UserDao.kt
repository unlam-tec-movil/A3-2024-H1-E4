package ar.edu.unlam.mobile.scaffolding.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import ar.edu.unlam.mobile.scaffolding.data.database.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Delete
    fun delete(user: User)

    @Update
    fun update(user: User)

    @Upsert
    fun upsert(user: User)

    @Query("SELECT * FROM user where id=:userId")
    fun getUser(userId: Long): Flow<User>

    @Query("SELECT * FROM user")
    fun getAllUsers(): Flow<List<User>>
}
