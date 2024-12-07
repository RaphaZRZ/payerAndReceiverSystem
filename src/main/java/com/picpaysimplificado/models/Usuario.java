package com.picpaysimplificado.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.picpaysimplificado.dtos.UsuarioDTO;
import com.picpaysimplificado.exceptions.InvalidCNPJException;
import com.picpaysimplificado.exceptions.InvalidCPFException;
import com.picpaysimplificado.serializers.DocumentSerializer;
import com.picpaysimplificado.serializers.PasswordSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "usuarios")
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @NotNull
    @Size(min = 3, max = 20, message = "O nome deve conter de 3 a 20 caracteres.")
    @Column(name = "nome")
    private String firstName;

    @NotNull
    @Size(min = 3, max = 20, message = "O sobrenome deve conter de 3 a 20 caracteres.")
    @Column(name = "sobrenome")
    private String lastName;

    @NotNull
    @JsonSerialize(using = DocumentSerializer.class)
    @Column(name = "documento", unique = true)
    private String document;

    @NotNull
    @Email(message = "email inválido.")
    @Column(name = "Email", unique = true)
    private String email;

    @NotNull
    @Size(min = 6, max = 60, message = "A senha deve conter de 6 a 60 caracteres.")
    @JsonSerialize(using = PasswordSerializer.class)
    @Column(name = "senha")
    private String password;

    @NotNull
    @Column(name = "saldo")
    private BigDecimal balance;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private UsuarioType usuarioType;

    public Usuario(UsuarioDTO usuarioData) throws Exception {
        if (usuarioData.usuarioType() == UsuarioType.MERCHANT && usuarioData.document().length() != 14)
            throw new InvalidCNPJException();

        else if (usuarioData.usuarioType() == UsuarioType.COMMON && usuarioData.document().length() != 11)
            throw new InvalidCPFException();

        this.firstName = usuarioData.firstName();
        this.lastName = usuarioData.lastName();
        this.document = usuarioData.document();
        this.email = usuarioData.email();
        this.password = usuarioData.password();
        this.balance = usuarioData.balance();
        this.usuarioType = usuarioData.usuarioType();
    }
}
