package pl.kaczmarek.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import lombok.SneakyThrows;
import pl.kaczmarek.model.AppException;
import pl.kaczmarek.parking_space_exchange.dto.HireRequest;
import pl.kaczmarek.parking_space_exchange.dto.LandLordRequest;
import pl.kaczmarek.parking_space_exchange.model.HirerEntity;
import pl.kaczmarek.parking_space_exchange.model.LandLordEntity;
import pl.kaczmarek.utils.TypeConversionUtils;

@Service
@SuppressWarnings("unchecked")
public abstract class EntityService <
        ENTITY extends IEntity,
        DAO extends EntityDAO<ENTITY>,
        IN_DTO extends Serializable,
        OUT_DTO extends EntityDTO<UUID>> {

    //<editor-fold desc="[INIT]">
    /* Generic repository */
    /* Error: Could not autowire. No Beans of 'DAO' type found. */
    protected DAO dao;

    /* Current Entity class */
    protected final Class<ENTITY> entityClass;

    /* Current DAO class */
    protected final Class<DAO> daoClass;

    /* Current Input (Request) DTO class */
    protected final Class<IN_DTO> inDtoClass;

    /* Current Output (Response) DTO class */
    protected final Class<OUT_DTO> outDtoClass;

    /* Dozer bean wiring */
    @Autowired
    protected DozerBeanMapper dozerBeanMapper;

    @Autowired
    protected ObjectMapper objectMapper;

    /* Base constructor */
    public EntityService(DAO dao) {
        /* Wire generic classes */
        this.entityClass = (Class<ENTITY>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.daoClass = (Class<DAO>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        this.inDtoClass = (Class<IN_DTO>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[2];
        this.outDtoClass = (Class<OUT_DTO>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[3];

        /* Wire DAO instance */
        this.dao = dao;
    }
    //</editor-fold>

    //<editor-fold desc="[CREATE]">
    /**
     * Method executed just before saving of passed entity that allows to create dependencies
     * @param recruit Entity being saved to the database
     * @param requestBody Request body as a base for creation of entity
     */
    @Transactional
    protected void preCreate(ENTITY recruit, IN_DTO requestBody) {

    }

    /**
     * Method executed right after saving of passed entity that allows to create further dependencies based on object created
     * @param recruit Entity being saved to the database
     * @param requestBody Request body as a base for creation of entity
     */
    @Transactional
    protected void postCreate(ENTITY recruit, IN_DTO requestBody) {

    }

    /**
     * Main entity creation method
     * @param requestBody Request body as a base for creation of entity
     * @return ENTITY Created entity reference
     */
    @Modifying
    @Transactional
    public ENTITY create(IN_DTO requestBody) {

        /* Reverse mapping */
        ENTITY recruit = dozerBeanMapper.map(requestBody, entityClass);

        /* Null checking safeguard */
        if (recruit == null) {
            /* TODO: Error handling */
            return null;
        }

        /* PreCreate */
        preCreate(recruit, requestBody);

        /* Create */
        dao.saveAndFlush(recruit);

        /* PostCreate */
        postCreate(recruit, requestBody);

        /* Return entity */
        return recruit;
    }

    /**
     * Mass entity creation method
     * @param requestBody List of dtos serving as request body and as a base for creation of entities
     * @return List<ENTITY> Created entities list reference
     */
    @Transactional
    public List<ENTITY> createMany(List<IN_DTO> requestBody) {
        return requestBody.stream().map( this::create ).collect(Collectors.toList());
    }
    //</editor-fold>
    //<editor-fold desc="[READ]">
    /**
     * Find ENTITY based on ENTITY
     * @param entity ENTITY to look for
     * @return ENTITY | NULL Entity in case of object being found, null otherwise
     */
    @SneakyThrows
    @Transactional
    public ENTITY find(ENTITY entity) {
        ENTITY selector = dao.getById(entity.getId());

        if (selector == null)
        {
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO returned null as result of a find method");
        }

        return selector;
    }
    /**
     * Find ENTITY based on SID
     * @param id SID to look for
     * @return ENTITY | NULL Entity in case of object being found, null otherwise
     */
    @SneakyThrows
    @Transactional
    public ENTITY find(UUID id) {
        ENTITY selector = dao.getById(id);

        if (selector == null)
        {
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO returned null as result of a find method");
        }

        return selector;
    }

    /**
     * Find all ENTITIES persisted in database
     * @return List<ENTITY> List of entities
     */
    @Transactional
    public List<ENTITY> findAll() {
        return dao.findAll();
    }
    //</editor-fold>
    //<editor-fold desc="[UPDATE]">
    /**
     * Method executed right before update of passed entity that allows to create or remove dependencies
     * @param recruit Entity being updated on the database
     * @param patch Request body as a base for update of entity
     */
    @Transactional
    protected void preUpdate(ENTITY recruit, JsonPatch patch) {

    }

    /**
     * Method executed right after update of passed entity that allows to create further dependencies based on object updated
     * @param recruit Entity being updated on the database
     * @param patch Request body as a base for update of entity
     */
    @Transactional
    protected void postUpdate(ENTITY recruit, JsonPatch patch) {

    }

    /**
     * Method updates ENTITY using PATCH method patching
     * @param id ID of entity being updated
     * @param patch Request body serving as a base for update
     * @return ENTITY | NULL Entity in case of successful update, null in case of failure
     */
    @SneakyThrows
    @Transactional
    public ENTITY update(UUID id, JsonPatch patch) {
        // Find recruit corresponding to specified ID
        ENTITY recruit = find(id);

        /* Return entity */
        return update(recruit, patch);
    }

    /**
     * Method updates ENTITY using PATCH method patching
     * @param recruit Entity being updated
     * @param patch Request body serving as a base for update
     * @return ENTITY | NULL Entity in case of successful update, null in case of failure
     */
    @SneakyThrows
    @Transactional
    public ENTITY update(ENTITY recruit, JsonPatch patch) {
        /* Null checking safeguard */
        if (recruit == null) {
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Entity evaluated to null, could not update.");
        }

        /* PreUpdate */
        preUpdate(recruit, patch);

        /* Update */
        JsonNode patchedNode = patch.apply(objectMapper.convertValue(recruit, JsonNode.class));
        recruit = objectMapper.treeToValue(patchedNode, entityClass);

        /* Save the update */
        dao.saveAndFlush(recruit);

        /* PostCreate */
        postUpdate(recruit, patch);

        /* Return entity */
        return recruit;
    }
    //</editor-fold>
    //<editor-fold desc="[UPDATE-PRIMITIVE]">
    /**
     * Method executed right before update of passed entity that allows to create or remove dependencies
     * @param recruit Entity being updated on the database
     * @param requestBody Request body as a base for update of entity
     */
    @Transactional
    protected void prePrimitiveUpdate(ENTITY recruit, Map<String, Object> requestBody) {

    }

    /**
     * Method executed right after update of passed entity that allows to create further dependencies based on object updated
     * @param recruit Entity being updated on the database
     * @param requestBody Request body as a base for update of entity
     */
    @Transactional
    protected void postPrimitiveUpdate(ENTITY recruit, Map<String, Object> requestBody) {

    }

    /**
     * Method updates ENTITY using PATCH method patching
     * @param id Id of entity being updated
     * @param requestBody Request body serving as a base for update
     * @return ENTITY | NULL Entity in case of successful update, null in case of failure
     */
    @Transactional
    public ENTITY updatePrimitive(UUID id, Map<String, Object> requestBody) {
        /* Find entity in the database */
        /* Return entity */
        return updatePrimitive(find(id), requestBody);
    }

    /**
     * Method updates ENTITY using PATCH method patching
     * @param recruit entity being updated
     * @param requestBody Request body serving as a base for update
     * @return ENTITY | NULL Entity in case of successful update, null in case of failure
     */
    @SneakyThrows
    @Transactional
    public ENTITY updatePrimitive(ENTITY recruit, Map<String, Object> requestBody) {

        /* Null checking safeguard */
        if (recruit == null) {
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Update primitive attempted on NULL object being passed");
        }

        /* PreUpdate */
        prePrimitiveUpdate(recruit, requestBody);

        /* Create */
        dao.saveAndFlush(TypeConversionUtils.mapReflective(recruit, requestBody, inDtoClass));

        /* PostCreate */
        postPrimitiveUpdate(recruit, requestBody);

        /* Return entity */
        return recruit;
    }
    //</editor-fold>
    //<editor-fold desc="[INSERT]">
    /**
     * Method executed right before update of passed entity that allows to create or remove dependencies
     * @param recruit Entity being replaced on the database
     * @param requestBody Request body as a base for replacement of entity
     */
    @Transactional
    protected void preInsert(ENTITY recruit, IN_DTO requestBody) {

    }

    /**
     * Method executed right after update of passed entity that allows to create further dependencies based on object updated
     * @param recruit Entity being replaced on the database
     * @param requestBody Request body as a base for replacement of entity
     */
    @Transactional
    protected void postInsert(ENTITY recruit, IN_DTO requestBody) {

    }

    /**
     * Method replaces ENTITY using PUT method patching
     * @param id ID of entity being replaced
     * @param requestBody Request body serving as a base for replacement
     * @return ENTITY | NULL Entity in case of successful replacement, null in case of failure
     */
    @SneakyThrows
    @Transactional
    public ENTITY insert(UUID id, IN_DTO requestBody) {
        // Find recruit corresponding to specified ID
        ENTITY recruit = find(id);

        /* Return entity */
        return insert(recruit, requestBody);
    }

    /**
     * Method replaces ENTITY using PUT method patching
     * @param recruit Entity being replaced
     * @param requestBody Request body serving as a base for replacement
     * @return ENTITY | NULL Entity in case of successful replacement, null in case of failure
     */
    @SneakyThrows
    @Transactional
    public ENTITY insert(ENTITY recruit, IN_DTO requestBody) {
        /* Null checking safeguard */
        if (recruit == null) {
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Entity evaluated to null, could not replace.");
        }

        /* PreUpdate */
        preInsert(recruit, requestBody);

        /* Update */
        ENTITY candidate = dozerBeanMapper.map(requestBody, entityClass);
        candidate.setId(recruit.getId());
        candidate.setCreationDate(recruit.getCreationDate());
        candidate.setModificationDate(Timestamp.from(Instant.now()));

        /* Save the update */
        dao.saveAndFlush(candidate);

        /* PostCreate */
        postInsert(candidate, requestBody);

        /* Return entity */
        return candidate;
    }
    //</editor-fold>

    //<editor-fold desc="[DELETE]">
    /**
     * Method executed just before deletion of passed entity that allows to delete/unlink it's dependencies
     * @param entity Entity to be deleted from the database
     */
    @Transactional
    protected void preDelete(ENTITY entity) { }

    /**
     * Method executed after deletion of an entity
     */
    @Transactional
    protected void postDelete() { }

    /**
     * Method deletes provided entity
     * @param entity Entity to be deleted
     */
    @SneakyThrows
    @Transactional
    public void delete(ENTITY entity) {
        /* Null-check safeguard */
        if(entity == null)
        {
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Entity evaluated to null, could not delete.");
        }

        /* Remove entity */
        try {
            /* PreDelete hook */
            preDelete(entity);

            /* Deletion attempt */
            dao.delete(entity);

            /* PostDelete hook */
            postDelete();
        }
        catch (DataIntegrityViolationException e) {
            throw e;
        }
    }

    /**
     * Method deleted entity based on SID
     * @param id SID of entity to be deleted
     */
    @Transactional
    public void delete(UUID id) {
        /* Find entity */
        ENTITY entity = find(id);

        /* Delete entity */
        delete(entity);
    }

    /**
     * Method deletes list of entities
     * @param entities List of ENTITY to be deleted
     */
    @Transactional
    public void deleteMany(List<ENTITY> entities) {
        entities.forEach(this::delete);
    }
    //</editor-fold>
    //<editor-fold desc="[UTILS]">
    /* Handy conversion methods from entity to vo */
    /**
     * Method converts entity to base DTO class
     * @param entity Entity being converted
     * @return DTO Converted DTO object
     */
    public IN_DTO toInDTO(ENTITY entity) {

        /* TODO: Complex null error handling? */
        if(entity == null) {
            return null;
        }

        /* return beanmap! */
        return dozerBeanMapper.map(entity, inDtoClass);
    }


    public LandLordEntity convertToEntity(LandLordRequest postDto) throws ParseException {
        LandLordEntity landLordEntity = dozerBeanMapper.map(postDto, LandLordEntity.class);
        return landLordEntity;
    }

    public HirerEntity convertToEntity(HireRequest postDto) throws ParseException {
        HirerEntity hirerEntity = dozerBeanMapper.map(postDto, HirerEntity.class);
        return hirerEntity;
    }

    /**
     * Method converts list of entities to a list of base DTO class
     * @param entity List of entities for conversion
     * @return List<DTO> Converted DTO objects
     */
    public List<IN_DTO> toInDTO(List<ENTITY> entity) {

        /* TODO: Complex null error handling? */
        if(entity == null) {
            return null;
        }

        /* return beanmap! */
        return entity
                .stream()
                .map(obj -> dozerBeanMapper.map(obj, inDtoClass))
                .collect(Collectors.toList());
    }

    /**
     * Method converts entity to specified DTO class
     * @param entity Entity being converted
     * @return VO Converted DTO object
     */
    public IN_DTO toInDTO(ENTITY entity, Class<IN_DTO> voClass) {

        /* TODO: Complex null error handling? */
        if(entity == null) {
            return null;
        }

        /* return beanmap! */
        return dozerBeanMapper.map(entity, voClass);
    }

    /**
     * Method converts list of entities to a list of specified DTO class
     * @param entity List of entities for conversion
     * @return List<DTO> Converted DTO objects
     */
    public List<IN_DTO> toInDTO(List<ENTITY> entity, Class<IN_DTO> voClass) {

        /* TODO: Complex null error handling? */
        if(entity == null) {
            return null;
        }

        /* return beanmap! */
        return entity
                .stream()
                .map(obj -> dozerBeanMapper.map(obj, voClass))
                .collect(Collectors.toList());
    }

    /**
     * Method converts entity to base DTO class
     * @param entity Entity being converted
     * @return DTO Converted DTO object
     */
    public OUT_DTO toOutDTO(ENTITY entity) {

        /* TODO: Complex null error handling? */
        if(entity == null) {
            return null;
        }

        /* return beanmap! */
        return dozerBeanMapper.map(entity, outDtoClass);
    }

    /**
     * Method converts list of entities to a list of base DTO class
     * @param entity List of entities for conversion
     * @return List<DTO> Converted DTO objects
     */
    public List<OUT_DTO> toOutDTO(List<ENTITY> entity) {

        /* TODO: Complex null error handling? */
        if(entity == null) {
            return null;
        }

        /* return beanmap! */
        return entity
                .stream()
                .map(obj -> dozerBeanMapper.map(obj, outDtoClass))
                .collect(Collectors.toList());
    }

    /**
     * Method converts entity to specified DTO class
     * @param entity Entity being converted
     * @return VO Converted DTO object
     */
    public OUT_DTO toOutDTO(ENTITY entity, Class<OUT_DTO> voClass) {

        /* TODO: Complex null error handling? */
        if(entity == null) {
            return null;
        }

        /* return beanmap! */
        return dozerBeanMapper.map(entity, voClass);
    }

    /**
     * Method converts list of entities to a list of specified DTO class
     * @param entity List of entities for conversion
     * @return List<DTO> Converted DTO objects
     */
    public List<IN_DTO> toOutDTO(List<ENTITY> entity, Class<IN_DTO> voClass) {

        /* TODO: Complex null error handling? */
        if(entity == null) {
            return null;
        }

        /* return beanmap! */
        return entity
                .stream()
                .map(obj -> dozerBeanMapper.map(obj, voClass))
                .collect(Collectors.toList());
    }
    //</editor-fold>
}

