package com.ecovacs.kotlinmvpdemo.mvp.model.bean

data class CategoryBean(val bgColor: String = "",
                        val defaultAuthorId: String? = null,
                        val headerImage: String = "",
                        val name: String = "",
                        val alias: String? = null,
                        val description: String = "",
                        val id: Long = 0,
                        val bgPicture: String = "")