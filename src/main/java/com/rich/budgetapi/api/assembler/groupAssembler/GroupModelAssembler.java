package com.rich.budgetapi.api.assembler.groupAssembler;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rich.budgetapi.api.model.GroupModel;
import com.rich.budgetapi.domain.model.Group;

@Component
public class GroupModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public GroupModel toModel(Group group) {
        return modelMapper.map(group, GroupModel.class);
    }

    public List<GroupModel> toCollectionModel(List<Group> groups) {
        List<GroupModel> groupsModel = new ArrayList<>();

        for (Group group : groups) {
            groupsModel.add(toModel(group));
        }

        return groupsModel;
    }
}
