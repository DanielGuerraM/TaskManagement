package com.TaskManagement.TaskManagementApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table
public class Category {
    @Id
    private long id;
    @Column(length = 50, unique = true)
    private String name;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    @JsonIgnore
    @OneToOne(mappedBy = "category", cascade = CascadeType.ALL)
    private Task task;

    public static class builder {
        private long id;
        @Column(length = 50)
        private String name;

        public builder id(long id) {
            this.id = id;
            return this;
        }

        public builder name(String name) {
            this.name = name;
            return this;
        }

        public Category build() {
            return new Category(this);
        }
    }

    @PrePersist
    public void setCreated_at() {
        this.created_at = LocalDateTime.now();
    }

    @PreUpdate
    public void setUpdated_at() {
        this.updated_at = LocalDateTime.now();
    }

    public Category() { }

    private Category(builder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }
}