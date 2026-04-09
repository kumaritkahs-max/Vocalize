package com.yourapp.vocalize.data.repository

import com.yourapp.vocalize.data.db.CategoryDao
import com.yourapp.vocalize.data.model.Category
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao
) {
    suspend fun insertCategory(category: Category) {
        categoryDao.insertCategory(category)
    }

    suspend fun getCategories(): List<Category> {
        return categoryDao.getCategories()
    }
}