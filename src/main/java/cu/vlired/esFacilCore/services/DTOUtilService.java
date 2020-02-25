package cu.vlired.esFacilCore.services;

import com.sun.istack.NotNull;
import cu.vlired.esFacilCore.model.BaseEntity;
import cu.vlired.esFacilCore.model.dto.BaseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.UUID;

@Service
public class DTOUtilService {

    final
    EntityManager entityManager;

    public DTOUtilService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public <T extends BaseEntity> T convertToEntity(Object source, Class<T> target) {

        Object entityId = getEntityId(source);
        T res;

        if (entityId == null) {
            res = map(source, target);
            res.setId(UUID.randomUUID());
        } else {
            res = entityManager.find(target, entityId);
        }

        return res;
    }

    public <T extends BaseDTO> T convertToDTO(Object source, Class<T> target) {
        return map(source, target);
    }

    private <T> T map(Object source, Class<T> target) {
        var modelMapper = new ModelMapper();
        return modelMapper.map(source, target);
    }

    private Object getEntityId(@NotNull Object dto) {
        for (Field field : dto.getClass().getDeclaredFields()) {
            if (field.getAnnotation(Id.class) != null) {
                try {
                    field.setAccessible(true);
                    return field.get(dto);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }
}