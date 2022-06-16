package com.softech.ehr.helpers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateSerializer extends StdSerializer<LocalDate> {

    private final DateTimeFormatter formatter2 =
        DateTimeFormatter.ofPattern("MMM-d-yyyy");

    public LocalDateSerializer() {
        this(null);
    }

    public LocalDateSerializer(Class<LocalDate> t) {
        super(t);
    }

    @Override
    public void serialize(
        LocalDate value,
        JsonGenerator gen,
        SerializerProvider arg2)
        throws IOException {
        gen.writeString(value.format(formatter2));
    }
}
