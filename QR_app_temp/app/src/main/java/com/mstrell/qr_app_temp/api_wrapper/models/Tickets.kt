package com.mstrell.qr_app_temp.api_wrapper.models

class Tickets(
    val listTickets: List<Ticket>
)

class Ticket(
    val id: Int,
    val title: String,
    val condition: Condition,
    val customer: Customer
)

class Condition(
    val from: String?,
    val to: String?
)

class Customer(
    val firstName: String,
    val lastName: String
)

class RepayStatus(
    val success: Boolean
)
