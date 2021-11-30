package com.store.entities;

import com.store.repositories.db_config.queries.Column;
import com.store.repositories.db_config.queries.Entity;
import com.store.repositories.db_config.queries.Id;
import lombok.Data;
import java.io.Serializable;

@Data
@Entity(table = "users")
public class User implements Serializable {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "userhash")
    private String userHash;

    public User(int id, String username, String password, String userHash) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userHash = userHash;
    }

    public User() {
    }
}
