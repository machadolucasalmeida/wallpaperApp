package com.example.wallpaperapp.loginscreen.model

interface LoginScreenRepository{
    fun getData(): LoginScreenModel
    fun setData(email: String, password: String, rememberPassword: Boolean)
}

class LoginScreenRepositoryImpl(): LoginScreenRepository{
    private var _modelData = LoginScreenModel("", "", false)
    override fun getData(): LoginScreenModel {
        return _modelData
    }
    override fun setData(email: String, password: String, rememberPassword: Boolean) {
        _modelData = LoginScreenModel(email, password, rememberPassword)
    }
}