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
package com.example.java.playground.xml.model;

import com.migesok.jaxb.adapter.javatime.*;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.time.*;