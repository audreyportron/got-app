package com.apo.mobgengot.domain

import io.reactivex.Single

class CategoriesService(private val categoriesRepository: CategoriesRepository) {
    fun getCategoriesByName(forceLoadApi: Boolean): Single<List<Category>> {
        return categoriesRepository.getCategories(forceLoadApi).map {
            it.filter { category -> category.type != CategoryType.UNKNOWN }
                .sortedBy { category -> category.title }
        }
    }
}