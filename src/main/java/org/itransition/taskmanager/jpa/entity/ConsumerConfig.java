package org.itransition.taskmanager.jpa.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.MapsId;
import javax.persistence.ForeignKey;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Index;
import javax.persistence.UniqueConstraint;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "consumer", callSuper = false)
@Entity
@Table(name = "consumer_config",
        indexes = @Index(name = "ix_consumer_config_notifications_enabled_notification_frequency",
                columnList = "notifications_enabled, notification_frequency"),
        uniqueConstraints = @UniqueConstraint(name = "uk_consumer_config_email_for_notifications",
                columnNames = "email_for_notifications"))
public class ConsumerConfig extends AbstractEntityLongId {

    @Column(name = "notifications_enabled", nullable = false, columnDefinition = "tinyint(1) default false")
    private Boolean notifications;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_frequency", nullable = false,
            columnDefinition = "varchar(30) default 'PER_MONTH'")
    private NotificationFrequency notificationFrequency;

    @Column(name = "email_for_notifications", nullable = false,
            columnDefinition = "varchar(40) default 'MISSED'")
    private String email;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_consumer_config_consumer"))
    private Consumer consumer;
}
