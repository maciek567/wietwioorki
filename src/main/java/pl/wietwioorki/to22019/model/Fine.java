package pl.wietwioorki.to22019.model;

import javafx.beans.property.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "fine")
public class Fine {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "fine_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pesel",
            referencedColumnName = "pesel")
    private Reader reader;
    float value;
    private String description;
    private boolean isPayed;

    public Fine(float value, String description, Reader reader) {
        this.value = value;
        this.description = description;
        this.reader = reader;
        this.isPayed = false;
    }

    public ObjectProperty<Long> getFineIdProperty() {
        return new SimpleObjectProperty<>(id);
    }

    public ObjectProperty<Long> getReaderPeselProperty() {
        return new SimpleObjectProperty<>(reader.getPesel());
    }

    public StringProperty getDescriptionProperty() {
        return new SimpleStringProperty(description);
    }

    public ObjectProperty<Float> getAmountProperty() {
        return new SimpleObjectProperty<>(value);
    }

    public BooleanProperty getIsPayedProperty() {
        return new SimpleBooleanProperty(isPayed);
    }

    public void payFine() {
        value = 0;
        isPayed = true;
    }
}
