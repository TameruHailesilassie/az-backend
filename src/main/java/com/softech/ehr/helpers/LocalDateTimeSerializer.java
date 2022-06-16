package com.softech.ehr.helpers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class LocalDateTimeSerializer extends StdSerializer<LocalDateTime> {

    private final
    DateTimeFormatter formatter =
        DateTimeFormatter.ofPattern("MMM-d-yyyy hh:mm:ss a");

    public LocalDateTimeSerializer() {
        this(null);
    }

    public LocalDateTimeSerializer(Class<LocalDateTime> t) {
        super(t);
    }

    @Override
    public void serialize(LocalDateTime localDateTime,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider)
        throws IOException {
        jsonGenerator.writeString(localDateTime.format(formatter));

    }
}
