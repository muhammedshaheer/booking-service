package com.github.muhammedshaheer.bookingservice.entity.converter;

import com.github.muhammedshaheer.bookingservice.entity.enums.BookingStatus;

import javax.persistence.AttributeConverter;

/**
 * @author Muhammed Shaheer
 */

public class BookingStatusConverter implements AttributeConverter<BookingStatus, String> {

    /**
     * Converts the value stored in the entity attribute into the
     * data representation to be stored in the database.
     *
     * @param attribute the entity attribute value to be converted
     * @return the converted data to be stored in the database
     * column
     */
    @Override
    public String convertToDatabaseColumn(BookingStatus attribute) {
        return attribute.getCode();
    }

    /**
     * Converts the data stored in the database column into the
     * value to be stored in the entity attribute.
     * Note that it is the responsibility of the converter writer to
     * specify the correct <code>dbData</code> type for the corresponding
     * column for use by the JDBC driver: i.e., persistence providers are
     * not expected to do such type conversion.
     *
     * @param dbData the data from the database column to be
     *               converted
     * @return the converted value to be stored in the entity
     * attribute
     */
    @Override
    public BookingStatus convertToEntityAttribute(String dbData) {
        return BookingStatus.fromCode(dbData);
    }
}
