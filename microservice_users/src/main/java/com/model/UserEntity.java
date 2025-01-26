package com.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor // Constructor sin parámetros
@AllArgsConstructor // Constructor con todos los parámetros
@Getter
@Setter
@Entity
@Table(name="usuarios")
public class UserEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idUser;
	private String name;
	private String email;
	private String password;
	private boolean enabled;
	
	@ManyToMany
	@JoinTable(name="user_roles",
	      joinColumns =@JoinColumn(name="id_usuario", referencedColumnName = "id_usuario"),
	       inverseJoinColumns = @JoinColumn(name="id_rol",referencedColumnName = "id"))
	private List<RolesEntity> roles;

}
