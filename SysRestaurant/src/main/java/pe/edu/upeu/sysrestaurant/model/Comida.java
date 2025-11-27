package pe.edu.upeu.sysrestaurant.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "upeu_comida")
public class Comida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comida")
    private Long idComida;
    
    @NotNull(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 120, message = "El nombre debe tener entre 2 y 120 caracteres")
    @Column(name = "nombre", nullable = false, length = 120)
    private String nombre;
    
    @Positive(message = "El Precio debe ser positivo")
    @Column(name = "precio", nullable = false)
    private Double precio;
    
    @PositiveOrZero(message = "El Stock debe ser positivo o cero")
    @Column(name = "stock", nullable = false)
    private Double stock;
    
    @Column(name = "descripcion", length = 255)
    private String descripcion;
    
    @Builder.Default
    @Column(name = "disponible", nullable = false)
    private Boolean disponible = true;
}


