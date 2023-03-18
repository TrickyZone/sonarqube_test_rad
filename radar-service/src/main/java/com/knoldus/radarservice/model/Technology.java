package com.knoldus.radarservice.model;

import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "technology")
public class Technology {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "ring")
    private String ring;

    @Column(name = "quadrant")
    private String quadrant;

    @Column(name = "is_new")
    private Boolean isNew;

    @Column(name = "description")
    private String description;

    @Column(name = "studio")
    private String studio;

    @Column(name = "quarter")
    private String quarter;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "year")
    private String year;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "alternate_technology")
    private String alternateTechnology;

    @Column(name = "github_url")
    private String githubUrl;
}