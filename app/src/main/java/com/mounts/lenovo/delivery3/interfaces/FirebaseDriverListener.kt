package com.mounts.lenovo.delivery3.interfaces

import com.mounts.lenovo.delivery3.model.Driver

interface FirebaseDriverListener {

    fun onDriverAdded(driver: Driver)

    fun onDriverRemoved(driver: Driver)

    fun onDriverUpdated(driver: Driver)


}