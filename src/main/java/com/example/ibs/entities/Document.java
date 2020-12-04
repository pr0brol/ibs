package com.example.ibs.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "document")
@Getter
@Setter
@NoArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "first_side_id")
    private DocumentSide firstSide;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "second_side_id")
    private DocumentSide secondSide;

}
