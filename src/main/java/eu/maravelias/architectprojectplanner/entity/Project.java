package eu.maravelias.architectprojectplanner.entity;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import io.jmix.core.DeletePolicy;
import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@JmixEntity
@Table(name = "PROJECT", indexes = {
    @Index(name = "IDX_PROJECT_CLIENT", columnList = "CLIENT_ID")
})
@Entity(name = "project")
public class Project {
    @JmixGeneratedValue @Column(name = "ID", nullable = false) @Id private UUID id;

    @Column(name = "START_DATE") private LocalDate startDate;

    @Column(name = "END_DATE") private LocalDate endDate;

    @Column(name = "STATUS", nullable = false, length = 20) @NotNull private String status;

    @InstanceName @Column(name = "PROJECT_NAME", nullable = false) @NotNull private String projectName;

    @Column(name = "DESCRIPTION") private String description;

    @Column(name = "VERSION", nullable = false) @Version private Integer version;

    @CreatedBy @Column(name = "CREATED_BY") private String createdBy;

    @CreatedDate @Column(name = "CREATED_DATE") private OffsetDateTime createdDate;

    @LastModifiedBy @Column(name = "LAST_MODIFIED_BY") private String lastModifiedBy;

    @LastModifiedDate @Column(name = "LAST_MODIFIED_DATE") private OffsetDateTime lastModifiedDate;

    @DeletedBy @Column(name = "DELETED_BY") private String deletedBy;

    @DeletedDate @Column(name = "DELETED_DATE") private OffsetDateTime deletedDate;

    @OnDeleteInverse(DeletePolicy.CASCADE) @JoinColumn(name = "CLIENT_ID", nullable = false) @ManyToOne(fetch = FetchType.LAZY, optional = false) private Client client;

    public Status getStatus() {return status == null ? null : Status.fromId(status);}

    public void setStatus(Status status) {this.status = status == null ? null : status.getId();}

    public LocalDate getEndDate() {return endDate;}

    public void setEndDate(LocalDate endDate) {this.endDate = endDate;}

    public LocalDate getStartDate() {return startDate;}

    public void setStartDate(LocalDate startDate) {this.startDate = startDate;}

    public Client getClient() {return client;}

    public void setClient(Client client) {this.client = client;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public String getProjectName() {return projectName;}

    public void setProjectName(String projectName) {this.projectName = projectName;}

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