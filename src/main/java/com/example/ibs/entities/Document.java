package com.example.ibs.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "document")
@Data
@NoArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @OneToOne
    @JoinColumn(name = "first_side_id")
    private DocumentSide firstSide;

    @OneToOne
    @JoinColumn(name = "second_side_id")
    private DocumentSide secondSide;

}
