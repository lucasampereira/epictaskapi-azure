package br.com.fiap.epictaskapi.dto;

public record UserDto (

     Long id,
     String name,
     String email,
     String password

) {

     public UserDto(String name, String email, String password){
          this(0l, name, email, password);
     }

}
