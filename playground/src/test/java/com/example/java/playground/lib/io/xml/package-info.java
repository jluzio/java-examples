@XmlJavaTypeAdapters({
    @XmlJavaTypeAdapter(type = Duration.class, value = DurationXmlAdapter.class),
    @XmlJavaTypeAdapter(type = Instant.class, value = InstantXmlAdapter.class),
    @XmlJavaTypeAdapter(type = LocalDateTime.class, value = LocalDateTimeXmlAdapter.class),
    @XmlJavaTypeAdapter(type = LocalDate.class, value = LocalDateXmlAdapter.class),
    @XmlJavaTypeAdapter(type = LocalTime.class, value = LocalTimeXmlAdapter.class),
    @XmlJavaTypeAdapter(type = MonthDay.class, value = MonthDayXmlAdapter.class),
    @XmlJavaTypeAdapter(type = OffsetTime.class, value = OffsetTimeXmlAdapter.class),
    @XmlJavaTypeAdapter(type = Period.class, value = PeriodXmlAdapter.class),
    @XmlJavaTypeAdapter(type = YearMonth.class, value = YearMonthXmlAdapter.class),
    @XmlJavaTypeAdapter(type = Year.class, value = YearXmlAdapter.class),
    @XmlJavaTypeAdapter(type = ZonedDateTime.class, value = ZonedDateTimeXmlAdapter.class),
    @XmlJavaTypeAdapter(type = ZoneId.class, value = ZoneIdXmlAdapter.class),
})
package com.example.java.playground.lib.io.xml;

import com.migesok.jaxb.adapter.javatime.DurationXmlAdapter;
import com.migesok.jaxb.adapter.javatime.InstantXmlAdapter;
import com.migesok.jaxb.adapter.javatime.LocalDateTimeXmlAdapter;
import com.migesok.jaxb.adapter.javatime.LocalDateXmlAdapter;
import com.migesok.jaxb.adapter.javatime.LocalTimeXmlAdapter;
import com.migesok.jaxb.adapter.javatime.MonthDayXmlAdapter;
import com.migesok.jaxb.adapter.javatime.OffsetTimeXmlAdapter;
import com.migesok.jaxb.adapter.javatime.PeriodXmlAdapter;
import com.migesok.jaxb.adapter.javatime.YearMonthXmlAdapter;
import com.migesok.jaxb.adapter.javatime.YearXmlAdapter;
import com.migesok.jaxb.adapter.javatime.ZoneIdXmlAdapter;
import com.migesok.jaxb.adapter.javatime.ZonedDateTimeXmlAdapter;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.OffsetTime;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;