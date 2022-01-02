package pl.kaczmarek.utils;

import org.dozer.Mapping;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class TypeConversionUtils {

    private static <T> void getFields(Class t, ReflectionUtils.FieldCallback fc) {
        List<Field> fields = new ArrayList<>();
        Class clazz = t;
        while (clazz != Object.class) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }

        fields.forEach(v -> {
            try {
                fc.doWith(v);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    // search in superclass also
    public static Field getField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        Field field = null;
        while(field == null){
            try {
                field = clazz.getDeclaredField(fieldName);
            }
            catch(NoSuchFieldException e){
                Class<?> sup = clazz.getSuperclass();
                if(sup == null)
                    throw e;
                clazz = sup;
            }
        }
        return field;
    }

    public static <T> T mapReflective(T in, Map<String, Object> dataSource, Class inVO) {

        getFields(inVO, field -> {

            Mapping obj = field.getAnnotation(Mapping.class);

            String sourceName  = (obj != null ? obj.value() : field.getName());
            String targetName = field.getName();

            if (dataSource.containsKey(targetName)) {

                /* ModifiedBy & CreatedBy stock class rewrite mapping handling */
                switch(sourceName)
                {
                    case "modifyBy": sourceName = "user1"; break;
                    case "createBy": sourceName = "user2"; break;
                }

                /* Declare field */
                Field sourceField = ReflectionUtils.findField(in.getClass(), sourceName);

                /* Null protect */
                if (sourceField == null)
                {
                    return;
                }

                /* Accessibility flag */
                sourceField.setAccessible(true);

                /* Store field type */
                Type fieldType = sourceField.getType();

                /* Null value case */
                if ( dataSource.get(targetName) == null ) {
                    /* In case of null, assign and jump out */
                    ReflectionUtils.setField(sourceField, in, null);
                    return;
                }


                if (fieldType.equals(Timestamp.class)) {
                    String candidateTime = ((String) dataSource.get(targetName)).replace("Z", "");

                    ReflectionUtils.setField(sourceField, in, toTimestamp(candidateTime));
                } else
                if(fieldType.equals(Double.class)) {
                    Double dblVal = Double.parseDouble(String.valueOf(dataSource.get(targetName)));
                    ReflectionUtils.setField(sourceField, in, dblVal);
                }
                else {
                    /* Generic setting of field value */
                    ReflectionUtils.setField(sourceField, in, dataSource.get(targetName));
                }

            }
        });

        return in;
    }

    public static Timestamp toTimestamp(String in)
    {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            Date parsed = dateFormat.parse( in );

            return new Timestamp(parsed.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String timestampToString(Timestamp in)
    {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(tz);

        return df.format(in);
    }

    public static String getISODateWithoutTime(Date date) throws ParseException {
        if (date != null) {
            TimeZone tz = TimeZone.getTimeZone("UTC");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            df.setTimeZone(tz);
            return df.format(date);
        } else {
            return null;
        }
    }
    public static Date convertStringToDate(String date) throws ParseException {

        Date newDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        return newDate;


    }

    public static Date addHoursToJavaUtilDate(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }
    public static java.sql.Date utilDateToSqlDate(Date date) {
        Date utilDate = date;
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        return sqlDate;
    }
}
