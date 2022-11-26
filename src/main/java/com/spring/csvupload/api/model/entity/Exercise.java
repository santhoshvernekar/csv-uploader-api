package com.spring.csvupload.api.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Table
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Exercise implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "SOURCE")
    private String source;
    @Column(name = "CODE_LIST_CODE")
    private String codeListCode;
    @Column(name = "CODE", unique = true)
    private String code;
    @Column(name = "DISPLAY_VALUE")
    private String displayValue;
    @Column(name = "LONG_DESCRIPTION")
    private String longDescription;
    @Column(name = "FROM_DATE")
    private Date fromDate;
    @Column(name = "TO_DATE")
    private Date toDate;
    @Column(name = "SORTING_PRIORITY")
    private Integer sortingPriority;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Exercise)) return false;
        Exercise that = (Exercise) o;
        return (!Objects.equals(id, that.id));
    }
}
