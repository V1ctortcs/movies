package com.trimble.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "MOVIE")
public class MovieEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MOVIE")
    private Integer codMovie;

    @Column(name = "TITLE_MOVIE")
    private String titleMovie;

    @Column(name = "RELEASE_DATE_MOVIE")
    private LocalDate releaseDateMovie;

    @Column(name = "SYNOPSIS_MOVIE")
    private String synopsisMovie;

    @Column(name = "USER_RATING_MOVIE")
    private Integer userRatingMovie;

}
