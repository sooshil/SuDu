package com.sukajee.sudu.data.util

sealed class SuduOrder(val orderType: OrderType) {
    class CreatedDate(orderType: OrderType): SuduOrder(orderType)
    class Color(orderType: OrderType): SuduOrder(orderType)
    class Title(orderType: OrderType): SuduOrder(orderType)
}
