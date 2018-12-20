package com.aliumujib.tabbarseed.data.contracts

import com.aliumujib.tabbarseed.data.model.RepositoryEntity
import io.reactivex.Observable


interface IGithubRepository {
    fun fetchGithubRepositoriesMatchingFilters(hashMap: HashMap<String, Any>): Observable<List<RepositoryEntity>>
}
