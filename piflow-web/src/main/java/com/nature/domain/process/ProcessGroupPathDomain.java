package com.nature.domain.process;

import com.nature.component.process.model.ProcessGroupPath;
import com.nature.repository.process.ProcessGroupPathJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class ProcessGroupPathDomain {

    @Autowired
    private ProcessGroupPathJpaRepository processGroupPathJpaRepository;

    private Specification<ProcessGroupPath> addEnableFlagParam() {
        Specification<ProcessGroupPath> specification = new Specification<ProcessGroupPath>() {
            @Override
            public Predicate toPredicate(Root<ProcessGroupPath> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                //root.get("enableFlag") means to get the field name of enableFlag
                return criteriaBuilder.equal(root.get("enableFlag"), 1);
            }
        };
        return specification;
    }

    private Specification<ProcessGroupPath> addParam(String key, String value) {
        Specification<ProcessGroupPath> specification = new Specification<ProcessGroupPath>() {
            @Override
            public Predicate toPredicate(Root<ProcessGroupPath> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                //root.get(key) means to get the name of the key field
                return criteriaBuilder.equal(root.get(key), value);
            }
        };
        return specification;
    }

    public ProcessGroupPath getProcessGroupPathById(String id) {
        ProcessGroupPath processGroupPath = processGroupPathJpaRepository.getOne(id);
        if (null != processGroupPath && !processGroupPath.getEnableFlag()) {
            processGroupPath = null;
        }
        return processGroupPath;
    }

    public List<ProcessGroupPath> getProcessGroupPathList() {
        return processGroupPathJpaRepository.findAll(addEnableFlagParam());
    }

    public ProcessGroupPath saveOrUpdate(ProcessGroupPath processGroupPath) {
        return processGroupPathJpaRepository.save(processGroupPath);
    }

    public List<ProcessGroupPath> saveOrUpdate(List<ProcessGroupPath> processGroupPathList) {
        return processGroupPathJpaRepository.saveAll(processGroupPathList);
    }

    public int updateEnableFlagById(String id, boolean enableFlag) {
        return processGroupPathJpaRepository.updateEnableFlagById(id, enableFlag);
    }

}
