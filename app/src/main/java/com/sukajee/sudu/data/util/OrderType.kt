package com.sukajee.sudu.data.util

sealed class OrderType {
    object Ascending : OrderType()
    object Descending : OrderType()
}
