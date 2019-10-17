/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.vlired.submod.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 *
 * @author luizo
 */
@Entity
@Table(name = "role")
public class Role extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="role_id")
    private Long role_id;
    @Column(name="name")
    private String name;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }
    
    public long getRole_Id() {
		return role_id;
	}

	public void setRole_Id(long role_id) {
		this.role_id = role_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
        
       // @OneToMany(mappedBy="role")
// 	private Set<Uer> employee;
        
}

