package com.mounts.lenovo.delivery3.model

data class MyoTwin_Order(var user_City:String,
                         var order_Name:String,
                         var weight:Int,
                         var receiver_Name:String,
                         var receiver_Phone:String,
                         var receiver_Address:String,
                         var receiver_City:String,
                         var pre_Paid:Boolean,
                         var post_Paid:Boolean,
                         var session_Id:String,
                         var same_city:Int)