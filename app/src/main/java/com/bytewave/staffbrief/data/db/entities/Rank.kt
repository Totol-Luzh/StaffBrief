package com.bytewave.staffbrief.data.db.entities

enum class Rank(val level: Int, val russianName: String) {
    SOLDIER(1, "Рядовой"),
    CORPORAL(2, "Ефрейтор"),
    JUNIOR_SERGEANT(3, "Младший сержант"),
    SERGEANT(4, "Сержант"),
    SENIOR_SERGEANT(5, "Старший сержант"),
    MASTER_SERGEANT(6, "Старшина"),
    ENSIGN(7, "Прапорщик"),
    SENIOR_ENSIGN(8, "Старший прапорщик"),
    JUNIOR_LIEUTENANT(9, "Младший лейтенант"),
    LIEUTENANT(10, "Лейтенант"),
    SENIOR_LIEUTENANT(11, "Старший лейтенант"),
    CAPTAIN(12, "Капитан"),
    MAJOR(13, "Майор")
}