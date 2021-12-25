package pl.kaczmarek.rest;

import com.github.fge.jsonpatch.JsonPatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;
import javax.validation.Valid;


/*
        ENTITY extends IEntity,
        DAO extends CommonRepository<ENTITY, SID>,
        DTO extends VO<SID>,
        SID extends Serializable
 */
@SuppressWarnings("unchecked")
public class EntityController <IN_DTO extends Serializable, OUT_DTO extends EntityDTO, SERVICE extends EntityService>
{
    private static final Logger logger = LoggerFactory.getLogger(EntityController.class);

    @Autowired
    protected SERVICE service;

    //<editor-fold desc="/ Root mappings"
    @GetMapping
    public ResponseEntity<List<OUT_DTO>> findAll() {
        return new ResponseEntity<>(
                service.toOutDTO( service.findAll() ),
                HttpStatus.OK
        );
    }

    @PostMapping
    @Transactional
    public ResponseEntity<OUT_DTO> create(@Valid @RequestBody IN_DTO requestBody) {
        return new ResponseEntity<>(
                (OUT_DTO) service.toOutDTO(service.create(requestBody)),
                HttpStatus.CREATED
        );
    }

    //</editor-fold>
    //<editor-fold desc="/{id} Mappings">
    @GetMapping("{id}")
    public ResponseEntity<OUT_DTO> read(@PathVariable UUID id) {
        return new ResponseEntity<>(
                (OUT_DTO) service.toOutDTO(service.find(id)),
                HttpStatus.OK
        );
    }

    @PatchMapping(path = "{id}", consumes = "application/json-patch+json")
    public ResponseEntity<OUT_DTO> update(@PathVariable UUID id, @RequestBody JsonPatch patchRequest) {
        return new ResponseEntity<>(
                (OUT_DTO) service.toOutDTO(service.update(id, patchRequest)),
                HttpStatus.ACCEPTED
        );
    }

    @PatchMapping("primitive/{id}")
    public ResponseEntity<OUT_DTO> update(@PathVariable UUID id, @Valid @RequestBody Map<String, Object> requestBody) {
        return new ResponseEntity<>(
                (OUT_DTO) service.toOutDTO(service.updatePrimitive(id, requestBody)),
                HttpStatus.ACCEPTED
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<OUT_DTO> insert(@PathVariable UUID id, @Valid @RequestBody IN_DTO requestBody) {
        return new ResponseEntity<>(
                (OUT_DTO) service.toOutDTO(service.insert(id, requestBody)),
                HttpStatus.ACCEPTED
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
    //</editor-fold>
}


