package com.nature.component.flow.model;

import com.nature.base.BaseHibernateModelUUIDNoCorpAgentId;
import com.nature.component.mxGraph.model.MxGraphModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "FLOW_GROUP")
@Setter
@Getter
public class FlowGroup extends BaseHibernateModelUUIDNoCorpAgentId {

    private static final long serialVersionUID = 1L;

    @Column(columnDefinition = "varchar(255) COMMENT 'flow name'")
    private String name;

    @Column(name = "description", columnDefinition = "text(0) COMMENT 'description'")
    private String description;

    @Column(name = "page_id")
    private String pageId;

    @Column(columnDefinition = "bit(1) COMMENT 'isExample'")
    private Boolean isExample = false;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "flowGroup")
    @Where(clause = "enable_flag=1")
    private MxGraphModel mxGraphModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_FLOW_PROJECT_ID")
    private FlowProject flowProject;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "flowGroup")
    @Where(clause = "enable_flag=1")
    @OrderBy(clause = "lastUpdateDttm desc")
    private List<Flow> flowList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "flowGroup")
    @Where(clause = "enable_flag=1")
    @OrderBy(clause = "lastUpdateDttm desc")
    private List<FlowGroupPaths> flowGroupPathsList = new ArrayList<>();

}
