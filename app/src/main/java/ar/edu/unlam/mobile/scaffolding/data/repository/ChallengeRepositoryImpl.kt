package ar.edu.unlam.mobile.scaffolding.data.repository

import ar.edu.unlam.mobile.scaffolding.data.database.dao.ChallengeDao
import ar.edu.unlam.mobile.scaffolding.data.database.entity.Challenge
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//TODO: hacer mapeos de entidad a dominio y viceversa según el método
class ChallengeRepositoryImpl @Inject constructor(private val challengeDao: ChallengeDao) :
    ChallengeRepository {

    override fun insertChallenge(challenge: Challenge) {
        challengeDao.insert(challenge)
    }

    override fun deleteChallenge(challenge: Challenge) {
        challengeDao.delete(challenge)
    }

    override fun updateChallenge(challenge: Challenge) {
        challengeDao.update(challenge)
    }

    override fun getChallenge(challengeId: Long): Flow<Challenge> {
        return challengeDao.getChallenge(challengeId)
    }

    override fun getAllChallenges(): Flow<List<Challenge>> {
        return challengeDao.getAllChallenges()
    }
}