package com.aditya.getciflist.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_error_messages")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TblErrorMessages implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;

    @NotNull
    @NotEmpty
    private  String serviceCode;

    @NotNull
    @NotEmpty
    private String errorCode;


    private String errorLink;

    @NotNull
    @NotEmpty
    @Column(columnDefinition=" TEXT NOT NULL COMMENT ''")
    private String errorMessage;

    @Column(columnDefinition=" BOOLEAN NOT NULL COMMENT '0=Inactive, 1=Active'")
    private boolean status;


    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;
}
