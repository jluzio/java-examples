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

import io.github.threetenjaxb.core.DurationXmlAdapter;
import io.github.threetenjaxb.core.InstantXmlAdapter;
import io.github.threetenjaxb.core.LocalDateTimeXmlAdapter;
import io.github.threetenjaxb.core.LocalDateXmlAdapter;
import io.github.threetenjaxb.core.LocalTimeXmlAdapter;
import io.github.threetenjaxb.core.MonthDayXmlAdapter;
import io.github.threetenjaxb.core.OffsetTimeXmlAdapter;
import io.github.threetenjaxb.core.PeriodXmlAdapter;
import io.github.threetenjaxb.core.YearMonthXmlAdapter;
import io.github.threetenjaxb.core.YearXmlAdapter;
import io.github.threetenjaxb.core.ZoneIdXmlAdapter;
import io.github.threetenjaxb.core.ZonedDateTimeXmlAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
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