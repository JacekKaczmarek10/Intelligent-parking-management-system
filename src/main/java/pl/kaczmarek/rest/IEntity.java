package pl.kaczmarek.rest;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public abstract class IEntity {

    /* Identifier */
    @Id
    @GeneratedValue(
            generator = "uuid2"
    )
    @GenericGenerator(
            name = "uuid2", strategy = "uuid2"
    )
    @Type(type="uuid-char")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    /* Creation date */
    @Column(name = "creation_date")
    private Timestamp creationDate;

    @Column(name = "modification_date")
    private Timestamp modificationDate;

    /* Getter & Setter */
    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Timestamp getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Timestamp creationDate) {
        this.modificationDate = creationDate;
    }

    /* Custom hooks */
    @PrePersist
    protected void onCreate() {
        creationDate = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        modificationDate = new Timestamp(System.currentTimeMillis());
    }
}
