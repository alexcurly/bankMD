package com.bank.profile.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name="audit" , schema="profile")
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="entity_type", nullable = false)
    private String entityType;

    @Column(name="operation_type", nullable = false)
    private String operationType;

    @Column(name="created_by", nullable = false)
    private String createdBy;

    @Column(name="modified_by")
    private String modifiedBy;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @CreationTimestamp
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name="new_entity_json")
    private String newEntityJson;

    @Column(name="entity_json", nullable = false)
    private String entityJson;
}
