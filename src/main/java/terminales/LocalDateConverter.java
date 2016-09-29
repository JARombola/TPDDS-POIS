package terminales;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.mongodb.morphia.converters.SimpleValueConverter;
import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;

public class LocalDateConverter extends TypeConverter implements SimpleValueConverter {

    public LocalDateConverter() {
        super(LocalDate.class);
    }

    
    @Override
    public Object decode(Class<?> targetClass, Object fromDBObject, MappedField optionalExtraInfo) {
    	DateTime dt = new DateTime((Date)fromDBObject);
    	return dt.toLocalDate();
    }

    @Override
    public Object encode(Object value, MappedField optionalExtraInfo) {
        Date date = ((LocalDate)value).toDateTimeAtStartOfDay().toDate();
        return date;

    }
}