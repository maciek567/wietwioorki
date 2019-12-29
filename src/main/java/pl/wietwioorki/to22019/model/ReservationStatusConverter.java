package pl.wietwioorki.to22019.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ReservationStatusConverter implements AttributeConverter<ReservationStatus, String> {
    @Override
    public String convertToDatabaseColumn(ReservationStatus value) {
        if (value == null) {
            return null;
        }
        return value.getDescription();
    }

    @Override
    public ReservationStatus convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }
        return ReservationStatus.of(value);
    }

}
