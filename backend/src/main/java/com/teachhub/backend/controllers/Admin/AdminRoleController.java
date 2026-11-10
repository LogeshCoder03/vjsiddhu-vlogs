package com.teachhub.backend.controllers.Admin;

import com.teachhub.backend.Utils.ResponseData;
import com.teachhub.backend.dtos.Admin.AdminRequestDto.RequestRoleDto;
import com.teachhub.backend.entities.Role;
import com.teachhub.backend.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/roles")
public class AdminRoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<Page<Role> > getRoles(@RequestParam(defaultValue = "0") int page){
        return ResponseEntity.ok(roleService.getAll(page));
    }

    @PostMapping
    public ResponseEntity<ResponseData> createRole(@RequestBody RequestRoleDto requestRoleDto){
        roleService.create(requestRoleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseData("Role created Successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseData> updateRole(@PathVariable Long id,
                                                   @RequestBody RequestRoleDto requestRoleDto){
        roleService.update(id,requestRoleDto);
        return ResponseEntity.ok(new ResponseData("Role updated Successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData> deleteRole(@PathVariable Long id){
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
