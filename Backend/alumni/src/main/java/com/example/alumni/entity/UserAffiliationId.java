package com.example.alumni.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Represents the composite primary key for the UserAffiliation entity.
 * This class must implement Serializable and provide equals() and hashCode() methods.
 */
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode // Lombok automatically generates proper equals() and hashCode()
public class UserAffiliationId implements Serializable {

    private String userId;
    private String entityId;
}