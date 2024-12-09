package com.example.wallpaperapp.registerscreen.model

interface RegisterScreenRepository {
    fun setData(email: String, password: String, firstName: String, lastName: String, phoneNumber: String, dateOfBirth: String, error: String?)
    fun getData(): RegisterScreenModel
}

class RegisterScreenRepositoryImpl() : RegisterScreenRepository{
    private var _data = RegisterScreenModel(email = "", password = "", firstName = "", lastName = "", phoneNumber = "", dateOfBirth = "", error = null)
    override fun setData(email: String, password: String, firstName: String, lastName: String, phoneNumber: String, dateOfBirth: String, error: String?) {
        _data = RegisterScreenModel(email, password, firstName, lastName, phoneNumber, dateOfBirth, error)
    }
    override fun getData(): RegisterScreenModel {
        return _data
    }
}