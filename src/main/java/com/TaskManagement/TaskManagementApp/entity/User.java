package com.TaskManagement.TaskManagementApp.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    private long id;
    @Column(length = 50)
    private String name;
    @Column(length = 50)
    private String last_name;
    @Column(length = 250)
    private String email;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> tasks;

    public static class builder {
        private long id;
        private String name;
        private String last_name;
        private String email;

        public builder id(long id) {
            this.id = id;
            return this;
        }

        public builder name(String name) {
            this.name = name;
            return this;
        }

        public builder last_name(String last_name) {
            this.last_name = last_name;
            return this;
        }

        public builder email(String email) {
            this.email = email;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    public User() { }

    public User(builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.last_name = builder.last_name;
        this.email = builder.email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    @PrePersist
    public void setCreated_at() {
        this.created_at = LocalDateTime.now();
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    @PreUpdate
    public void setUpdated_at() {
        this.updated_at = LocalDateTime.now();
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}