package com.mennad.api.association.repositories

import com.mennad.api.association.domain.model.Association
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface AssociationRepository: MongoRepository<Association, String>