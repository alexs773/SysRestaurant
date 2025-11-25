package pe.edu.upeu.sysrestaurant.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "upeu_pedido_carrito")
public class PedidoCarrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carrito")
    public Long idCarrito;
    @Column(name = "dniruc", nullable = false, length = 12)
    public String dniruc;
    @ManyToOne
    @JoinColumn(name = "id_comida", nullable = false)
    public Comida comida;
    @Column(name = "nombre_comida", nullable = false, length = 120)
    public String nombreComida;
    @Column(name = "cantidad", nullable = false)
    public Double cantidad;
    @Column(name = "punitario", nullable = false)
    public Double punitario;
    @Column(name = "ptotal", nullable = false)
    public Double ptotal;
    @Column(name = "estado", nullable = false)
    public int estado;
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    public Usuario usuario;
}

