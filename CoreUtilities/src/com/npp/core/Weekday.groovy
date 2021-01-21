package com.pabslabs.core

enum Weekday {
    SUNDAY (Calendar.SUNDAY), MONDAY(Calendar.MONDAY), TUESDAY(Calendar.TUESDAY), WEDNESDAY(Calendar.WEDNESDAY), THURSDAY(Calendar.THURSDAY), FRIDAY(Calendar.FRIDAY), SATURDAY(Calendar.SATURDAY)

    int calendarConstantValue

    Weekday(int calendarConstant) {
        calendarConstantValue = calendarConstant
    }

    //Return calendar day of the week constant
    int getCalendarDay() {
        return calendarConstantValue
    }

    Weekday getDay(String day) {
        try {
            return valueOf(day)
        } catch(IllegalArgumentException ex) {
            return null    //if the string doesn't match the enum value, return null
        }
    }
}