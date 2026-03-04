package eu.maravelias.architectprojectplanner.entity;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.UUID;

import io.jmix.core.DeletePolicy;
import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@JmixEntity
@Table(name = "TIME_LOG", indexes = {
    @Index(name = "IDX_TIME_LOG_PROJECT", columnList = "PROJECT_ID"),
    @Index(name = "IDX_TIME_LOG_DELIVERABLE", columnList = "DELIVERABLE_ID"),
    @Index(name = "IDX_TIME_LOG_TASK", columnList = "TASK_ID"),
    @Index(name = "IDX_TIME_LOG_USER", columnList = "USER_ID")
})
@Entity(name = "timeLog")
public class TimeLog {
    @JmixGeneratedValue @Column(name = "ID", nullable = false) @Id private UUID id;

    @OnDeleteInverse(DeletePolicy.DENY) @OnDelete(DeletePolicy.UNLINK) @JoinColumn(name = "PROJECT_ID", nullable = false) @Composition @NotNull @OneToOne(fetch = FetchType.LAZY, optional = false) private Project project;

    @OnDeleteInverse(DeletePolicy.UNLINK) @OnDelete(DeletePolicy.UNLINK) @JoinColumn(name = "DELIVERABLE_ID") @Composition @OneToOne(fetch = FetchType.LAZY) private Deliverable deliverable;

    @OnDeleteInverse(DeletePolicy.UNLINK) @OnDelete(DeletePolicy.UNLINK) @JoinColumn(name = "TASK_ID") @Composition @OneToOne(fetch = FetchType.LAZY) private Task task;

    @OnDeleteInverse(DeletePolicy.DENY) @OnDelete(DeletePolicy.UNLINK) @JoinColumn(name = "USER_ID", nullable = false) @Composition @NotNull @OneToOne(fetch = FetchType.LAZY, optional = false) private User user;

    @Temporal(TemporalType.DATE) @Column(name = "WORK_DATE", nullable = false) @NotNull private Date workDate;

    @Column(name = "DURATION", nullable = false) @NotNull private Duration duration;

    @Column(name = "NOTE") private String note;

    @Column(name = "VERSION", nullable = false) @Version private Integer version;

    @CreatedBy @Column(name = "CREATED_BY") private String createdBy;

    @CreatedDate @Column(name = "CREATED_DATE") private OffsetDateTime createdDate;

    @LastModifiedBy @Column(name = "LAST_MODIFIED_BY") private String lastModifiedBy;

    @LastModifiedDate @Column(name = "LAST_MODIFIED_DATE") private OffsetDateTime lastModifiedDate;

    @DeletedBy @Column(name = "DELETED_BY") private String deletedBy;

    @DeletedDate @Column(name = "DELETED_DATE") private OffsetDateTime deletedDate;

    public String getNote() {return note;}

    public void setNote(String note) {this.note = note;}

    public Duration getDuration() {return duration;}

    public void setDuration(Duration duration) {this.duration = duration;}

    public Date getWorkDate() {return workDate;}

    public void setWorkDate(Date workDate) {this.workDate = workDate;}

    public User getUser() {return user;}

    public void setUser(User user) {this.user = user;}

    public Task getTask() {return task;}

    public void setTask(Task task) {this.task = task;}

    public Deliverable getDeliverable() {return deliverable;}

    public void setDeliverable(Deliverable deliverable) {this.deliverable = deliverable;}

    public Project getProject() {return project;}

    public void setProject(Project project) {this.project = project;}

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