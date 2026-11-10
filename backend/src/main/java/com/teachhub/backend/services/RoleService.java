package com.teachhub.backend.services;

import com.teachhub.backend.dtos.Admin.AdminRequestDto.RequestRoleDto;
import com.teachhub.backend.entities.Role;
import com.teachhub.backend.exceptions.ResourceNotFoundException;
import com.teachhub.backend.repositories.RolesRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleService {
    private RolesRepository rolesRepository;

    public Page<Role> getAll(int page){
        Pageable pageable= PageRequest.of(page,10);
        return rolesRepository.findAll(pageable);
    }

    public void create(RequestRoleDto requestRoleDto){
        Role role=new Role();
        role.setRole(requestRoleDto.getRole());
        rolesRepository.save(role);
    }
    public void update(Long id,RequestRoleDto requestRoleDto){
        Role role=rolesRepository.findById(id).orElseThrow(
                ResourceNotFoundException::new
        );
        role.setRole(requestRoleDto.getRole());
        rolesRepository.save(role);
    }
    public void delete(Long id) {
        rolesRepository.findById(id).orElseThrow(
                ResourceNotFoundException::new
        );
        rolesRepository.deleteById(id);
    }
}
