package com.Smillio.app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "documentos_odontologo")
public class DocumentoOdontologo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "odontologo_id", nullable = false)
    private Odontologo odontologo;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoDocumento tipo; // TITULO, DIPLOMA, LICENCIA, CV, OTRO

    @Column(nullable = false)
    private String nombreArchivo; // Nombre original del archivo

    @Column(nullable = false)
    private String rutaArchivo; // Ruta o URL donde se almacena el archivo

    private Long tamaño; // Tamaño del archivo en bytes

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaSubida;

    @Column(nullable = false)
    private Boolean verificado = false; // Si la clínica ha revisado el documento

    private String notas; // Comentarios de verificación (si es rechazado)

    @PrePersist
    protected void onCreate() {
        this.fechaSubida = LocalDateTime.now();
    }

    public enum TipoDocumento {
        TITULO,
        DIPLOMA,
        LICENCIA,
        CV,
        OTRO
    }
}
