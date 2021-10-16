package com.university.service;

import com.university.entity.Group;
import com.university.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UniversityService {

    @Autowired
    private GroupRepository groupRepository;

    @Transactional
    public Group saveGroup(Group group) {
        return groupRepository.save(group);
    }

    @Transactional(readOnly = true)
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }
}
