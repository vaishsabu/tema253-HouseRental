package at.htlstp.aslan.houserent.model;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class House implements Serializable {

    @Id
    @NotBlank(message = "{notBlank}")
    @EqualsAndHashCode.Include
    private String houseNr;

    @NotNull(message = "{notNull}")
    @Range(min = 18500, max = 2090000, message = "{PriceError}")
    private Integer Price;

    @NotNull(message = "{notBlank}")
    private String street;

    @NotBlank(message = "{notBlank}")
    private String model;

    @ManyToOne
    private Station station;

    public void sethouseNr(String houseNr) {
        this.houseNr = houseNr.strip();
    }

    public void setModel(String model) {
        this.model = model.strip();
    }

    @Override
    public String toString() {
        return "(" + houseNr + ") " + model;
    }
}
