package ar.edu.unlam.mobile.scaffolding.data.repository

import ar.edu.unlam.mobile.scaffolding.data.database.entity.Challenge
import kotlinx.coroutines.flow.Flow

interface ChallengeRepository {
    fun insertChallenge(challenge: Challenge)

    fun deleteChallenge(challenge: Challenge)

    fun updateChallenge(challenge: Challenge)

    fun getChallenge(challengeId: Long): Flow<Challenge>

    fun getAllChallenges(): Flow<List<Challenge>>
}
