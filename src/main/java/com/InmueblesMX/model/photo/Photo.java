package com.InmueblesMX.model.photo;

import com.InmueblesMX.model.property.PropertyEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "photos")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    @ManyToOne
    @JoinColumn(name = "id_property")
    @JsonIgnore
    private PropertyEntity property;

}
