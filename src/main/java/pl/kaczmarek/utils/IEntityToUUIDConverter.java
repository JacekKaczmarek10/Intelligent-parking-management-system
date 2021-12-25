package pl.kaczmarek.utils;

import org.dozer.CustomConverter;
import org.dozer.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import lombok.SneakyThrows;
import pl.kaczmarek.rest.IEntity;


public class IEntityToUUIDConverter implements CustomConverter {

    private static final Logger logger = LoggerFactory.getLogger(IEntityToUUIDConverter.class);

    @SneakyThrows
    @Override
    public Object convert(Object destination, Object source, Class<?> destClass, Class<?> sourceClass) {

        if (source == null) {
            return null;
        }

        IEntity dest = null;
        if (source instanceof UUID) {
            if (destination == null) {
                try {
                    dest = (IEntity) Class.forName(destClass.getName()).newInstance();
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                    throw new MappingException("1 IEntityToStringConverter used incorrectly. Arguments passed in were:" + destination + " and " + source);
                }
            } else {
                dest = (IEntity) destination;
            }
            dest.setId((UUID) source);
            return dest;
        } else if (source instanceof IEntity) {
            return ((IEntity) source).getId();
        } else {
            throw new MappingException("2 IEntityToStringConverter used incorrectly. Arguments passed in were:" + destination + " and " + source);
        }
    }

}
