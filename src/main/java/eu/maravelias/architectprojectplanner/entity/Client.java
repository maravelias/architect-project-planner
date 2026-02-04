package eu.maravelias.architectprojectplanner.entity;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import io.jmix.core.DeletePolicy;
import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@JmixEntity
@Table(name = "CLIENT")
@Entity(name = "client")
public class Client {
    @JmixGeneratedValue @Column(name = "ID", nullable = false) @Id private UUID id;

    @InstanceName @Column(name = "NAME", nullable = false) @NotNull private String name;

    @OnDeleteInverse(DeletePolicy.UNLINK) @OnDelete(DeletePolicy.CASCADE) @Composition @OneToMany(mappedBy = "client") private List<Project> projects;

    @Column(name = "DESCRIPTION") private String description;

    @Column(name = "VERSION", nullable = false) @Version private Integer version;

    @CreatedBy @Column(name = "CREATED_BY") private String createdBy;

    @CreatedDate @Column(name = "CREATED_DATE") private OffsetDateTime createdDate;

    @LastModifiedBy @Column(name = "LAST_MODIFIED_BY") private String lastModifiedBy;

    @LastModifiedDate @Column(name = "LAST_MODIFIED_DATE") private OffsetDateTime lastModifiedDate;

    @DeletedBy @Column(name = "DELETED_BY") private String deletedBy;

    @DeletedDate @Column(name = "DELETED_DATE") private OffsetDateTime deletedDate;

    public List<Project> getProjects() {return projects;}

    public void setProjects(List<Project> projects) {this.projects = projects;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public OffsetDateTime getDeletedDate() {return deletedDate;}

    public void setDeletedDate(OffsetDateTime deletedDate) {this.deletedDate = deletedDate;}

    public String getDeletedBy() {return deletedBy;}

    public void setDeletedBy(String deletedBy) {this.deletedBy = deletedBy;}

    public OffsetDateTime getLastModifiedDate() {return lastModifiedDate;}

    public void setLastModifiedDate(OffsetDateTime lastModifiedDate) {this.lastModifiedDate = lastModifiedDate;}

    public String getLastModifiedBy() {return lastModifiedBy;}

    public void setLastModifiedBy(String lastModifiedBy) {this.lastModifiedBy = lastModifiedBy;}

    public OffsetDateTime getCreatedDate() {return createdDate;}

    public void setCreatedDate(OffsetDateTime createdDate) {this.createdDate = createdDate;}

    public String getCreatedBy() {return createdBy;}

    public void setCreatedBy(String createdBy) {this.createdBy = createdBy;}

    public Integer getVersion() {return version;}

    public void setVersion(Integer version) {this.version = version;}

    public UUID getId() {return id;}

    public void setId(UUID id) {this.id = id;}

}