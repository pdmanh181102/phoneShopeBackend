package tttn_2025.phoneShop.common.services.classes;

import java.lang.reflect.Field;
import java.util.Arrays;

import org.springframework.stereotype.Service;

@Service
public class ClassUtilService {

    public boolean isValidField(String fieldName, Class<?> clazz) {
        if (fieldName == null || clazz == null)
            return false;

        return Arrays.stream(clazz.getDeclaredFields())
                .anyMatch(field -> field.getName().equals(fieldName));
    }

    public String getFirstFieldName(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        if (fields.length == 0) {
            throw new IllegalStateException(clazz.getName() + " has no declared fields.");
        }
        return fields[0].getName();
    }

}
