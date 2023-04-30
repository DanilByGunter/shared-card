package com.project.shared_card.activity.converter;

import android.os.Build;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;

public class DateConverter {
    public static long FromNowDateToLong(){
        ZonedDateTime zdt = ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault());
        return zdt.toInstant().toEpochMilli();
    }
    public static LocalDateTime FromLongDateToLocalDateTime(long date){
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(date),
                        TimeZone.getDefault().toZoneId());
    }
}
