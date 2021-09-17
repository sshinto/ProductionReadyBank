package com.hollandandbarret.bank.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Represents all common properties of standard domain objects
 *
 * @author Savitha Shinto
 */
@MappedSuperclass
@Data
@NoArgsConstructor
public abstract class BaseDomainObject implements Serializable {
    @CreationTimestamp
    @Column(name = "CREATED_TIMESTAMP")
    protected Timestamp createTimestamp;

    @UpdateTimestamp
    @Column(name = "UPDATED_TIMESTAMP")
    protected Timestamp lastEditTimestamp;
}
