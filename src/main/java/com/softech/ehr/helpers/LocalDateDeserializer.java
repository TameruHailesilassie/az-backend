package com.softech.ehr.helpers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;


public class LocalDateDeserializer extends StdDeserializer<LocalDate> {

    private final
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");

    public LocalDateDeserializer() {
        this(null);
    }

    public LocalDateDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public LocalDate deserialize(JsonParser jsonparser, DeserializationContext context)
        throws IOException, JsonProcessingException {
        String date = jsonparser.getText();
        if (date.isEmpty())
            return null;
        try {
            return LocalDate.parse(date, formatter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
