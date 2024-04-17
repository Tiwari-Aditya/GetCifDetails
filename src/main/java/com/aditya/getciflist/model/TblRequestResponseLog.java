package com.aditya.getciflist.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_request_response_log")
@Data
public class TblRequestResponseLog implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(columnDefinition = "VARCHAR(100) NULL COMMENT 'This is the unique ID to identify the particular rwquest'")
    String iblApiReqId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = " BIGINT(20) NOT NULL COMMENT 'Primary id of tbl_request_log'")
    private long reqId;

    @NotNull
    @NotEmpty
    private String endUrl;

    @NotNull
    @NotEmpty
    @Column(columnDefinition = " LONGTEXT NOT NULL COMMENT ''")
    private String request;

    @Column(columnDefinition = " LONGTEXT NULL COMMENT ''")
    private String response;

    private String activityType;

    private LocalDateTime requestTime;

    private LocalDateTime responseTime;

    @Column(columnDefinition = " VARCHAR(50) NULL DEFAULT 'Pending'")
    private String responseStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
