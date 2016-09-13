package terminales;

import java.sql.Date;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.joda.time.LocalDate;

@Converter(autoApply = true)
public class ConversorFechas implements AttributeConverter<LocalDate, Date> {
	@Override
	public Date convertToDatabaseColumn(LocalDate entityValue) {
		return (new java.sql.Date(entityValue.toDate().getTime()));
	}

	@Override
	public LocalDate convertToEntityAttribute(Date databaseValue) {
		return (new LocalDate(databaseValue));
	}
}
